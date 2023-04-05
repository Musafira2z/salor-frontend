package com.musafira2z.store.web.ui.home

import androidx.compose.runtime.*
import com.copperleaf.ballast.repository.cache.Cached
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.copperleaf.ballast.repository.cache.isLoading
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.web.ui.app.CartBar
import com.musafira2z.store.web.ui.components.Carousal
import com.musafira2z.store.web.ui.components.Product
import com.musafira2z.store.web.ui.components.Products
import com.musafira2z.store.web.ui.components.Spinner
import com.musafira2z.store.web.ui.components.shared.SearchBox
import com.musafira2z.store.web.ui.components.shared.SideBar
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.events.EventListener
import kotlin.math.floor

@Composable
fun HomePage(
    webInjector: ComposeWebInjector
) {
    val viewModelScope = rememberCoroutineScope()
    val vm: HomeViewModel = remember(viewModelScope) { webInjector.homeViewModel(viewModelScope) }
    LaunchedEffect(vm) {
        vm.trySend(HomeContract.Inputs.Initialize)
    }

    val uiState by vm.observeStates().collectAsState()

    HomePageContent(uiState) {
        vm.trySend(it)
    }
}

@Composable
fun HomePageContent(
    uiState: HomeContract.State,
    postInput: (HomeContract.Inputs) -> Unit
) {
    val onScroll = EventListener {
        val documentHeight = document.documentElement?.scrollHeight
        val scrollDifference = floor(window.innerHeight + window.scrollY).toInt()
        val scrollEnded = documentHeight == scrollDifference

        if (scrollEnded) {
            println("load more...")
            postInput(HomeContract.Inputs.FetchHomeProducts(true))
        }
    }

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
                                category?.slug?.let {
                                    postInput(HomeContract.Inputs.GoCategoryPage(slug = it))
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

    Div(attrs = {
        toClasses("md:ml-60 lg:ml-60 p-5")
    }) {
        uiState.banners.getCachedOrNull()?.let {
            Carousal(banners = it)
        }
        Div(attrs = {
            toClasses("container mx-auto")
        }) {
            DisposableEffect(Unit) {
                println("adding listener...")
                window.addEventListener("scroll", callback = onScroll)
                onDispose {
                    println("removing listener...")
                    window.removeEventListener("scroll", onScroll)
                }
            }

            if (uiState.products.isLoading() && uiState.products !is Cached.NotLoaded) {
                Spinner()
            }

            uiState.blocks.getCachedOrNull()?.let {
                it.menu?.items?.forEachIndexed { index, item ->
                    Div(attrs = {
                        toClasses("text-start")
                    }) {
                        H1(attrs = {
                            classes("font-bold", "pt-5", "text-xl")
                        }) {
                            Text(item.menuItemWithChildrenFragmentProducts.name)
                        }
                    }

                    Div(attrs = {
                        classes("py-10")
                    }) {
                        Div(attrs = {
                            toClasses("grid grid-cols-12 gap-5")
                        }) {
                            item.menuItemWithChildrenFragmentProducts.category?.products?.edges?.forEach { _product ->
                                _product.node.productDetailsFragment.variants?.forEach {
                                    Product(
                                        product = _product,
                                        variant = it,
                                        onProductDetails = { productSlug, variantId ->
                                            postInput(
                                                HomeContract.Inputs.GoProductDetailsPage(
                                                    slug = productSlug,
                                                    variantId = variantId
                                                )
                                            )
                                        }
                                    ) {
                                        postInput(HomeContract.Inputs.AddToCart(it))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Div(attrs = {
                toClasses("text-start")
            }) {
                H1(attrs = {
                    classes("font-bold", "pt-5", "text-xl")
                }) {
                    Text("Popular Product")
                }
                Div(attrs = {
                    classes("lg:hidden")
                }) {
                    SearchBox { term, isSubmit ->

                    }
                }
            }

            Products(
                uiState.allProducts,
                onProductDetails = { productSlug, variantId ->
                    postInput(
                        HomeContract.Inputs.GoProductDetailsPage(
                            slug = productSlug,
                            variantId = variantId
                        )
                    )
                }
            ) {
                postInput(HomeContract.Inputs.AddToCart(it))
            }
        }
    }

    //carts
    uiState.carts.getCachedOrNull()?.let { cart ->
        if (cart.lines.isNotEmpty()) {
            CartBar(cart = cart, onCheckout = {
                postInput(HomeContract.Inputs.GoCheckoutPage)
            }) {

            }
        }
    }
}