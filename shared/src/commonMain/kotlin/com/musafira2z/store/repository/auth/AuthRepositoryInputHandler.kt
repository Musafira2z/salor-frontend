package com.musafira2z.store.repository.auth

import com.apollographql.apollo3.ApolloClient
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.observeInputsFromBus
import com.copperleaf.ballast.repository.cache.fetchWithCache
import com.musafira2z.store.*
import com.musafira2z.store.repository.settings.SettingsRepository
import com.musafira2z.store.utils.ResponseResource

class AuthRepositoryInputHandler(
    private val eventBus: EventBus,
    private val settingsRepository: SettingsRepository,
    private val apolloClient: ApolloClient
) : InputHandler<
        AuthRepositoryContract.Inputs,
        Any,
        AuthRepositoryContract.State> {
    override suspend fun InputHandlerScope<
            AuthRepositoryContract.Inputs,
            Any,
            AuthRepositoryContract.State>.handleInput(
        input: AuthRepositoryContract.Inputs
    ) = when (input) {
        is AuthRepositoryContract.Inputs.ClearCaches -> {
            updateState { AuthRepositoryContract.State() }
        }
        is AuthRepositoryContract.Inputs.Initialize -> {
            val previousState = getCurrentState()

            if (!previousState.initialized) {
                updateState { it.copy(initialized = true) }
                // start observing flows here
                logger.debug("initializing")
                observeFlows(
                    key = "Observe account changes",
                    eventBus
                        .observeInputsFromBus<AuthRepositoryContract.Inputs>(),
                )
            } else {
                logger.debug("already initialized")
                noOp()
            }
        }
        is AuthRepositoryContract.Inputs.RefreshAllCaches -> {
            // then refresh all the caches in this repository
            val currentState = getCurrentState()
            if (currentState.dataListInitialized) {
                postInput(AuthRepositoryContract.Inputs.RefreshDataList(true))
            }

            Unit
        }

        is AuthRepositoryContract.Inputs.DataListUpdated -> {
            updateState { it.copy(dataList = input.dataList) }
        }
        is AuthRepositoryContract.Inputs.RefreshDataList -> {
            updateState { it.copy(dataListInitialized = true) }
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.dataList },
                updateState = { AuthRepositoryContract.Inputs.DataListUpdated(it) },
                doFetch = { TODO() },
            )
        }
        is AuthRepositoryContract.Inputs.LoginUser -> {
            sideJob("LoginUser") {
                val resource = try {
                    val authenticateMutation =
                        LoginCustomerMutation(email = input.username, password = input.password)
                    val response = apolloClient.mutation(authenticateMutation).execute()
                    if (response.data == null || response.errors?.isNotEmpty() == true) {
                        throw Exception("Login failed")
                    }
                    ResponseResource.Success(response.data!!.tokenCreate)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ResponseResource.Error(exception = e)
                }
                postInput(AuthRepositoryContract.Inputs.UpdateLoginResponse(resource))
            }
        }
        is AuthRepositoryContract.Inputs.UpdateLoginResponse -> {
            updateState { it.copy(loginResponse = input.loginResponse) }
            when (val data = input.loginResponse) {
                is ResponseResource.Error -> {

                }
                ResponseResource.Idle -> {

                }
                ResponseResource.Loading -> {

                }
                is ResponseResource.Success -> {
                    postInput(AuthRepositoryContract.Inputs.FetchLoginStatus)
                }
            }
        }
        AuthRepositoryContract.Inputs.FetchLoginStatus -> {
            postInput(AuthRepositoryContract.Inputs.UpdateLoginStatus(settingsRepository.authToken != null))
        }
        is AuthRepositoryContract.Inputs.UpdateLoginStatus -> {
            updateState { it.copy(isLoggedIn = input.isLoggedIn) }
        }
        is AuthRepositoryContract.Inputs.SignupUser -> {
            sideJob("SignupUser") {
                val resource = try {
                    val authenticateMutation =
                        RegisterCustomerMutation(
                            email = input.username,
                            password = input.password,
                            firstname = input.fullName,
                            redirect = baseUrl,
                            channel = settingsRepository.channel ?: normalChannel
                        )
                    val response = apolloClient.mutation(authenticateMutation).execute()
                    if (response.data == null || response.errors?.isNotEmpty() == true) {
                        throw Exception("Signup failed")
                    }
                    ResponseResource.Success(response.data!!.accountRegister)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ResponseResource.Error(exception = e)
                }
                postInput(AuthRepositoryContract.Inputs.UpdateSignupResponse(resource))
            }
        }
        is AuthRepositoryContract.Inputs.UpdateSignupResponse -> {
            updateState { it.copy(signupResponse = input.loginResponse) }
        }
        AuthRepositoryContract.Inputs.SignOut -> {
            sideJob("SignOut") {
                settingsRepository.saveAuthToken(null)
                settingsRepository.saveCsrfToken(null)
                settingsRepository.saveRefreshToken(null)
                settingsRepository.saveAuthChannel(null)
                settingsRepository.saveCheckoutToken(null)
            }
        }
        is AuthRepositoryContract.Inputs.GetMe -> {
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.me },
                updateState = { AuthRepositoryContract.Inputs.UpdateMe(it) },
                doFetch = {
                    apolloClient.query(CurrentUserDetailsQuery()).execute().data?.me!!
                }
            )
        }
        is AuthRepositoryContract.Inputs.UpdateMe -> {
            updateState { it.copy(me = input.me) }
        }
        is AuthRepositoryContract.Inputs.GetAddress -> {
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.address },
                updateState = { AuthRepositoryContract.Inputs.UpdateAddress(it) },
                doFetch = {
                    apolloClient.query(CurrentUserAddressesQuery()).execute().data?.me!!
                }
            )
        }
        is AuthRepositoryContract.Inputs.GetOrders -> {
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.orders },
                updateState = { AuthRepositoryContract.Inputs.UpdateOrders(it) },
                doFetch = {
                    apolloClient.query(OrdersQuery()).execute().data?.me!!
                }
            )
        }
        is AuthRepositoryContract.Inputs.UpdateAddress -> {
            updateState { it.copy(address = input.address) }
        }
        is AuthRepositoryContract.Inputs.UpdateOrders -> {
            updateState { it.copy(orders = input.orders) }
        }
        is AuthRepositoryContract.Inputs.RequestPasswordReset -> {
            sideJob("RequestPasswordReset") {
                if (settingsRepository.authToken == null) {
                    try {
                        val response = apolloClient.mutation(
                            RequestPasswordResetMutation(
                                channel = settingsRepository.channel ?: normalChannel,
                                email = input.email,
                                redirectUrl = "http://localhost:8080/#/reset-password"
                            )
                        ).execute()
                        println(response.data)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}
