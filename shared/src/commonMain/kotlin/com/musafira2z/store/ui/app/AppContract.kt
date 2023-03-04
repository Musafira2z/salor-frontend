package com.musafira2z.store.ui.app

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.fragment.CheckoutDetailsFragment

object AppContract {
    data class State(
        val loading: Boolean = false,
        val carts: Cached<CheckoutDetailsFragment> = Cached.NotLoaded()
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object GoBack : Inputs()
        data class FetchCarts(val forceRefresh: Boolean) : Inputs()
        data class UpdateCarts(val carts: Cached<CheckoutDetailsFragment>) : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
