package com.musafira2z.store.web.ui.home

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.*
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
        HomeContract.Events.GoCheckoutPage -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Checkout.directions().build()
                )
            )
            Unit
        }
        is HomeContract.Events.GoCategoryPage -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Category.directions().path(event.slug).build()
                )
            )
            Unit
        }
        is HomeContract.Events.GoProductDetailsPage -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.ProductDetails.directions()
                        .path(event.slug)
                        .queryParameter("variantId", event.variantId ?: "")
                        .build()
                )
            )
            Unit
        }
    }
}