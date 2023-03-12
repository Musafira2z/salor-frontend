package com.musafira2z.store.web.ui.app

import androidx.compose.runtime.*
import com.copperleaf.ballast.navigation.routing.*
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.ui.app.AppContract
import com.musafira2z.store.ui.app.AppViewModel
import com.musafira2z.store.web.ui.category.CategoryScreen
import com.musafira2z.store.web.ui.checkout.CheckoutPage
import com.musafira2z.store.web.ui.components.CartBody
import com.musafira2z.store.web.ui.components.Drawer
import com.musafira2z.store.web.ui.components.shared.SideBar
import com.musafira2z.store.web.ui.components.shared.TopAppBar
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.home.HomePage
import com.musafira2z.store.web.ui.router.WebPage
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.dom.*

@Composable
fun AppScreen() {
    val applicationScope = rememberCoroutineScope()
    val injector: ComposeWebInjector = remember(applicationScope) {
        ComposeWebInjector(
            applicationScope,
            true,
            WebPage.HomePage
        )
    }

    val router = remember(injector) { injector.router() }
    val routerState by router.observeStates().collectAsState()
    val currentDestination = routerState.currentDestinationOrNull
    val currentRoute = routerState.currentRouteOrNull

    val vm: AppViewModel = remember(applicationScope) { injector.appViewModel(applicationScope) }

    LaunchedEffect(vm) {
        vm.trySend(AppContract.Inputs.Initialize)
    }

    val uiState by vm.observeStates().collectAsState()

    //TopAppBar
    TopAppBar(
        isLoggedIn = uiState.isLoggedIn,
        loginResponse = uiState.loginResponse,
        signupResponse = uiState.signupResponse
    ) {
        vm.trySend(it)
    }

    //content
    routerState.renderCurrentDestination(
        route = {
            when (it) {
                WebPage.HomePage -> {
                    HomePage(webInjector = injector)
                }
                WebPage.Category -> {
                    val slug: String by stringPath()
                    CategoryScreen(webInjector = injector, slug = slug)
                }
                WebPage.Checkout -> {
                    CheckoutPage(injector = injector)
                }
            }
        },
        notFound = {
            Text("Not found")
        }
    )
}

@Composable
fun CartBar(
    cart: CheckoutDetailsFragment,
    onCheckout: () -> Unit,
    postInput: (AppContract.Inputs) -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }
    Div {
        if (!isOpen) {
            Div(attrs = {
                classes(
                    "bg-green-500",
                    "h-[7rem]",
                    "w-[7rem]",
                    "rounded-lg",
                    "rounded-r-none",
                    "z-30",
                    "fixed",
                    "top-1/2",
                    "right-0",
                    "flex",
                    "flex-col",
                    "justify-between",
                    "p-2",
                    "cursor-pointer",
                    "select-none"
                )
                onClick {
                    isOpen = true
                }
            }) {
                P(attrs = {
                    classes("text-slate-50", "text-sm", "md:text-xl", "text-center", "font-bold")
                }) {
                    Text("${cart.lines.size} Items")
                }
                Button(attrs = {
                    toClasses("bg-slate-50 text-slate-800 text-sm md:text-base py-2 px-6 rounded-lg font-bold")
                }) {
                    Text("৳${cart.totalPrice.gross.priceFragment.amount}")
                }
            }
        }

        Drawer(isOpen = isOpen) {
            CartBody(
                onClose = {
                    isOpen = false
                },
                cart = cart,
                onDecrement = {
                    postInput(AppContract.Inputs.Decrement(it))
                },
                onIncrement = {
                    postInput(AppContract.Inputs.Increment(it))
                }
            ) {
                A(
                    attrs = {
                        toClasses("col-span-6 w-full text-center text-slate-50 hover:text-slate-50 active:text-slate-50 focus:text-slate-50")
                        onClick {
                            it.preventDefault()
                            isOpen = false
                            onCheckout()
                        }
                    },
                    href = "#",
                ) {
                    Button {
                        Text("Checkout")
                    }
                }
            }
        }
    }
}