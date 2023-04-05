package com.musafira2z.store.repository.product

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.ProductBySlugQuery
import com.musafira2z.store.ProductCollectionQuery

object ProductRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val products: Cached<ProductCollectionQuery.Data> = Cached.NotLoaded(),
        val filteredProducts: Cached<ProductCollectionQuery.Data> = Cached.NotLoaded(),
        val productsByCategory: Cached<ProductCollectionQuery.Data> = Cached.NotLoaded(),
        val product: Cached<ProductBySlugQuery.Data> = Cached.NotLoaded()
    )

    sealed class Inputs {
        object ClearCaches : Inputs()
        object Initialize : Inputs()
        object RefreshAllCaches : Inputs()

        data class RefreshDataList(val forceRefresh: Boolean, val pageInfo: ProductCollectionQuery.PageInfo?) : Inputs()
        data class DataListUpdated(val dataList: Cached<ProductCollectionQuery.Data>) : Inputs()

        data class SearchProducts(val forceRefresh: Boolean, val filter: String) : Inputs()
        data class UpdateSearchedProducts(val products: Cached<ProductCollectionQuery.Data>) : Inputs()

        data class GetProductByCategory(val slug: String, val forceRefresh: Boolean) : Inputs()
        data class UpdateProductByCategory(
            val productsByCategory: Cached<ProductCollectionQuery.Data>
        ) : Inputs()

        data class GetProduct(val forceRefresh: Boolean, val slug: String) : Inputs()
        data class UpdateProduct(
            val productsByCategory: Cached<ProductBySlugQuery.Data>
        ) : Inputs()
    }
}
