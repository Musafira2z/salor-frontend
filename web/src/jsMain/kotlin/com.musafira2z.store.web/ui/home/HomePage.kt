package com.musafira2z.store.web.ui.home

import androidx.compose.runtime.*
import com.copperleaf.ballast.repository.cache.Cached
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.copperleaf.ballast.repository.cache.isLoading
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.web.ui.components.*
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
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
        }
    }

    uiState.banners.getCachedOrNull()?.let {
        Carousal(banners = it)
    }
    //carousal
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
                                Product(product = _product, variant = it)
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
                SearchBox()
            }
        }

        val products = uiState.products.getCachedOrNull()
        products?.let {
            Products(it)
        }
    }
}