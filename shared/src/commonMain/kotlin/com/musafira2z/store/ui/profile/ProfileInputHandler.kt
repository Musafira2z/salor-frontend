package com.musafira2z.store.ui.profile

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import kotlinx.coroutines.delay

class ProfileInputHandler : InputHandler<
        ProfileContract.Inputs,
        ProfileContract.Events,
        ProfileContract.State> {
    override suspend fun InputHandlerScope<
            ProfileContract.Inputs,
            ProfileContract.Events,
            ProfileContract.State>.handleInput(
        input: ProfileContract.Inputs
    ) = when (input) {
        is ProfileContract.Inputs.Initialize -> {
            updateState { it.copy(loading = true) }
            delay(1000)
            updateState { it.copy(loading = false) }
        }
        is ProfileContract.Inputs.GoBack -> {
            postEvent(ProfileContract.Events.NavigateUp)
        }
        is ProfileContract.Inputs.FetchMe -> {

        }
        is ProfileContract.Inputs.FetchOrders -> {

        }
        is ProfileContract.Inputs.UpdateMe -> {

        }
        is ProfileContract.Inputs.UpdateOrders -> {

        }
    }
}
