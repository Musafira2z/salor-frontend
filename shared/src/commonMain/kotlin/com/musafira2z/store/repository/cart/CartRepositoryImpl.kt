package com.musafira2z.store.repository.cart

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.repository.BallastRepository
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.AvailableShippingMethodsQuery
import com.musafira2z.store.CheckoutCompleteMutation
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(
    coroutineScope: CoroutineScope,
    eventBus: EventBus,
    configBuilder: BallastViewModelConfiguration.Builder,
) : BallastRepository<
        CartRepositoryContract.Inputs,
        CartRepositoryContract.State>(
    coroutineScope = coroutineScope,
    eventBus = eventBus,
    config = configBuilder.build()
), CartRepository {
    override fun clearAllCaches() {
        trySend(CartRepositoryContract.Inputs.ClearCaches)
    }

    override fun getCarts(refreshCache: Boolean): Flow<Cached<CheckoutDetailsFragment>> {
        trySend(CartRepositoryContract.Inputs.Initialize)
        trySend(CartRepositoryContract.Inputs.RefreshCarts(refreshCache))
        return observeStates()
            .map { it.dataList }
    }

    override fun getShippingMethods(refreshCache: Boolean): Flow<Cached<List<AvailableShippingMethodsQuery.AvailableShippingMethod>>> {
        trySend(CartRepositoryContract.Inputs.RefreshShippingMethod(refreshCache))
        return observeStates().map { it.shippingMethods }
    }

    override fun getLastOrder(): Flow<CheckoutCompleteMutation.CheckoutComplete?> {
        return observeStates().map { it.lastOrder }
    }

    override fun postInput(input: CartRepositoryContract.Inputs) {
        trySend(input)
    }
}
