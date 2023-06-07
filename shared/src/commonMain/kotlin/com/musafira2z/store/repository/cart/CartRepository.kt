package com.musafira2z.store.repository.cart

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.AvailableShippingMethodsQuery
import com.musafira2z.store.CheckoutCompleteMutation
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.utils.ResponseResource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    fun clearAllCaches()
    fun getCarts(refreshCache: Boolean = false): Flow<Cached<CheckoutDetailsFragment>>
    fun getShippingMethods(refreshCache: Boolean = false): Flow<Cached<List<AvailableShippingMethodsQuery.AvailableShippingMethod>>>
    fun getLastOrder(): Flow<ResponseResource<CheckoutCompleteMutation.CheckoutComplete>>

    fun postInput(input: CartRepositoryContract.Inputs)
}
