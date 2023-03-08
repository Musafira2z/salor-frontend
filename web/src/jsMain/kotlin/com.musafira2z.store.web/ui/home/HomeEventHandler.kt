package com.musafira2z.store.web.ui.home

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.vm.Router
import com.musafira2z.store.ui.home.HomeContract
import com.musafira2z.store.web.ui.router.WebPage

class HomeEventHandler(
    private val router: Router<WebPage>
) : EventHandler<
        HomeContract.Inputs,
        HomeContract.Events,
        HomeContract.State> {
    override suspend fun EventHandlerScope<
            HomeContract.Inputs,
            HomeContract.Events,
            HomeContract.State>.handleEvent(
        event: HomeContract.Events
    ) = when (event) {
        is HomeContract.Events.NavigateUp -> {

        }
    }
}