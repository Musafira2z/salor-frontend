package com.musafira2z.store.repository.menu

import com.apollographql.apollo3.ApolloClient
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.observeInputsFromBus
import com.copperleaf.ballast.repository.cache.fetchWithCache
import com.musafira2z.store.HomeMenuQuery
import com.musafira2z.store.defaultChannel
import com.musafira2z.store.type.LanguageCodeEnum

class MenuRepositoryInputHandler(
    private val eventBus: EventBus,
    private val apolloClient: ApolloClient
) : InputHandler<
        MenuRepositoryContract.Inputs,
        Any,
        MenuRepositoryContract.State> {
    override suspend fun InputHandlerScope<
            MenuRepositoryContract.Inputs,
            Any,
            MenuRepositoryContract.State>.handleInput(
        input: MenuRepositoryContract.Inputs
    ) = when (input) {
        is MenuRepositoryContract.Inputs.ClearCaches -> {
            updateState { MenuRepositoryContract.State() }
        }
        is MenuRepositoryContract.Inputs.Initialize -> {
            val previousState = getCurrentState()

            if (!previousState.initialized) {
                updateState { it.copy(initialized = true) }
                // start observing flows here
                logger.debug("initializing")
                observeFlows(
                    key = "Observe account changes",
                    eventBus
                        .observeInputsFromBus<MenuRepositoryContract.Inputs>(),
                )
            } else {
                logger.debug("already initialized")
                noOp()
            }
        }
        is MenuRepositoryContract.Inputs.RefreshAllCaches -> {
            // then refresh all the caches in this repository
            val currentState = getCurrentState()
            if (currentState.dataListInitialized) {
                postInput(MenuRepositoryContract.Inputs.RefreshDataList(true))
            }

            Unit
        }

        is MenuRepositoryContract.Inputs.DataListUpdated -> {
            updateState { it.copy(dataList = input.dataList) }
        }
        is MenuRepositoryContract.Inputs.RefreshDataList -> {
            updateState { it.copy(dataListInitialized = true) }
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.dataList },
                updateState = { MenuRepositoryContract.Inputs.DataListUpdated(it) },
                doFetch = {
                    apolloClient.query(
                        HomeMenuQuery(
                            locale = LanguageCodeEnum.EN_US,
                            channel = defaultChannel
                        )
                    ).execute().data!!
                },
            )
        }
    }
}
