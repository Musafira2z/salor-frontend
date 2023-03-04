package com.musafira2z.store.repository.cart

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.fragment.CheckoutDetailsFragment

object CartRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val dataList: Cached<CheckoutDetailsFragment> = Cached.NotLoaded(),
    )

    sealed class Inputs {
        object ClearCaches : Inputs()
        object Initialize : Inputs()
        object RefreshAllCaches : Inputs()

        data class RefreshDataList(val forceRefresh: Boolean) : Inputs()
        data class DataListUpdated(val dataList: Cached<CheckoutDetailsFragment>) : Inputs()
    }
}
