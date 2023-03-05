package com.musafira2z.store.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.plusAssign
import com.musafira2z.store.repository.settings.SettingsRepository
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val commonModule = module {
    factory {
        BallastViewModelConfiguration.Builder().apply {
            this += LoggingInterceptor()
//            this += BallastDebuggerInterceptor(get())
            logger = ::PrintlnLogger
        }
    }

    single {
        Settings()
    }

    single {
        SettingsRepository(get())
    }

    single {
        val httpInterceptor = object : HttpInterceptor {
            override suspend fun intercept(
                request: HttpRequest,
                chain: HttpInterceptorChain
            ): HttpResponse {
                val newRequest = request.newBuilder()
                    // remove apollo headers
                    .headers(request.headers.filter { !it.name.lowercase().startsWith("x-apollo") })
                    .build()
                return chain.proceed(newRequest)
            }
        }

        ApolloClient.Builder()
            .serverUrl("https://api.musafira2z.com/graphql/")
            .addHttpInterceptor(httpInterceptor)
            .build()
    }
}

val platformModule = module {

}

val appModule = listOf(commonModule, platformModule)