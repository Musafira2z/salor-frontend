package com.musafira2z.store.web.ui.category

import androidx.compose.runtime.*
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.ui.category.CategoryContract
import com.musafira2z.store.web.ui.app.CartBar
import com.musafira2z.store.web.ui.components.Products
import com.musafira2z.store.web.ui.components.shared.SideBar
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.dom.*

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
                                    postInput(CategoryContract.Inputs.GoCategoryPage(slug = it))
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
                    //products
                    uiState.products.getCachedOrNull()?.let {
                        Products(
                            products = it,
                            onProductDetails = { productSlug, variantId ->

                            }
                        ) {
                            //add to cart
                            postInput(CategoryContract.Inputs.AddToCart(it))
                        }
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