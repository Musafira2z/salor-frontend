package com.musafira2z.store.web.ui.page

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.vm.Router
import com.musafira2z.store.ui.page.PageContract
import com.musafira2z.store.web.ui.router.WebPage

class PageEventHandler(
    private val router: Router<WebPage>
) : EventHandler<
        PageContract.Inputs,
        PageContract.Events,
        PageContract.State> {
    override suspend fun EventHandlerScope<
            PageContract.Inputs,
            PageContract.Events,
            PageContract.State>.handleEvent(
        event: PageContract.Events
    ) = when (event) {
        is PageContract.Events.NavigateUp -> {

        }
    }
}
