package com.musafira2z.store.web.ui.checkout

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.vm.Router
import com.musafira2z.store.ui.checkout.CheckoutContract
import com.musafira2z.store.web.ui.router.WebPage

class CheckoutEventHandler(
    private val router: Router<WebPage>
) : EventHandler<
        CheckoutContract.Inputs,
        CheckoutContract.Events,
        CheckoutContract.State> {
    override suspend fun EventHandlerScope<
            CheckoutContract.Inputs,
            CheckoutContract.Events,
            CheckoutContract.State>.handleEvent(
        event: CheckoutContract.Events
    ) = when (event) {
        is CheckoutContract.Events.NavigateUp -> {

        }
    }
}