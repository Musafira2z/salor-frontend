package com.musafira2z.store.web.ui.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.ui.category.CategoryContract
import com.musafira2z.store.web.ui.app.CartBar
import com.musafira2z.store.web.ui.components.Products
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.events.EventListener
import kotlin.math.floor

@Composable
fun CategoryScreen(
    webInjector: ComposeWebInjector,
    slug: String
) {
    val viewModelScope = rememberCoroutineScope()
    val vm: CategoryViewModel =
        remember(viewModelScope, slug) { webInjector.categoryViewModel(viewModelScope) }

    LaunchedEffect(vm) {
        vm.trySend(CategoryContract.Inputs.Initialize(slug = slug))
    }

    val uiState by vm.observeStates().collectAsState()

    CategoryContent(uiState = uiState) {
        vm.trySend(it)
    }
}

@Composable
fun CategoryContent(
    uiState: CategoryContract.State,
    postInput: (CategoryContract.Inputs) -> Unit
) {

    val onScroll = EventListener {
        val documentHeight = document.documentElement?.scrollHeight
        val scrollDifference = floor(window.innerHeight + window.scrollY).toInt()
        val scrollEnded = documentHeight == scrollDifference

        if (scrollEnded) {
            println("load more...")
            postInput(CategoryContract.Inputs.GetProductByCategory(uiState.slug, true))
        }
    }

    Div(attrs = {
        toClasses("md:ml-60 lg:ml-60 p-5")
    }) {
        Div(attrs = {
            toClasses("container mx-auto")
        }) {
            uiState.category?.let {
                Div(attrs = {
                    toClasses("md:ml-60 lg:ml-60 p-5")
                }) {
                    Div(attrs = {
                        toClasses("text-start")
                    }) {
                        H1(attrs = {
                            classes("font-bold", "pt-5", "text-xl")
                        }) {
                            Text(it.title)
                        }
                    }
                }

                //categories
                if (it.child.isNotEmpty()) {
                    Div(attrs = {
                        classes("py-10")
                    }) {
                        Div(attrs = {
                            toClasses("grid grid-cols-12 gap-5")
                        }) {
                            it.child.forEach { menuItem ->
                                Div(
                                    attrs = {
                                        classes(
                                            "col-span-12",
                                            "sm:col-span-6",
                                            "md:col-span-6",
                                            "lg:col-span-3",
                                            "border",
                                            "p-5",
                                            "rounded-lg",
                                            "hover:shadow-green-200",
                                            "relative",
                                            "hover:no-underline",
                                            "hover:text-slate-700",
                                            "shadow-lg",
                                            "hover:shadow-xl",
                                            "hover:transform",
                                            "hover:scale-105",
                                            "duration-300",
                                            "h-fit"
                                        )
                                        onClick {
                                            menuItem.slug.let {
                                                postInput(
                                                    CategoryContract.Inputs.GoCategoryPage(
                                                        slug = it
                                                    )
                                                )
                                            }
                                        }
                                    }
                                ) {
                                    Div {
                                        Div(attrs = {
                                            classes(
                                                "flex",
                                                "justify-center",
                                                "w-full",
                                                "aspect-square"
                                            )
                                        }) {
                                            val image =
                                                menuItem.image?.replace(
                                                    "http://localhost:8000",
                                                    "https://musafirtd.sgp1.cdn.digitaloceanspaces.com"
                                                )
                                            Img(
                                                src = image
                                                    ?: "",
                                                alt = "",
                                            )
                                        }
                                        H1(attrs = {
                                            classes(
                                                "text-lg",
                                                "py-7",
                                                "truncate",
                                                "hover:text-clip"
                                            )
                                        }) {
                                            Text(menuItem.title)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    DisposableEffect(Unit) {
                        println("adding listener...")
                        window.addEventListener("scroll", callback = onScroll)
                        onDispose {
                            println("removing listener...")
                            window.removeEventListener("scroll", onScroll)
                        }
                    }
                    //products
                    Products(
                        products = uiState.allProducts,
                        onProductDetails = { productSlug, variantId ->
                            postInput(
                                CategoryContract.Inputs.GoProductDetailsPage(
                                    slug = productSlug,
                                    variantId = variantId
                                )
                            )
                        }
                    ) {
                        //add to cart
                        postInput(CategoryContract.Inputs.AddToCart(it))
                    }
                }
            }
        }
    }

    //carts
    uiState.carts.getCachedOrNull()?.let { cart ->
        if (cart.lines.isNotEmpty()) {
            CartBar(cart = cart, onCheckout = {
                postInput(CategoryContract.Inputs.GoCheckoutPage)
            }) {

            }
        }
    }
}