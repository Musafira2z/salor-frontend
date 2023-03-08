package com.musafira2z.store.repository.product

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.ProductCollectionQuery

object ProductRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val products: Cached<ProductCollectionQuery.Data> = Cached.NotLoaded(),
        val productsByCategory: Cached<ProductCollectionQuery.Data> = Cached.NotLoaded(),
    )

    sealed class Inputs {
        object ClearCaches : Inputs()
        object Initialize : Inputs()
        object RefreshAllCaches : Inputs()

        data class RefreshDataList(val forceRefresh: Boolean) : Inputs()
        data class DataListUpdated(val dataList: Cached<ProductCollectionQuery.Data>) : Inputs()

        data class GetProductByCategory(val slug: String, val forceRefresh: Boolean) : Inputs()
        data class UpdateProductByCategory(val productsByCategory: Cached<ProductCollectionQuery.Data>):Inputs()
    }
}
