package com.musafira2z.store.repository.product

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.repository.BallastRepository
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.ProductBySlugQuery
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

    override fun getProducts(refreshCache: Boolean, pageInfo: ProductCollectionQuery.PageInfo?): Flow<Cached<ProductCollectionQuery.Data>> {
        trySend(ProductRepositoryContract.Inputs.Initialize)
        trySend(ProductRepositoryContract.Inputs.RefreshDataList(refreshCache, pageInfo = pageInfo))
        return observeStates()
            .map { it.products }
    }

    override fun searchProducts(refreshCache: Boolean, filter: String): Flow<Cached<ProductCollectionQuery.Data>> {
        trySend(ProductRepositoryContract.Inputs.SearchProducts(refreshCache, filter))
        return observeStates().map { it.filteredProducts }
    }

    override fun getProductsByCategory(
        refreshCache: Boolean,
        slug: String
    ): Flow<Cached<ProductCollectionQuery.Data>> {
        trySend(
            ProductRepositoryContract.Inputs.GetProductByCategory(
                forceRefresh = refreshCache,
                slug = slug
            )
        )
        return observeStates().map { it.productsByCategory }
    }

    override fun getProductBySlug(
        refreshCache: Boolean,
        slug: String
    ): Flow<Cached<ProductBySlugQuery.Data>> {
        trySend(
            ProductRepositoryContract.Inputs.GetProduct(
                forceRefresh = refreshCache,
                slug = slug
            )
        )
        return observeStates().map { it.product }
    }
}
