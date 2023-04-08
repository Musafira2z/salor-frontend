package com.musafira2z.store.ui.product_details

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.ProductBySlugQuery
import com.musafira2z.store.ProductCollectionQuery
import com.musafira2z.store.fragment.CheckoutDetailsFragment

object ProductDetailContract {
    data class State(
        val loading: Boolean = false,
        val slug: String = "",
        val variantId: String? = null,
        val product: Cached<ProductBySlugQuery.Data> = Cached.NotLoaded(),
        val relatedProducts: Cached<ProductCollectionQuery.Data> = Cached.NotLoaded(),
        val carts: Cached<CheckoutDetailsFragment> = Cached.NotLoaded()
    )

    sealed class Inputs {
        data class Initialize(val slug: String, val variantId: String?) : Inputs()
        object GoBack : Inputs()
        data class GetProduct(val forceRefresh: Boolean, val slug: String) : Inputs()
        data class UpdateProduct(val product: Cached<ProductBySlugQuery.Data>) : Inputs()
        data class GetRelatedProducts(val forceRefresh: Boolean, val categoryId: String) : Inputs()
        data class AddToCart(val variantId: String) : Inputs()
        data class UpdateRelatedProducts(
            val relatedProducts: Cached<ProductCollectionQuery.Data>
        ) : Inputs()

        data class FetchCarts(val forceRefresh: Boolean) : Inputs()
        data class UpdateCarts(val carts: Cached<CheckoutDetailsFragment>) : Inputs()
        object GoCheckoutPage : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
        object GoCheckoutPage : Events()
    }
}
