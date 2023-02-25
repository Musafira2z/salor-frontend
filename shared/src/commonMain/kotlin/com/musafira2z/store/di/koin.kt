package com.musafira2z.store.di

import com.apollographql.apollo3.ApolloClient
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.debugger.BallastDebuggerClientConnection
import com.copperleaf.ballast.debugger.BallastDebuggerInterceptor
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.EventBusImpl
import com.musafira2z.store.httpClient
import com.musafira2z.store.repository.menu.MenuRepository
import com.musafira2z.store.repository.menu.MenuRepositoryImpl
import com.musafira2z.store.repository.menu.MenuRepositoryInputHandler
import com.musafira2z.store.ui.home.HomeEventHandler
import com.musafira2z.store.ui.home.HomeInputHandler
import com.musafira2z.store.ui.home.HomeViewModel
import org.koin.dsl.module

val commonModule = module {

//    factory {
//        BallastDebuggerClientConnection(
//            engineFactory = ,
//            applicationCoroutineScope = get(),
//            host = "127.0.0.1",
//        ).also {
//            it.connect()
//        }
//    }

    factory {
        BallastViewModelConfiguration.Builder().apply {
            this += LoggingInterceptor()
//            this += BallastDebuggerInterceptor(get())
            logger = ::PrintlnLogger
        }
    }

    single {
        ApolloClient.Builder()
            .serverUrl("https://api.musafira2z.com/graphql/")
            .build()
    }
    single<EventBus> { EventBusImpl() }
    factory<MenuRepository> {
        MenuRepositoryImpl(
            coroutineScope = get(),
            eventBus = get(),
            configBuilder = get(),
            inputHandler = MenuRepositoryInputHandler(get(), get())
        )
    }

    factory { HomeInputHandler(get()) }
    factory { HomeEventHandler() }
    factory { parametersHolder ->
        HomeViewModel(parametersHolder.get(), get(), get())
    }

}

val platformModule = module {

}

val appModule = listOf(commonModule, platformModule)