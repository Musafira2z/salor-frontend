package com.musafira2z.store.web.ui.category

import androidx.compose.runtime.*
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.ui.category.CategoryContract
import com.musafira2z.store.web.ui.components.AddToCartButton
import com.musafira2z.store.web.ui.components.Product
import com.musafira2z.store.web.ui.components.SearchBox
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import com.musafira2z.store.web.ui.utils.toFormatPrice
import com.musafira2z.store.web.ui.utils.toUnDiscountFormatPrice
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
    uiState.category?.let {
        Div(attrs = {
            toClasses("container mx-auto")
        }) {
            Div(attrs = {
                toClasses("text-start")
            }) {
                H1(attrs = {
                    classes("font-bold", "pt-5", "text-xl")
                }) {
                    Text(it.name)
                }
            }
        }

        if (it.children.isNullOrEmpty()) {

        } else {
            Div(attrs = {
                classes("py-10")
            }) {
                Div(attrs = {
                    toClasses("grid grid-cols-12 gap-5")
                }) {
                    it.children?.forEach {
                        Div(attrs = {
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
                        }) {
                            Div {
                                Div(attrs = {
                                    classes("flex", "justify-center", "w-full", "aspect-square")
                                }) {
                                    val image =
                                        it.menuItemFragment.category?.backgroundImage?.url?.replace(
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
                                    classes("text-lg", "py-7", "truncate", "hover:text-clip")
                                }) {
                                    Text(it.menuItemFragment.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}