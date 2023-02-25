package com.musafira2z.store.repository.menu

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.HomeMenuQuery

object MenuRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val dataList: Cached<HomeMenuQuery.Data> = Cached.NotLoaded(),
    )

    sealed class Inputs {
        object ClearCaches : Inputs()
        object Initialize : Inputs()
        object RefreshAllCaches : Inputs()

        data class RefreshDataList(val forceRefresh: Boolean) : Inputs()
        data class DataListUpdated(val dataList: Cached<HomeMenuQuery.Data>) : Inputs()
    }
}
