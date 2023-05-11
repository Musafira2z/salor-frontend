package com.musafira2z.store.web.ui.app

import androidx.compose.runtime.*
import com.copperleaf.ballast.navigation.routing.*
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.ui.app.AppContract
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.web.ui.category.CategoryScreen
import com.musafira2z.store.web.ui.checkout.CheckoutPage
import com.musafira2z.store.web.ui.components.CartBody
import com.musafira2z.store.web.ui.components.Drawer
import com.musafira2z.store.web.ui.components.shared.SearchBox
import com.musafira2z.store.web.ui.components.shared.SideBar
import com.musafira2z.store.web.ui.components.shared.TopAppBar
import com.musafira2z.store.web.ui.dashboard.orders.OrdersPage
import com.musafira2z.store.web.ui.dashboard.proifle.ProfileScreen
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.home.HomePage
import com.musafira2z.store.web.ui.page.PageScreen
import com.musafira2z.store.web.ui.product_details.ProductDetailsScreen
import com.musafira2z.store.web.ui.router.WebPage
import com.musafira2z.store.web.ui.search.SearchPage
import com.musafira2z.store.web.ui.success.OrderSuccessPage
import com.musafira2z.store.web.ui.utils.toClasses
import com.musafira2z.store.web.ui.utils.toFormatPrice
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLButtonElement

@Composable
fun AppScreen() {
    val applicationScope = rememberCoroutineScope()
    val injector: ComposeWebInjector = remember(applicationScope) {
        ComposeWebInjector(
            applicationScope, true, WebPage.HomePage
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
    val me = uiState.me.getCachedOrNull()

    if (currentRoute != WebPage.Search) {
        //TopAppBar
        TopAppBar(
            isLoggedIn = uiState.isLoggedIn,
            loginResponse = uiState.loginResponse,
            signupResponse = uiState.signupResponse,
            searchBox = {
                SearchBox(onSearch = { term, isSubmit ->
                    if (isSubmit) {
                        vm.trySend(AppContract.Inputs.GoSearchPage(term))
                    }
                })
            },
            name = me?.firstName,
            email = me?.email
        ) {
            vm.trySend(it)
        }
    }

    if (currentRoute == WebPage.HomePage || currentRoute == WebPage.Category) {
        //sidebar
        SideBar {
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
                uiState.categories.getCachedOrNull()?.menu?.items?.forEach {
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
                                    //remove backdrop
                                    //remove overflow
//                                val backDrop = document.querySelector("div[drawer-backdrop]")
//                                backDrop?.remove()
//                                document.body?.classList?.remove("overflow-hidden")

                                    val mobileMenu =
                                        document.getElementById("mobile-menu") as HTMLButtonElement?
                                    mobileMenu?.click()

                                    category?.slug?.let {
                                        vm.trySend(AppContract.Inputs.GoCategoryPage(slug = it))
                                    }
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

    //content
    routerState.renderCurrentDestination(route = {
        when (it) {
            WebPage.HomePage -> {
                HomePage(webInjector = injector, postAppInput = {
                    vm.trySend(it)
                })
            }

            WebPage.Category -> {
                val slug: String by stringPath()
                CategoryScreen(webInjector = injector, slug = slug)
            }

            WebPage.Checkout -> {
                CheckoutPage(injector = injector)
            }

            WebPage.ProductDetails -> {
                val slug: String by stringPath()
                val variantId: String? by optionalStringQuery()

                ProductDetailsScreen(injector = injector, slug = slug, variantId = variantId) {
                    vm.trySend(it)
                }
            }

            WebPage.Search -> {
                val filter: String? by optionalStringQuery()
                var search by remember { mutableStateOf(filter ?: "") }

                TopAppBar(
                    isLoggedIn = uiState.isLoggedIn,
                    loginResponse = uiState.loginResponse,
                    signupResponse = uiState.signupResponse,
                    searchBox = {
                        SearchBox(
                            onSearch = { term, isSubmit ->
                                search = term
                            }, initial = filter ?: ""
                        )
                    },
                    name = me?.firstName,
                    email = me?.email
                ) {
                    vm.trySend(it)
                }

                SearchPage(
                    webInjector = injector,
                    search = search
                )
            }

            WebPage.Page -> {
                val slug by stringPath()
                PageScreen(webInjector = injector, slug = slug)
            }

            WebPage.PasswordReset -> {
                val email by optionalStringQuery()
                val token by optionalStringQuery()

                Text("$email \n $token")
            }

            WebPage.Profile -> {
                ProfileScreen(webInjector = injector)
            }

            WebPage.Orders -> {
                OrdersPage(webInjector = injector)
            }

            WebPage.OrderSuccess -> {
                val slug by stringPath()
                OrderSuccessPage(webInjector = injector, slug = slug)
            }

            WebPage.OrderDetails -> {

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
                    Text(cart.totalPrice.gross.priceFragment.toFormatPrice())
                }
            }
        }

        Drawer(isOpen = isOpen) {
            CartBody(
                onClose = {
                    isOpen = false
                },
                cart = cart,
                onDecrement = { variantId, qty ->
                    postInput(AppContract.Inputs.Decrement(variantId, qty))
                },
                onIncrement = {
                    postInput(AppContract.Inputs.Increment(it))
                },
                removeLine = {
                    postInput(AppContract.Inputs.RemoveLine(it))
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