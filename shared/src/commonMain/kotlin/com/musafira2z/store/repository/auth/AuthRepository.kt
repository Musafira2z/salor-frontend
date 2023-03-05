package com.musafira2z.store.repository.auth

import com.copperleaf.ballast.repository.cache.Cached
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun clearAllCaches()
    fun getDataList(refreshCache: Boolean = false): Flow<Cached<List<String>>>

}
