package com.musafira2z.store.repository.product

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.repository.BallastRepository
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.cache.Cached
import com.copperleaf.ballast.repository.withRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(
    coroutineScope: CoroutineScope,
    eventBus: EventBus,
    configBuilder: BallastViewModelConfiguration.Builder,
    inputHandler: ProductRepositoryInputHandler
) : BallastRepository<
        ProductRepositoryContract.Inputs,
        ProductRepositoryContract.State>(coroutineScope = coroutineScope,
    eventBus = eventBus,
    config = configBuilder
        .apply {
            this.inputHandler = inputHandler
            initialState = ProductRepositoryContract.State()
            name = "Product Repository"
        }.withRepository().build()
), ProductRepository {
    override fun clearAllCaches() {
        trySend(ProductRepositoryContract.Inputs.ClearCaches)
    }

    override fun getDataList(refreshCache: Boolean): Flow<Cached<List<String>>> {
        trySend(ProductRepositoryContract.Inputs.Initialize)
        trySend(ProductRepositoryContract.Inputs.RefreshDataList(refreshCache))
        return observeStates()
            .map { it.dataList }
    }
}
