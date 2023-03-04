package com.musafira2z.store.repository.product

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.ProductCollectionQuery
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun clearAllCaches()
    fun getDataList(refreshCache: Boolean = false): Flow<Cached<ProductCollectionQuery.Data>>

}
