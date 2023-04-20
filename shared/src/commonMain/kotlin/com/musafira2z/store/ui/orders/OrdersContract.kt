package com.musafira2z.store.ui.orders

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.OrdersQuery

object OrdersContract {
    data class State(
        val loading: Boolean = false,
        val orders: Cached<OrdersQuery.Me> = Cached.NotLoaded()
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object GoBack : Inputs()
        data class FetchOrders(val forceRefresh: Boolean) : Inputs()
        data class UpdateOrders(val orders: Cached<OrdersQuery.Me>) : Inputs()
        data class GoOrderDetails(val orderId: String) : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
        data class NavigateOrderDetails(val orderId: String) : Events()
    }
}
