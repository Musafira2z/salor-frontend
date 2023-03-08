package com.musafira2z.store.repository.menu

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.repository.BallastRepository
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.HomeBannerMenuQuery
import com.musafira2z.store.HomeMenuQuery
import com.musafira2z.store.MainMenuQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MenuRepositoryImpl(
    coroutineScope: CoroutineScope,
    eventBus: EventBus,
    configBuilder: BallastViewModelConfiguration.Builder
) : BallastRepository<
        MenuRepositoryContract.Inputs,
        MenuRepositoryContract.State>(
    coroutineScope = coroutineScope,
    eventBus = eventBus,
    config = configBuilder.build()
), MenuRepository {
    override fun clearAllCaches() {
        trySend(MenuRepositoryContract.Inputs.ClearCaches)
    }

    override fun getHomeBlock(refreshCache: Boolean): Flow<Cached<HomeMenuQuery.Data>> {
        trySend(MenuRepositoryContract.Inputs.Initialize)
        trySend(MenuRepositoryContract.Inputs.RefreshDataList(refreshCache))
        return observeStates()
            .map { it.dataList }
    }

    override fun getHomeBanner(refreshCache: Boolean): Flow<Cached<HomeBannerMenuQuery.Data>> {
        trySend(MenuRepositoryContract.Inputs.GetHomeBanner(refreshCache))
        return observeStates().map { it.homeBanner }
    }

    override fun getAllCategories(refreshCache: Boolean): Flow<Cached<MainMenuQuery.Data>> {
        trySend(MenuRepositoryContract.Inputs.GetCategories(refreshCache))
        return observeStates().map { it.categories }
    }
}
