package com.musafira2z.store.ui.orders

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.auth.AuthRepository
import kotlinx.coroutines.flow.map

class OrdersInputHandler(
    private val authRepository: AuthRepository
) : InputHandler<
        OrdersContract.Inputs,
        OrdersContract.Events,
        OrdersContract.State> {
    override suspend fun InputHandlerScope<
            OrdersContract.Inputs,
            OrdersContract.Events,
            OrdersContract.State>.handleInput(
        input: OrdersContract.Inputs
    ) = when (input) {
        is OrdersContract.Inputs.Initialize -> {
            postInput(OrdersContract.Inputs.FetchOrders(true))
        }
        is OrdersContract.Inputs.GoBack -> {
            postEvent(OrdersContract.Events.NavigateUp)
        }
        is OrdersContract.Inputs.FetchOrders -> {
            observeFlows("FetchOrders") {
                listOf(
                    authRepository.fetchOrders(input.forceRefresh)
                        .map { OrdersContract.Inputs.UpdateOrders(it) }
                )
            }
        }
        is OrdersContract.Inputs.UpdateOrders -> {
            updateState { it.copy(orders = input.orders) }
        }
        is OrdersContract.Inputs.GoOrderDetails -> {
            postEvent(OrdersContract.Events.NavigateOrderDetails(input.orderId))
        }
    }
}
