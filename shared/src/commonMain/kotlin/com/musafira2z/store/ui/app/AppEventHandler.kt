package com.musafira2z.store.ui.app

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

class AppEventHandler : EventHandler<
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
    }
}
