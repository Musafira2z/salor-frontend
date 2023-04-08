package com.musafira2z.store.web.ui.product_details

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import com.musafira2z.store.ui.product_details.ProductDetailContract
import com.musafira2z.store.web.ui.router.WebPage
import com.musafira2z.store.web.ui.router.WebPagerRouter

class ProductDetailEventHandler(
    private val router: WebPagerRouter
) : EventHandler<
        ProductDetailContract.Inputs,
        ProductDetailContract.Events,
        ProductDetailContract.State> {
    override suspend fun EventHandlerScope<
            ProductDetailContract.Inputs,
            ProductDetailContract.Events,
            ProductDetailContract.State>.handleEvent(
        event: ProductDetailContract.Events
    ) = when (event) {
        is ProductDetailContract.Events.NavigateUp -> {

        }
        ProductDetailContract.Events.GoCheckoutPage -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.Checkout.directions().build()
                )
            )
            Unit
        }
    }
}
