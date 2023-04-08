package com.musafira2z.store.web.ui.product_details

import androidx.compose.runtime.*
import com.copperleaf.ballast.repository.cache.Cached
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.copperleaf.ballast.repository.cache.isLoading
import com.musafira2z.store.ui.app.AppContract
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.ui.product_details.ProductDetailContract
import com.musafira2z.store.web.ui.app.CartBar
import com.musafira2z.store.web.ui.components.CaretLeft
import com.musafira2z.store.web.ui.components.Spinner
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import com.musafira2z.store.web.ui.utils.toFormatPrice
import com.musafira2z.store.web.ui.utils.toUnDiscountFormatPrice
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.dom.*

@Composable
fun ProductDetailsScreen(
    injector: ComposeWebInjector,
    slug: String,
    variantId: String?,
    postAppInput: (AppContract.Inputs) -> Unit
) {
    val viewModelScope = rememberCoroutineScope()
    val vm: ProductDetailViewModel =
        remember(viewModelScope, slug) { injector.productDetailsViewModel(viewModelScope) }

    LaunchedEffect(vm) {
        vm.trySend(ProductDetailContract.Inputs.Initialize(slug = slug, variantId = variantId))
    }

    val uiState by vm.observeStates().collectAsState()

    DetailsContent(uiState = uiState, postAppInput = postAppInput) {
        vm.trySend(it)
    }
}

@Composable
fun DetailsContent(
    uiState: ProductDetailContract.State,
    postAppInput: (AppContract.Inputs) -> Unit,
    postInput: (ProductDetailContract.Inputs) -> Unit
) {

    Div {
        Div(attrs = {
            toClasses("container mx-auto mt-5")
        }) {
            Div(attrs = {
                toClasses("grid xl:grid-cols-2 lg:grid-cols-2 md:grid-cols-1 sm:grid-cols-1 grid-cols-1 gap-5")
            }) {

                if (uiState.product.isLoading() && uiState.product !is Cached.NotLoaded) {
                    Div(
                        attrs = {
                            classes("col-span-2")
                        }
                    ) {
                        Spinner()
                    }
                }

                uiState.product.getCachedOrNull()?.product?.productDetailsFragment?.let { _product ->
                    var selectedVariant by remember { mutableStateOf(_product.variants?.firstOrNull()) }
                    Div(
                        attrs = {
                            toClasses("col-span-1  border")
                        }
                    ) {
                        Div {
                            val image = _product.thumbnail?.imageFragment?.url?.replace(
                                "http://localhost:8000",
                                "https://musafirtd.sgp1.cdn.digitaloceanspaces.com"
                            )
                            Img(
                                src = image ?: "",
                                attrs = {
                                    classes("w-full")
                                },
                                alt = ""
                            )
                        }
                        Div(attrs = {
                            toClasses("flex justify-center py-5")
                        }) {
                            _product.media?.forEach {
                                val image = it.productMediaFragment.url.replace(
                                    "http://localhost:8000",
                                    "https://musafirtd.sgp1.cdn.digitaloceanspaces.com"
                                )
                                Div(
                                    attrs = {
                                        toClasses("border-green-500 w-24  border-2 rounded-lg p-2")
                                    }
                                ) {
                                    Img(
                                        src = image ?: "",
                                        alt = ""
                                    )
                                }
                            }
                        }
                    }
                    Div(
                        attrs = {
                            toClasses("col-span-1 bg-slate-50 p-5")
                        }
                    ) {
                        Div(attrs = {
                            toClasses("grid grid-cols-7")
                        }) {
                            Div(attrs = {
                                toClasses("col-span-4")
                            }) {
                                H1(attrs = {
                                    classes("text-lg", "font-bold")
                                }) {
                                    Text(_product.name)
                                }

                                _product.variants?.let { _variants ->
                                    if (_variants.size > 1) {
                                        Select(
                                            attrs = {
                                                toClasses("bg-slate-50 focus:ring-green-500 focus:ring-2   border-2 border-green-500 text-xl text-green-500  font-extrabold rounded-lg   block  w-24 p-2.5 ")
                                            }
                                        ) {
                                            _variants.forEach {
                                                Option(
                                                    value = it.productVariantDetailsFragment.id,
                                                    attrs = {
                                                        classes("text-green-500")
                                                        if (it.productVariantDetailsFragment.id == uiState.variantId) {
                                                            selected()
                                                        }
                                                    }
                                                ) {
                                                    Text(it.productVariantDetailsFragment.name)
                                                }
                                            }
                                        }
                                    }
                                }

                                /*  Div {
                                      //product description
                                      val parser = edjsParser(
                                          config = json(),
                                          customs = json(),
                                          embeds = json()
                                      )
                                      _product.description?.let {
                                          val html = parser.parse(buildJsonObject {
                                              it as String
                                          })
                                      }
                                  }*/

                                Div(attrs = {
                                    classes("text-base")
                                }) {
                                    P {
                                        Text("Supplied by Musafir")
                                    }
                                    P {
                                        Text("Supplier: Musafir")
                                    }
                                }
                            }
                            Div(attrs = {
                                toClasses("col-span-3 font-bold  text-lg  text-end")
                            }) {
                                selectedVariant?.productVariantDetailsFragment?.pricing?.price?.gross?.priceFragment.toUnDiscountFormatPrice(
                                    selectedVariant?.productVariantDetailsFragment?.pricing?.price?.gross?.priceFragment?.amount
                                )?.let {
                                    Div(attrs = {
                                        toClasses("flex text-red-500  justify-end items-center")
                                    }) {
                                        P(attrs = { toClasses("text-red-500 font-bold line-through  text-sm") }) {
                                            Text(it)
                                        }
                                    }
                                }

                                Div(attrs = {
                                    toClasses("flex text-red-500  justify-end items-center")
                                }) {
                                    P(attrs = { toClasses("text-green-500 font-bold  text-lg") }) {
                                        Text("${selectedVariant?.productVariantDetailsFragment?.pricing?.price?.gross?.priceFragment?.toFormatPrice()}")
                                    }
                                }

                                Div {
                                    Button(attrs = {
                                        toClasses("border-2 border-green-500 rounded-lg text-green-500 hover:bg-green-500 hover:text-slate-50 font-bold hover:duration-500 duration-500 w-full py-3 px-6")
                                        onClick {
                                            selectedVariant?.let {
                                                postInput(ProductDetailContract.Inputs.AddToCart(it.productVariantDetailsFragment.id))
                                            }
                                        }
                                    }) {
                                        Text("Add to Cart")
                                    }
                                }
                            }
                        }

                        Div(attrs = {
                            toClasses("border p-5 mt-5 bg-white")
                        }) {
                            Div(attrs = {
                                toClasses("flex justify-between")
                            }) {
                                P(attrs = {
                                    classes("text-lg")
                                }) {
                                    Text("Review (0)")
                                }
                                Button(attrs = {
                                    toClasses("px-10 text-lg  py-1 rounded-full")
                                }) {
                                    Text("See All")
                                }
                            }

                            Div(attrs = {
                                toClasses("grid grid-cols-12 mt-6 border p-5 bg-slate-50")
                            }) {
                                Div(attrs = {
                                    toClasses("col-span-1 flex items-center")
                                }) {
                                    Text("Icon")
                                }
                                Div(attrs = {
                                    toClasses("col-span-8 flex items-center")
                                }) {
                                    Div(
                                        attrs = {

                                        }
                                    ) {
                                        H4(attrs = {
                                            classes("font-bold")
                                        }) {
                                            Text("Md Abul Kalam Akhand")
                                        }
                                        P {
                                            Text("Good product Good product Good product Good product  Good product Good product Good product Good product   Good product Good product Good product Good product")
                                        }
                                    }
                                }
                                Div(attrs = {
                                    toClasses("col-span-3 flex justify-end items-center")
                                }) {
                                    P(
                                        attrs = {
                                            classes("font-sm")
                                        }
                                    ) {
                                        Text("Oct 2 ,2022")
                                    }
                                }
                            }
                        }
                    }
                }
            }

            /* Div(attrs = {
                 toClasses("mt-10 flex  justify-between px-5")
             }) {
                 H2(attrs = {
                     classes("font-bold", "text-lg")
                 }) {
                     Text("Related Items")
                 }
             }
             Div(attrs = {
                 classes("py-10")
             }) {
                 Div(attrs = {
                     toClasses("grid grid-cols-12 gap-5")
                 }) {
                     //products
                 }
             }*/
        }
    }

    //carts
    uiState.carts.getCachedOrNull()?.let { cart ->
        if (cart.lines.isNotEmpty()) {
            CartBar(
                cart = cart,
                onCheckout = {
                    postInput(ProductDetailContract.Inputs.GoCheckoutPage)
                }
            ) {
                postAppInput(it)
            }
        }
    }

}
