package com.musafira2z.store.web.ui.success

import androidx.compose.runtime.*
import com.musafira2z.store.ui.success.OrderSuccessContract
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import org.jetbrains.compose.web.ExperimentalComposeWebSvgApi
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.svg.Path
import org.jetbrains.compose.web.svg.Svg

@Composable
fun OrderSuccessPage(
    webInjector: ComposeWebInjector,
    slug: String
) {
    val viewModelScope = rememberCoroutineScope()
    val vm: OrderSuccessViewModel =
        remember(viewModelScope, slug) { webInjector.orderSuccessViewModel(viewModelScope) }
    LaunchedEffect(vm) {
        vm.trySend(OrderSuccessContract.Inputs.Initialize(slug))
    }

    val uiState by vm.observeStates().collectAsState()

    OrderSuccessContent(uiState = uiState) {
        vm.trySend(it)
    }
}


@OptIn(ExperimentalComposeWebSvgApi::class)
@Composable
fun OrderSuccessContent(
    uiState: OrderSuccessContract.State,
    postInput: (OrderSuccessContract.Inputs) -> Unit
) {
    Div(attrs = {
        classes("bg-gray-100", "h-screen")
    }) {
        Div(attrs = {
            classes("bg-white", "p-6", "md:mx-auto")
        }) {
            Svg(attrs = {
                attr("viewBox", "0 0 24 24")
                attr("width", "64")
                attr("height", "64")
                classes("text-green-600", "mx-auto", "my-6")
            }) {
                Path(
                    d = "M12,0A12,12,0,1,0,24,12,12.014,12.014,0,0,0,12,0Zm6.927,8.2-6.845,9.289a1.011,1.011,0,0,1-1.43.188L5.764,13.769a1,1,0,1,1,1.25-1.562l4.076,3.261,6.227-8.451A1,1,0,1,1,18.927,8.2Z"
                )
            }
            Div(attrs = {
                classes("text-center")
            }) {
                H3(attrs = {
                    classes(
                        "md:text-2xl",
                        "text-base",
                        "text-gray-900",
                        "font-semibold",
                        "text-center"
                    )
                }) {
                    Text("Order #${uiState.slug} Placed!")
                }
                P(attrs = {
                    classes("text-gray-600", "my-2")
                }) {
                    Text("Thank you for completing your order.")
                }
                P {
                    Text(" Have a great day! ")
                }
                Div(attrs = {
                    classes("py-10", "text-center")
                }) {
                    A(attrs = {
                        classes(
                            "px-12",
                            "text-white",
                            "font-semibold",
                            "bg-green-500",
                            "hover:opacity-75",
                            "text-slate-50",
                            "rounded-lg",
                            "py-3"
                        )
                    }, href = "/") {
                        Text(" GO HOME ")
                    }
                }
            }
        }
    }
}


