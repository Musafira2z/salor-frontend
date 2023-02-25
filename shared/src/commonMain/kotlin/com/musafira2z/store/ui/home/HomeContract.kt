package com.musafira2z.store.ui.home

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.HomeMenuQuery

object HomeContract {
    data class State(
        val loading: Boolean = false,
        val blocks: Cached<HomeMenuQuery.Data> = Cached.NotLoaded()
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object GoBack : Inputs()
        data class FetchHomeBlocks(val forceRefresh: Boolean): Inputs()
        data class UpdateHomeBlocks(val blocks: Cached<HomeMenuQuery.Data>): Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
