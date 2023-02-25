package com.musafira2z.store.repository.menu

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.HomeMenuQuery
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun clearAllCaches()
    fun getHomeBlock(refreshCache: Boolean = false): Flow<Cached<HomeMenuQuery.Data>>
}
