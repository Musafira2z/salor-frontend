package com.musafira2z.store.web.ui.dashboard.orders

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import com.copperleaf.ballast.navigation.routing.path
import com.copperleaf.ballast.navigation.vm.Router
import com.musafira2z.store.ui.orders.OrdersContract
import com.musafira2z.store.web.ui.router.WebPage

class OrdersEventHandler(
    private val router: Router<WebPage>
) : EventHandler<
        OrdersContract.Inputs,
        OrdersContract.Events,
        OrdersContract.State> {
    override suspend fun EventHandlerScope<
            OrdersContract.Inputs,
            OrdersContract.Events,
            OrdersContract.State>.handleEvent(
        event: OrdersContract.Events
    ) = when (event) {
        is OrdersContract.Events.NavigateUp -> {

        }
        is OrdersContract.Events.NavigateOrderDetails -> {
            router.trySend(
                RouterContract.Inputs.GoToDestination(
                    WebPage.OrderDetails.directions()
                        .path(event.orderId)
                        .build()
                )
            )
            Unit
        }
    }
}
