package com.musafira2z.store.repository.product

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.HomeMenuQuery

object ProductRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val dataList: Cached<List<String>> = Cached.NotLoaded(),
    )

    sealed class Inputs {
        object ClearCaches : Inputs()
        object Initialize : Inputs()
        object RefreshAllCaches : Inputs()

        data class RefreshDataList(val forceRefresh: Boolean) : Inputs()
        data class DataListUpdated(val dataList: Cached<List<String>>) : Inputs()
    }
}
