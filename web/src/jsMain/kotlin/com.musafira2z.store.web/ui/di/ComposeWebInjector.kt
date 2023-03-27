package com.musafira2z.store.web.ui.di

import com.copperleaf.ballast.*
import com.copperleaf.ballast.debugger.BallastDebuggerClientConnection
import com.copperleaf.ballast.navigation.browser.BrowserHashNavigationInterceptor
import com.copperleaf.ballast.navigation.routing.RoutingTable
import com.copperleaf.ballast.navigation.routing.fromEnum
import com.copperleaf.ballast.navigation.vm.withRouter
import com.copperleaf.ballast.repository.bus.EventBusImpl
import com.copperleaf.ballast.repository.withRepository
import com.musafira2z.store.repository.auth.AuthRepositoryContract
import com.musafira2z.store.repository.auth.AuthRepositoryImpl
import com.musafira2z.store.repository.auth.AuthRepositoryInputHandler
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
import com.musafira2z.store.ui.category.CategoryContract
import com.musafira2z.store.ui.category.CategoryInputHandler
import com.musafira2z.store.ui.checkout.CheckoutContract
import com.musafira2z.store.ui.checkout.CheckoutInputHandler
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.ui.home.HomeInputHandler
import com.musafira2z.store.ui.product_details.ProductDetailContract
import com.musafira2z.store.ui.product_details.ProductDetailInputHandler
import com.musafira2z.store.web.ui.category.CategoryEventHandler
import com.musafira2z.store.web.ui.category.CategoryViewModel
import com.musafira2z.store.web.ui.checkout.CheckoutEventHandler
import com.musafira2z.store.web.ui.checkout.CheckoutViewModel
import com.musafira2z.store.web.ui.home.HomeEventHandler
import com.musafira2z.store.web.ui.home.HomeViewModel
import com.musafira2z.store.web.ui.product_details.ProductDetailEventHandler
import com.musafira2z.store.web.ui.product_details.ProductDetailViewModel
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
                        apolloClient = get(),
                        settingsRepository = get()
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
                        eventBus = eventBus, apolloClient = get(), settingsRepository = get()
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
                        eventBus = eventBus, apolloClient = get(), settingsRepository = get()
                    ),
                    initialState = ProductRepositoryContract.State(),
                    name = "Product Repository",
                )
                .withRepository(),
        )
    }

    private val authRepository by lazy {
        AuthRepositoryImpl(
            coroutineScope = applicationScope,
            eventBus = eventBus,
            configBuilder = commonBuilder()
                .withViewModel(
                    inputHandler = AuthRepositoryInputHandler(
                        eventBus = eventBus,
                        apolloClient = get(),
                        settingsRepository = get()
                    ),
                    initialState = AuthRepositoryContract.State(),
                    name = "Auth Repository",
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
                    cartRepository = cartRepository,
                    menuRepository = menuRepository,
                    authRepository = authRepository,
                    settingsRepository = get()
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
            eventHandler = HomeEventHandler(router = router)
        )
    }

    fun categoryViewModel(coroutineScope: CoroutineScope): CategoryViewModel {
        return CategoryViewModel(
            coroutineScope = coroutineScope,
            configBuilder = commonBuilder()
                .withViewModel(
                    initialState = CategoryContract.State(),
                    inputHandler = CategoryInputHandler(
                        menuRepository = menuRepository,
                        productRepository = productRepository,
                        cartRepository = cartRepository
                    ),
                    name = "CategoryScreen"
                ),
            categoryEventHandler = CategoryEventHandler(router = router)
        )
    }

    fun productDetailsViewModel(coroutineScope: CoroutineScope): ProductDetailViewModel {
        return ProductDetailViewModel(
            coroutineScope = coroutineScope,
            configBuilder = commonBuilder()
                .withViewModel(
                    initialState = ProductDetailContract.State(),
                    inputHandler = ProductDetailInputHandler(
                        productRepository = productRepository,
                        cartRepository = cartRepository
                    ),
                    name = "CategoryScreen"
                ),
            eventHandler = ProductDetailEventHandler(router = router)
        )
    }

    fun checkoutViewModel(coroutineScope: CoroutineScope): CheckoutViewModel {
        return CheckoutViewModel(
            coroutineScope = coroutineScope,
            configBuilder = commonBuilder()
                .withViewModel(
                    initialState = CheckoutContract.State(),
                    inputHandler = CheckoutInputHandler(
                        cartRepository = cartRepository,
                        authRepository = authRepository
                    ),
                    name = "CategoryScreen"
                ),
            eventHandler = CheckoutEventHandler(router = router)
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
//                this += LoggingInterceptor()
//                this += BallastDebuggerInterceptor(debuggerConnection)
//                logger = ::JsConsoleBallastLogger
            }
    }
}