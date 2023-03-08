package com.musafira2z.store.repository.product

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.repository.BallastRepository
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.CollectionBySlugQuery
import com.musafira2z.store.ProductCollectionQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    coroutineScope: CoroutineScope,
    eventBus: EventBus,
    configBuilder: BallastViewModelConfiguration.Builder
) : BallastRepository<
        ProductRepositoryContract.Inputs,
        ProductRepositoryContract.State>(
    coroutineScope = coroutineScope,
    eventBus = eventBus,
    config = configBuilder
        .build()
), ProductRepository {
    override fun clearAllCaches() {
        trySend(ProductRepositoryContract.Inputs.ClearCaches)
    }

    override fun getDataList(refreshCache: Boolean): Flow<Cached<ProductCollectionQuery.Data>> {
        trySend(ProductRepositoryContract.Inputs.Initialize)
        trySend(ProductRepositoryContract.Inputs.RefreshDataList(refreshCache))
        return observeStates()
            .map { it.products }
    }

    override fun getProductsByCategory(
        refreshCache: Boolean,
        slug: String
    ): Flow<Cached<CollectionBySlugQuery.Data>> {
        trySend(ProductRepositoryContract.Inputs.GetProductByCategory(forceRefresh = refreshCache, slug = slug))
        return observeStates().map { it.productsByCategory }
    }
}
