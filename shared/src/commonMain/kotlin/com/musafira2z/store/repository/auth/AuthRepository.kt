package com.musafira2z.store.repository.auth

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.*
import com.musafira2z.store.utils.ResponseResource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun clearAllCaches()
    fun getDataList(refreshCache: Boolean = false): Flow<Cached<List<String>>>

    fun isLoggedIn(): Flow<Boolean>
    fun loginUser(
        username: String,
        password: String
    ): Flow<ResponseResource<LoginCustomerMutation.TokenCreate?>>

    fun signupUser(
        fullName: String,
        username: String,
        password: String
    ): Flow<ResponseResource<RegisterCustomerMutation.AccountRegister?>>

    fun fetchMe(forceRefresh: Boolean): Flow<Cached<CurrentUserDetailsQuery.Me>>
    fun fetchAddress(forceRefresh: Boolean): Flow<Cached<CurrentUserAddressesQuery.Me>>
    fun fetchOrders(forceRefresh: Boolean): Flow<Cached<OrdersQuery.Me>>

    fun postInput(input: AuthRepositoryContract.Inputs)
}
