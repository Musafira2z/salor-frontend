package com.musafira2z.store.repository.product

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.ProductBySlugQuery
import com.musafira2z.store.ProductCollectionQuery
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun clearAllCaches()
    fun getDataList(refreshCache: Boolean = false): Flow<Cached<ProductCollectionQuery.Data>>
    fun searchProducts(refreshCache: Boolean = false, filter: String): Flow<Cached<ProductCollectionQuery.Data>>
    fun getProductsByCategory(
        refreshCache: Boolean = false,
        slug: String
    ): Flow<Cached<ProductCollectionQuery.Data>>

    fun getProductBySlug(
        refreshCache: Boolean = false,
        slug: String
    ): Flow<Cached<ProductBySlugQuery.Data>>
}
