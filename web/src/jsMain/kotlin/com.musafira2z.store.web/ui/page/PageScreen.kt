package com.musafira2z.store.web.ui.page

import androidx.compose.runtime.*
import com.musafira2z.store.ui.page.PageContract
import com.musafira2z.store.utils.ResponseResource
import com.musafira2z.store.web.ui.components.Spinner
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import editorjs.ETResponse
import editorjs.EditorJs
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.dom.Div

@Composable
fun PageScreen(
    webInjector: ComposeWebInjector,
    slug: String
) {
    val viewModelScope = rememberCoroutineScope()
    val vm: PageViewModel =
        remember(viewModelScope, slug) { webInjector.pageViewModel(viewModelScope) }

    LaunchedEffect(vm) {
        vm.trySend(PageContract.Inputs.Initialize(slug))
    }

    val uiState by vm.observeStates().collectAsState()

    PageScreenContent(uiState = uiState) {
        vm.trySend(it)
    }
}

@Composable
fun PageScreenContent(
    uiState: PageContract.State,
    postInput: (PageContract.Inputs) -> Unit
) {
    when (val page = uiState.page) {
        is ResponseResource.Error -> {

        }
        ResponseResource.Idle -> {

        }
        ResponseResource.Loading -> {
            Div {
                Spinner()
            }
        }
        is ResponseResource.Success -> {
            page.data.page?.let {
                Div {
                    println(it.content)
                    val editorJsResponse = remember(it) {
                        Json {
                            ignoreUnknownKeys = true
                        }.decodeFromString<ETResponse>(it.content as String)
                    }

                    if (editorJsResponse.blocks.isNotEmpty()) {
                        EditorJs(editorJsResponse.blocks)
                    }
                }
            }
        }
    }
}