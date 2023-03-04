package com.musafira2z.store.web.ui.di

import com.copperleaf.ballast.*
import com.copperleaf.ballast.core.JsConsoleBallastLogger
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.debugger.BallastDebuggerClientConnection
import com.copperleaf.ballast.debugger.BallastDebuggerInterceptor
import com.copperleaf.ballast.navigation.browser.BrowserHashNavigationInterceptor
import com.copperleaf.ballast.navigation.routing.RoutingTable
import com.copperleaf.ballast.navigation.routing.fromEnum
import com.copperleaf.ballast.navigation.vm.withRouter
import com.copperleaf.ballast.repository.bus.EventBusImpl
import com.copperleaf.ballast.repository.withRepository
import com.musafira2z.store.repository.cart.CartRepositoryContract
import com.musafira2z.store.repository.cart.CartRepositoryImpl
import com.musafira2z.store.repository.cart.CartRepositoryInputHandler
import com.musafira2z.store.repository.menu.MenuRepositoryContract
import com.musafira2z.store.repository.menu.MenuRepositoryImpl
import com.musafira2z.store.repository.menu.MenuRepositoryInputHandler
import com.musafira2z.store.repository.product.ProductRepositoryContract
import com.musafira2z.store.repository.product.ProductRepositoryImpl
import com.musafira2z.store.repository.product.ProductRepositoryInputHandler
import com.musafira2z.store.ui.app.AppContract
import com.musafira2z.store.ui.app.AppEventHandler
import com.musafira2z.store.ui.app.AppInputHandler
import com.musafira2z.store.ui.app.AppViewModel
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.ui.home.HomeEventHandler
import com.musafira2z.store.ui.home.HomeInputHandler
import com.musafira2z.store.ui.home.HomeViewModel
import com.musafira2z.store.web.ui.router.WebPage
import com.musafira2z.store.web.ui.router.WebPagerRouter
import io.ktor.client.engine.js.*
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class ComposeWebInjector(
    private val applicationScope: CoroutineScope,
    private val useBrowserHashes: Boolean,
    private val initialRoute: WebPage,
) : KoinComponent {
    @OptIn(ExperimentalBallastApi::class)
    private val router by lazy {
        WebPagerRouter(
            viewModelCoroutineScope = applicationScope,
            config = commonBuilder()
                .withRouter(RoutingTable.fromEnum(WebPage.values()), null)
                .apply {
                    if (useBrowserHashes) {
                        interceptors += BrowserHashNavigationInterceptor<WebPage>(
                            initialRoute
                        )
                    }
                }
                .build(),
        )
    }

    private val eventBus = EventBusImpl()

    fun router(): WebPagerRouter {
        return router
    }

    private val cartRepository by lazy {
        CartRepositoryImpl(
            coroutineScope = applicationScope,
            eventBus = eventBus,
            configBuilder = commonBuilder()
                .withViewModel(
                    inputHandler = CartRepositoryInputHandler(
                        eventBus = eventBus,
                        apolloClient = get()
                    ),
                    initialState = CartRepositoryContract.State(),
                    name = "Cart Repository",
                )
                .withRepository(),
        )
    }

    private val menuRepository by lazy {
        MenuRepositoryImpl(
            coroutineScope = applicationScope,
            eventBus = eventBus,
            configBuilder = commonBuilder()
                .withViewModel(
                    inputHandler = MenuRepositoryInputHandler(
                        eventBus = eventBus, apolloClient = get()
                    ),
                    initialState = MenuRepositoryContract.State(),
                    name = "Menu Repository",
                )
                .withRepository(),
        )
    }

    private val productRepository by lazy {
        ProductRepositoryImpl(
            coroutineScope = applicationScope,
            eventBus = eventBus,
            configBuilder = commonBuilder()
                .withViewModel(
                    inputHandler = ProductRepositoryInputHandler(
                        eventBus = eventBus, apolloClient = get()
                    ),
                    initialState = ProductRepositoryContract.State(),
                    name = "Product Repository",
                )
                .withRepository(),
        )
    }

    fun appViewModel(coroutineScope: CoroutineScope): AppViewModel {
        return AppViewModel(
            coroutineScope = coroutineScope,
            configBuilder = commonBuilder().withViewModel(
                initialState = AppContract.State(),
                inputHandler = AppInputHandler(
                    cartRepository = cartRepository
                ),
                name = "AppScreen"
            ),
            eventHandler = AppEventHandler()
        )
    }

    fun homeViewModel(coroutineScope: CoroutineScope): HomeViewModel {
        return HomeViewModel(
            coroutineScope = coroutineScope,
            configBuilder = commonBuilder()
                .withViewModel(
                    initialState = HomeContract.State(),
                    inputHandler = HomeInputHandler(
                        menuRepository = menuRepository,
                        productRepository = productRepository,
                        cartRepository = cartRepository
                    ),
                    name = "HomeScreen"
                ),
            eventHandler = HomeEventHandler()
        )
    }

    private val debuggerConnection by lazy {
        BallastDebuggerClientConnection(
            engineFactory = Js,
            applicationCoroutineScope = applicationScope,
            host = "127.0.0.1",
        ).also {
            it.connect()
        }
    }

    private fun commonBuilder(): BallastViewModelConfiguration.Builder {
        return BallastViewModelConfiguration.Builder()
            .apply {
                this += LoggingInterceptor()
                this += BallastDebuggerInterceptor(debuggerConnection)
                logger = ::JsConsoleBallastLogger
            }
    }
}