package com.musafira2z.store.web.ui.dashboard.proifle

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.musafira2z.store.ui.profile.ProfileContract

class ProfileEventHandler : EventHandler<
        ProfileContract.Inputs,
        ProfileContract.Events,
        ProfileContract.State> {
    override suspend fun EventHandlerScope<
            ProfileContract.Inputs,
            ProfileContract.Events,
            ProfileContract.State>.handleEvent(
        event: ProfileContract.Events
    ) = when (event) {
        is ProfileContract.Events.NavigateUp -> {

        }
    }
}