package com.musafira2z.store.repository.cart

import com.apollographql.apollo3.ApolloClient
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.observeInputsFromBus
import com.copperleaf.ballast.repository.cache.fetchWithCache
import com.musafira2z.store.CheckoutByTokenQuery
import com.musafira2z.store.type.LanguageCodeEnum

class CartRepositoryInputHandler(
    private val eventBus: EventBus,
    private val apolloClient: ApolloClient
) : InputHandler<
        CartRepositoryContract.Inputs,
        Any,
        CartRepositoryContract.State> {
    override suspend fun InputHandlerScope<
            CartRepositoryContract.Inputs,
            Any,
            CartRepositoryContract.State>.handleInput(
        input: CartRepositoryContract.Inputs
    ) = when (input) {
        is CartRepositoryContract.Inputs.ClearCaches -> {
            updateState { CartRepositoryContract.State() }
        }
        is CartRepositoryContract.Inputs.Initialize -> {
            val previousState = getCurrentState()

            if (!previousState.initialized) {
                updateState { it.copy(initialized = true) }
                // start observing flows here
                logger.debug("initializing")
                observeFlows(
                    key = "Observe account changes",
                    eventBus
                        .observeInputsFromBus<CartRepositoryContract.Inputs>(),
                )
            } else {
                logger.debug("already initialized")
                noOp()
            }
        }
        is CartRepositoryContract.Inputs.RefreshAllCaches -> {
            // then refresh all the caches in this repository
            val currentState = getCurrentState()
            if (currentState.dataListInitialized) {
                postInput(CartRepositoryContract.Inputs.RefreshDataList(true))
            }

            Unit
        }

        is CartRepositoryContract.Inputs.DataListUpdated -> {
            updateState { it.copy(dataList = input.dataList) }
        }
        is CartRepositoryContract.Inputs.RefreshDataList -> {
            updateState { it.copy(dataListInitialized = true) }
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.dataList },
                updateState = { CartRepositoryContract.Inputs.DataListUpdated(it) },
                doFetch = {
                    apolloClient.query(
                        CheckoutByTokenQuery(
                            checkoutToken = "",
                            locale = LanguageCodeEnum.EN
                        )
                    ).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            it.data?.checkout?.checkoutDetailsFragment!!
                        }
                    }
                },
            )
        }
    }
}
