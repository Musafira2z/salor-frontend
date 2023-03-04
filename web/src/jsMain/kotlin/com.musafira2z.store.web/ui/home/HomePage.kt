package com.musafira2z.store.web.ui.home

import androidx.compose.runtime.*
import com.copperleaf.ballast.repository.cache.Cached
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.copperleaf.ballast.repository.cache.isLoading
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.ui.home.HomeViewModel
import com.musafira2z.store.web.ui.components.Carousal
import com.musafira2z.store.web.ui.components.Products
import com.musafira2z.store.web.ui.components.SearchBox
import com.musafira2z.store.web.ui.components.Spinner
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

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
    //carousal
    Carousal()
    Div(attrs = {
        toClasses("container mx-auto")
    }) {
        if (uiState.products.isLoading() && uiState.products !is Cached.NotLoaded) {
            Spinner()
        }
        Div(attrs = {
            toClasses("text-start")
        }) {
            H1(attrs = {
                classes("font-bold", "pt-5", "text-xl")
            }) {
                Text("Popular Product")
                uiState.blocks.getCachedOrNull()?.let {
                    Text("${it.menu?.items?.size}")
                }
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