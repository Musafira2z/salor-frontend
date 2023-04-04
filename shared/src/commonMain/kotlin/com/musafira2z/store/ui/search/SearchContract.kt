package com.musafira2z.store.ui.search

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.MainMenuQuery
import com.musafira2z.store.ProductCollectionQuery
import com.musafira2z.store.fragment.CheckoutDetailsFragment

object SearchContract {
    data class State(
        val loading: Boolean = false,
        val filter: String? = null,
        val products: Cached<ProductCollectionQuery.Data> = Cached.NotLoaded(),
        val categories: Cached<MainMenuQuery.Data> = Cached.NotLoaded(),
        val carts: Cached<CheckoutDetailsFragment> = Cached.NotLoaded(),
    )

    sealed class Inputs {
        data class Initialize(val filter: String?) : Inputs()
        object GoBack : Inputs()
        object FetchCategories : Inputs()
        data class UpdateCategories(val categories: Cached<MainMenuQuery.Data>) : Inputs()
        data class FetchCarts(val forceRefresh: Boolean) : Inputs()
        data class UpdateCarts(val carts: Cached<CheckoutDetailsFragment>) : Inputs()
        data class AddToCart(val variantId: String) : Inputs()
        object GoCheckoutPage : Inputs()
        data class GoCategoryPage(val slug: String) : Inputs()
        data class GoProductDetailsPage(val slug: String, val variantId: String?) : Inputs()
        data class FetchProducts(val forceRefresh: Boolean,val filter: String) : Inputs()
        data class UpdateProducts(val products: Cached<ProductCollectionQuery.Data>) : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
        object GoCheckoutPage : Events()
        data class GoCategoryPage(val slug: String) : Events()
        data class GoProductDetailsPage(val slug: String, val variantId: String?) : Events()
    }
}
