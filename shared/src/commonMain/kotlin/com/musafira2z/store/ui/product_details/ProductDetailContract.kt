package com.musafira2z.store.ui.product_details

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.ProductBySlugQuery

object ProductDetailContract {
    data class State(
        val loading: Boolean = false,
        val slug: String = "",
        val variantId: String? = null,
        val product: Cached<ProductBySlugQuery.Data> = Cached.NotLoaded()
    )

    sealed class Inputs {
        data class Initialize(val slug: String, val variantId: String?) : Inputs()
        object GoBack : Inputs()
        data class GetProduct(val forceRefresh: Boolean, val slug: String) : Inputs()
        data class UpdateProduct(val product: Cached<ProductBySlugQuery.Data>) : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
