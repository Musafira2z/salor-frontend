package com.musafira2z.store.web.ui.app

import androidx.compose.runtime.*
import com.copperleaf.ballast.navigation.routing.*
import com.copperleaf.ballast.repository.cache.Cached
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.MainMenuQuery
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.ui.app.AppContract
import com.musafira2z.store.ui.app.AppViewModel
import com.musafira2z.store.web.ui.category.CategoryScreen
import com.musafira2z.store.web.ui.checkout.CheckoutPage
import com.musafira2z.store.web.ui.components.*
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.home.HomePage
import com.musafira2z.store.web.ui.router.WebPage
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
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

    //sidebar
    SideBar(categories = uiState.categories) {
        router.trySend(
            RouterContract.Inputs.ReplaceTopDestination(
                WebPage.Category.directions()
                    .path(it)
                    .build()
            )
        )
    }
    //content
    Div(attrs = {
        toClasses("md:ml-60  lg:ml-60 p-5")
    }) {
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

    //carts
    uiState.carts.getCachedOrNull()?.let { cart ->
        if (cart.lines.isNotEmpty()) {
            CartBar(cart = cart, onCheckout = {
                router.trySend(
                    RouterContract.Inputs.GoToDestination(
                        WebPage.Checkout.directions().build()
                    )
                )
            }) {
                vm.trySend(it)
            }
        }
    }
}

@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun SideBar(categories: Cached<MainMenuQuery.Data>, onCategoryClick: (String) -> Unit) {
    Aside(attrs = {
        id("logo-sidebar")
        classes(
            "fixed",
            "top-0",
            "left-0",
            "z-40",
            "w-64",
            "h-screen",
            "pt-28",
            "transition-transform",
            "-translate-x-full",
            "bg-white",
            "border-r",
            "border-gray-200",
            "sm:translate-x-0",
            "dark:bg-gray-800",
            "dark:border-gray-700"
        )
        attr("aria-label", "Sidebar")
    }) {
        Div(attrs = {
            classes("h-full", "px-3", "pb-4", "overflow-y-auto", "bg-white", "dark:bg-gray-800")
        }) {
            Div(attrs = {
                toClasses("flex justify-center mb-5")
            }) {
                Button(attrs = {
                    toClasses("w-full h-10 text-slate-50  text-lg font-extrabold bg-gradient-to-r from-yellow-300 rounded-lg to-pink-700")
                }) {
                    Text("Offer")
                }
            }
            Hr()
            Ul(attrs = {
                classes("space-y-2")
            }) {
                categories.getCachedOrNull()?.menu?.items?.forEach {
                    val category = it.menuItemWithChildrenFragment.category
                    Li {
                        A(
                            attrs = {
                                classes(
                                    "flex",
                                    "items-center",
                                    "p-2",
                                    "text-base",
                                    "font-normal",
                                    "text-gray-900",
                                    "rounded-lg",
                                    "dark:text-white",
                                    "hover:bg-gray-100",
                                    "dark:hover:bg-gray-700"
                                )
                                onClick {
                                    category?.slug?.let(onCategoryClick)
                                }
                            },
                        ) {
                            Img(
                                attrs = {
                                    attr("aria-hidden", "true")
                                    classes(
                                        "flex-shrink-0",
                                        "w-6",
                                        "h-6",
                                        "text-gray-500",
                                        "transition",
                                        "duration-75",
                                        "dark:text-gray-400",
                                        "group-hover:text-gray-900",
                                        "dark:group-hover:text-white"
                                    )
                                },
                                src = it.menuItemWithChildrenFragment.category?.backgroundImage?.url
                                    ?: ""
                            )
                            Span(attrs = {
                                classes("flex-1", "ml-3")
                            }) {
                                Text(it.menuItemWithChildrenFragment.name)
                            }
                        }

                    }
                }
            }
        }
    }
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