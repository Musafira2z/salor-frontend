package com.musafira2z.store.web.ui.success

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.vm.Router
import com.musafira2z.store.ui.success.OrderSuccessContract
import com.musafira2z.store.web.ui.router.WebPage

class OrderSuccessEventHandler(
    private val router: Router<WebPage>
) : EventHandler<
        OrderSuccessContract.Inputs,
        OrderSuccessContract.Events,
        OrderSuccessContract.State> {
    override suspend fun EventHandlerScope<
            OrderSuccessContract.Inputs,
            OrderSuccessContract.Events,
            OrderSuccessContract.State>.handleEvent(
        event: OrderSuccessContract.Events
    ) = when (event) {
        is OrderSuccessContract.Events.NavigateUp -> {
            
        }
    }
}
