package com.musafira2z.store.web.ui.dashboard.orders

import androidx.compose.runtime.*
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.fragment.OrderDetailsFragment
import com.musafira2z.store.ui.orders.OrdersContract
import com.musafira2z.store.web.ui.di.ComposeWebInjector
import com.musafira2z.store.web.ui.utils.toClasses
import com.musafira2z.store.web.ui.utils.toFormatPrice
import org.jetbrains.compose.web.dom.*

@Composable
fun OrdersPage(
    webInjector: ComposeWebInjector
) {
    val viewModelScope = rememberCoroutineScope()
    val vm: OrdersViewModel =
        remember(viewModelScope) { webInjector.orderViewModel(viewModelScope) }
    LaunchedEffect(vm) {
        vm.trySend(OrdersContract.Inputs.Initialize)
    }

    val uiState by vm.observeStates().collectAsState()
    OrdersPageContent(uiState = uiState) {
        vm.trySend(it)
    }
}

@Composable
fun OrdersPageContent(uiState: OrdersContract.State, postInput: (OrdersContract.Inputs) -> Unit) {
    Div(attrs = {
        classes("container", "mx-auto")
    }) {
        Div(attrs = {
            classes(
                "w-full",
                "xl:ml-64",
                "lg:ml-56",
                "md:ml-50",
                "sm:mx-5",
                "mx-5",
                "mt-5"
            )
        }) {
            uiState.orders.getCachedOrNull()?.orders?.edges?.let {
                it.forEach { orderNode ->
                    OrderItem(order = orderNode.node.orderDetailsFragment) { orderId ->
                        postInput(OrdersContract.Inputs.GoOrderDetails(orderId))
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItem(
    order: OrderDetailsFragment,
    onOrderDetail: (String) -> Unit
) {
    Div(attrs = {
        toClasses("block mt-2 max-w-sm p-6 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-100 dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-700")
        onClick {
            onOrderDetail(order.id)
        }
    }) {
        Div(attrs = {
            toClasses("flex")
        }) {
            H5(attrs = {
                toClasses("flex-none mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white")
            }) {
                Text("Order #${order.number}")
            }
            Div(attrs = {
                classes("grow")
            }) {

            }
            Button(attrs = {
                toClasses("flex-none bg-green-50")
                onClick {
                    onOrderDetail(order.id)
                }
            }) {
                Text("Details")
            }
        }
        Hr()
        P(attrs = {
            toClasses("font-normal text-gray-700 dark:text-gray-400")
        }) {
            Text("Date: ${order.created}")
        }
        H5(attrs = {
            toClasses("flex-none mb-2 mt-2 text-lg font-bold tracking-tight text-gray-900 dark:text-white")
        }) {
            Text("Total: ${order.total.gross.priceFragment.toFormatPrice()}")
        }
    }
}