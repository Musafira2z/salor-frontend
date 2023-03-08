package com.musafira2z.store.ui.app

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.MainMenuQuery
import com.musafira2z.store.fragment.CheckoutDetailsFragment

object AppContract {
    data class State(
        val loading: Boolean = false,
        val carts: Cached<CheckoutDetailsFragment> = Cached.NotLoaded(),
        val categories: Cached<MainMenuQuery.Data> = Cached.NotLoaded()
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object GoBack : Inputs()
        data class FetchCarts(val forceRefresh: Boolean) : Inputs()
        data class UpdateCarts(val carts: Cached<CheckoutDetailsFragment>) : Inputs()

        data class FetchCategories(val forceRefresh: Boolean) : Inputs()
        data class UpdateCategories(val categories: Cached<MainMenuQuery.Data>) : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
