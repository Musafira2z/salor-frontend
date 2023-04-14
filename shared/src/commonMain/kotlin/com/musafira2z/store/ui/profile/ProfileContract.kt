package com.musafira2z.store.ui.profile

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.CurrentUserDetailsQuery
import com.musafira2z.store.OrdersQuery

object ProfileContract {
    data class State(
        val loading: Boolean = false,
        val me: Cached<CurrentUserDetailsQuery.Me> = Cached.NotLoaded(),
        val orders: Cached<OrdersQuery.Data> = Cached.NotLoaded()
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object GoBack : Inputs()
        data class FetchMe(val forceRefresh: Boolean) : Inputs()
        data class UpdateMe(val me: Cached<CurrentUserDetailsQuery.Me>) : Inputs()
        data class FetchOrders(val forceRefresh: Boolean) : Inputs()
        data class UpdateOrders(val orders: Cached<OrdersQuery.Data>) : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
