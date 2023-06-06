package com.musafira2z.store.utils

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.mpp.currentTimeMillis
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.musafira2z.store.RefreshTokenMutation
import com.musafira2z.store.repository.settings.SettingsRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AuthorizationInterceptor(
    private val settingsRepository: SettingsRepository,
    private val maxSize: Int = 1
) : HttpInterceptor {
    private val mutex = Mutex()

    class Token(val value: String, val expiresEpochSecond: Long)

    /**
     * A token link in the chain
     */
    class TokenLink(
        val oldValue: String?,
        val newValue: String,
        val expiresEpochSecond: Long,
        var next: TokenLink?,
    )

    private var head: TokenLink? = null
    private var tail: TokenLink? = null
    private var listSize: Int = 0

    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        val tokenValue = mutex.withLock {
            if (tail == null) {
                val savedExpire = settingsRepository.authTokenExpire
                var token = settingsRepository.authToken?.let {
                    Token(
                        it,
                        savedExpire ?: currentTimeSeconds()
                    )
                }

                if (token == null) {
                    token = refreshToken(null)
                }

                tail = TokenLink(
                    oldValue = null,
                    newValue = token.value,
                    expiresEpochSecond = token.expiresEpochSecond,
                    next = null
                )
                head = tail
                listSize++
            }

            val link = tail!!

            // Start refreshing tokens 2 seconds before they actually expire to account for
            // network time
            val margin = 2
            if (currentTimeMillis() / 1000 + margin - link.expiresEpochSecond >= 0) {
                // This token will soon expire, get a new one
                val token = refreshToken(link.newValue)

                insert(
                    TokenLink(
                        oldValue = link.newValue,
                        newValue = token.value,
                        expiresEpochSecond = token.expiresEpochSecond,
                        next = null
                    )
                )
            }

            tail!!.newValue
        }

        val response = chain.proceed(
            if (tokenValue.isNotBlank()) {
                request.newBuilder().addHeader("Authorization", "JWT $tokenValue").build()
            } else {
                request.newBuilder().build()
            }
        )

        return if (response.statusCode == 401) {
            val newTokenValue: String = mutex.withLock {
                var cur = head
                while (cur != null) {
                    if (cur.oldValue == tokenValue) {
                        // follow the chain up to the new token
                        while (cur!!.next != null) {
                            cur = cur.next
                        }
                        // we have found a valid new token for this old token
                        return@withLock cur.newValue
                    }
                    cur = cur.next
                }

                // we haven't found a link for this old value, get a new token
                val token = refreshToken(tokenValue)
                insert(
                    TokenLink(
                        oldValue = tokenValue,
                        newValue = token.value,
                        expiresEpochSecond = token.expiresEpochSecond,
                        next = null
                    )
                )

                token.value
            }
            chain.proceed(
                request.newBuilder().addHeader("Authorization", "JWT $newTokenValue").build()
            )
        } else {
            response
        }
    }

    suspend fun refreshToken(oldToken: String?): Token {
        val csrfToken = settingsRepository.csrfToken
        val refreshToken = settingsRepository.refreshToken
        if (refreshToken != null && csrfToken != null) {
            val refreshMutation =
                RefreshTokenMutation(csrfToken = csrfToken, refreshToken = refreshToken)
            val client = ApolloClient.Builder()
                .serverUrl("https://api.musafira2z.com/graphql/")
                .addHttpInterceptor(object : HttpInterceptor {
                    override suspend fun intercept(
                        request: HttpRequest,
                        chain: HttpInterceptorChain
                    ): HttpResponse {
                        val newRequest = request.newBuilder()
                            // remove apollo headers
                            .headers(request.headers.filter {
                                !it.name.lowercase().startsWith("x-apollo")
                            })
                            .build()
                        return chain.proceed(newRequest)
                    }
                })
                .build()
            val response = client.mutation(refreshMutation).execute()
            if (response.data == null || response.hasErrors()) {
                return Token("", currentTimeSeconds())
            }
            val token = response.data?.tokenRefresh?.token
                ?: return Token("", currentTimeSeconds())

            val expire = currentTimeSeconds() + (1000 * 60 * 5)
            settingsRepository.saveAuthToken(token)
            settingsRepository.saveAuthTokenExpire(expire)

            return Token(token, expire)
        } else {
            return Token("", currentTimeSeconds())
        }
    }

    /**
     * Insert a new link.
     *
     * Assumes the list is not empty
     */
    private fun insert(tokenLink: TokenLink) {
        tail!!.next = tokenLink
        tail = tokenLink
        listSize++

        // Trim the list if needed
        while (listSize > maxSize) {
            head = head!!.next
            listSize--
        }
    }
}

fun currentTimeSeconds() = currentTimeMillis() / 1000