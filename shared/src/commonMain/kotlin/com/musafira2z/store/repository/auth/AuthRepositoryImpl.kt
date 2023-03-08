package com.musafira2z.store.repository.auth

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.repository.BallastRepository
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.LoginCustomerMutation
import com.musafira2z.store.utils.ResponseResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    coroutineScope: CoroutineScope,
    eventBus: EventBus,
    configBuilder: BallastViewModelConfiguration.Builder,
) : BallastRepository<
        AuthRepositoryContract.Inputs,
        AuthRepositoryContract.State>(
    coroutineScope = coroutineScope,
    eventBus = eventBus,
    config = configBuilder.build()
), AuthRepository {
    override fun clearAllCaches() {
        trySend(AuthRepositoryContract.Inputs.ClearCaches)
    }

    override fun getDataList(refreshCache: Boolean): Flow<Cached<List<String>>> {
        trySend(AuthRepositoryContract.Inputs.Initialize)
        trySend(AuthRepositoryContract.Inputs.RefreshDataList(refreshCache))
        return observeStates()
            .map { it.dataList }
    }

    override fun isLoggedIn(): Flow<Boolean> {
        trySend(AuthRepositoryContract.Inputs.FetchLoginStatus)
        return observeStates().map { it.isLoggedIn }
    }

    override fun loginUser(
        username: String,
        password: String
    ): Flow<ResponseResource<LoginCustomerMutation.TokenCreate?>> {
        trySend(AuthRepositoryContract.Inputs.LoginUser(username = username, password = password))
        return observeStates().map { it.loginResponse }
    }
}
