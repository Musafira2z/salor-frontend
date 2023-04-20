package com.musafira2z.store.web.ui.app

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import com.copperleaf.ballast.navigation.routing.queryParameter
import com.copperleaf.ballast.navigation.vm.Router
import com.musafira2z.store.ui.app.AppContract
import com.musafira2z.store.web.ui.router.WebPage

class AppEventHandler(
    private val router: Router<WebPage>
) : EventHandler<
        AppContract.Inputs,
        AppContract.Events,
        AppContract.State> {
    override suspend fun EventHandlerScope<
            AppContract.Inputs,
            AppContract.Events,
            AppContract.State>.handleEvent(
        event: AppContract.Events
    ) = when (event) {
        is AppContract.Events.NavigateUp -> {

        }

        is AppContract.Events.GoSearchPage -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Search.directions()
                        .queryParameter("filter", event.filter ?: "")
                        .build()
                )
            )
            Unit
        }
        AppContract.Events.NavigateProfile -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Profile.directions()
                        .build()
                )
            )
            Unit
        }
        AppContract.Events.NavigateOrder -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Orders.directions()
                        .build()
                )
            )
            Unit
        }
    }
}
