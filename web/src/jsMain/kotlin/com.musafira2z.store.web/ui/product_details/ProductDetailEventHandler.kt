package com.musafira2z.store.web.ui.product_details

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.musafira2z.store.ui.product_details.ProductDetailContract
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
    }
}
