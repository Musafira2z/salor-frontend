package com.musafira2z.store.web.ui.search

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.*
import com.musafira2z.store.ui.search.SearchContract
import com.musafira2z.store.web.ui.router.WebPage
import com.musafira2z.store.web.ui.router.WebPagerRouter

class SearchEventHandler(private val router: WebPagerRouter) : EventHandler<
        SearchContract.Inputs,
        SearchContract.Events,
        SearchContract.State> {
    override suspend fun EventHandlerScope<
            SearchContract.Inputs,
            SearchContract.Events,
            SearchContract.State>.handleEvent(
        event: SearchContract.Events
    ) = when (event) {
        is SearchContract.Events.NavigateUp -> {

        }

        is SearchContract.Events.GoCategoryPage -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Category.directions().path(event.slug).build()
                )
            )
            Unit
        }
        SearchContract.Events.GoCheckoutPage -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Checkout.directions().build()
                )
            )
            Unit
        }
        is SearchContract.Events.GoProductDetailsPage -> {
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
