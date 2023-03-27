package com.musafira2z.store.web.ui.product_details

import androidx.compose.runtime.*
import com.musafira2z.store.ui.product_details.ProductDetailContract
import com.musafira2z.store.web.ui.components.TbCurrencyTaka
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.dom.*

@Composable
fun ProductDetailsScreen(
    injector: ComposeWebInjector,
    slug: String,
    variantId: String?
) {
    val viewModelScope = rememberCoroutineScope()
    val vm: ProductDetailViewModel =
        remember(viewModelScope, slug) { injector.productDetailsViewModel(viewModelScope) }

    LaunchedEffect(vm) {
        vm.trySend(ProductDetailContract.Inputs.Initialize(slug = slug, variantId = variantId))
    }

    val uiState by vm.observeStates().collectAsState()

    DetailsContent(uiState = uiState) {
        vm.trySend(it)
    }
}

@Composable
fun DetailsContent(
    uiState: ProductDetailContract.State,
    postInput: (ProductDetailContract.Inputs) -> Unit
) {
    Div {
        Div(attrs = {
            toClasses("container mx-auto mt-5")
        }) {
            Div(attrs = {
                toClasses("relative top-4")
            }) {
                Text("Back button")
            }
            Div(attrs = {
                toClasses("grid xl:grid-cols-2 lg:grid-cols-2 md:grid-cols-1 sm:grid-cols-1 grid-cols-1 gap-5")
            }) {
                Div(
                    attrs = {
                        toClasses("col-span-1  border")
                    }
                ) {
                    Div {
                        Img(
                            src = "https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/thumbnails/products/8941100513194.webp",
                            attrs = {
                                classes("w-full")
                            },
                            alt = ""
                        )
                    }
                    Div(attrs = {
                        toClasses("flex justify-center py-5")
                    }) {
                        Div(
                            attrs = {
                                toClasses("border-green-500 w-24  border-2 rounded-lg p-2")
                            }
                        ) {
                            Img(
                                src = "https://shatkora.sgp1.cdn.digitaloceanspaces.com/media/thumbnails/products/8941100513194.webp",
                                alt = ""
                            )
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
                                Text("Aci Disinfectant Spray 300ml + ( Savlon Hand Rub Free )")
                            }
                            Select(
                                attrs = {
                                    toClasses("bg-slate-50 focus:ring-green-500 focus:ring-2   border-2 border-green-500 text-xl text-green-500  font-extrabold rounded-lg   block  w-24 p-2.5 ")
                                }
                            ) {
                                Option(value = "Ac", attrs = {
                                    classes("text-green-500")
                                }) {
                                    Text("1 pc")
                                }
                            }
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
                            Div(attrs = {
                                toClasses("flex text-red-500  justify-end items-center")
                            }) {
                                TbCurrencyTaka()
                                P(attrs = { toClasses("text-red-500 font-bold line-through  text-sm") }) {
                                    Text("350.00")
                                }
                            }
                            Div(attrs = {
                                toClasses("flex text-red-500  justify-end items-center")
                            }) {
                                TbCurrencyTaka()
                                P(attrs = { toClasses("text-green-500 font-bold  text-lg") }) {
                                    Text("350.00")
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

            Div(attrs = {
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
            }
        }
    }
}
