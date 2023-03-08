package com.musafira2z.store.repository.auth

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.LoginCustomerMutation
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
}
