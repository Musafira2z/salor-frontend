package com.musafira2z.store.repository.auth

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.observeInputsFromBus
import com.copperleaf.ballast.repository.cache.fetchWithCache

class AuthRepositoryInputHandler(
    private val eventBus: EventBus,
) : InputHandler<
        AuthRepositoryContract.Inputs,
        Any,
        AuthRepositoryContract.State> {
    override suspend fun InputHandlerScope<
            AuthRepositoryContract.Inputs,
            Any,
            AuthRepositoryContract.State>.handleInput(
        input: AuthRepositoryContract.Inputs
    ) = when (input) {
        is AuthRepositoryContract.Inputs.ClearCaches -> {
            updateState { AuthRepositoryContract.State() }
        }
        is AuthRepositoryContract.Inputs.Initialize -> {
            val previousState = getCurrentState()

            if (!previousState.initialized) {
                updateState { it.copy(initialized = true) }
                // start observing flows here
                logger.debug("initializing")
                observeFlows(
                    key = "Observe account changes",
                    eventBus
                        .observeInputsFromBus<AuthRepositoryContract.Inputs>(),
                )
            } else {
                logger.debug("already initialized")
                noOp()
            }
        }
        is AuthRepositoryContract.Inputs.RefreshAllCaches -> {
            // then refresh all the caches in this repository
            val currentState = getCurrentState()
            if (currentState.dataListInitialized) {
                postInput(AuthRepositoryContract.Inputs.RefreshDataList(true))
            }

            Unit
        }

        is AuthRepositoryContract.Inputs.DataListUpdated -> {
            updateState { it.copy(dataList = input.dataList) }
        }
        is AuthRepositoryContract.Inputs.RefreshDataList -> {
            updateState { it.copy(dataListInitialized = true) }
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.dataList },
                updateState = { AuthRepositoryContract.Inputs.DataListUpdated(it) },
                doFetch = { TODO() },
            )
        }
    }
}
