package com.musafira2z.store.ui.profile

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.auth.AuthRepository
import kotlinx.coroutines.flow.map

class ProfileInputHandler(
    private val authRepository: AuthRepository
) : InputHandler<
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
            postInput(ProfileContract.Inputs.FetchMe(false))
            postInput(ProfileContract.Inputs.FetchAddress(true))
        }
        is ProfileContract.Inputs.GoBack -> {
            postEvent(ProfileContract.Events.NavigateUp)
        }
        is ProfileContract.Inputs.FetchMe -> {
            observeFlows("FetchMe") {
                listOf(
                    authRepository.fetchMe(input.forceRefresh)
                        .map { ProfileContract.Inputs.UpdateMe(it) }
                )
            }
        }
        is ProfileContract.Inputs.FetchOrders -> {

        }
        is ProfileContract.Inputs.UpdateMe -> {
            updateState { it.copy(me = input.me) }
        }
        is ProfileContract.Inputs.UpdateOrders -> {

        }
        is ProfileContract.Inputs.FetchAddress -> {
            observeFlows("FetchAddress") {
                listOf(
                    authRepository.fetchAddress(input.forceRefresh)
                        .map { ProfileContract.Inputs.UpdateAddress(it) }
                )
            }
        }
        is ProfileContract.Inputs.UpdateAddress -> {
            updateState { it.copy(address = input.me) }
        }
    }
}
