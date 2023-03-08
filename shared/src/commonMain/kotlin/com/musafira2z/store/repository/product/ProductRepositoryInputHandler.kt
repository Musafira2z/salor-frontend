package com.musafira2z.store.repository.product

import com.apollographql.apollo3.ApolloClient
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.observeInputsFromBus
import com.copperleaf.ballast.repository.cache.fetchWithCache
import com.musafira2z.store.CollectionBySlugQuery
import com.musafira2z.store.ProductBySlugQuery
import com.musafira2z.store.ProductCollectionQuery
import com.musafira2z.store.defaultChannel
import com.musafira2z.store.type.LanguageCodeEnum
import com.musafira2z.store.type.ProductFilterInput

class ProductRepositoryInputHandler(
    private val eventBus: EventBus,
    private val apolloClient: ApolloClient
) : InputHandler<
        ProductRepositoryContract.Inputs,
        Any,
        ProductRepositoryContract.State> {
    override suspend fun InputHandlerScope<
            ProductRepositoryContract.Inputs,
            Any,
            ProductRepositoryContract.State>.handleInput(
        input: ProductRepositoryContract.Inputs
    ) = when (input) {
        is ProductRepositoryContract.Inputs.ClearCaches -> {
            updateState { ProductRepositoryContract.State() }
        }
        is ProductRepositoryContract.Inputs.Initialize -> {
            val previousState = getCurrentState()

            if (!previousState.initialized) {
                updateState { it.copy(initialized = true) }
                // start observing flows here
                logger.debug("initializing")
                observeFlows(
                    key = "Observe account changes",
                    eventBus
                        .observeInputsFromBus<ProductRepositoryContract.Inputs>(),
                )
            } else {
                logger.debug("already initialized")
                noOp()
            }
        }
        is ProductRepositoryContract.Inputs.RefreshAllCaches -> {
            // then refresh all the caches in this repository
            val currentState = getCurrentState()
            if (currentState.dataListInitialized) {
                postInput(ProductRepositoryContract.Inputs.RefreshDataList(true))
            }

            Unit
        }

        is ProductRepositoryContract.Inputs.DataListUpdated -> {
            updateState { it.copy(products = input.dataList) }
        }
        is ProductRepositoryContract.Inputs.RefreshDataList -> {
            updateState { it.copy(dataListInitialized = true) }
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.products },
                updateState = { ProductRepositoryContract.Inputs.DataListUpdated(it) },
                doFetch = {
                    apolloClient.query(
                        ProductCollectionQuery(
                            after = "",
                            first = 20,
                            channel = defaultChannel,
                            filter = ProductFilterInput(),
                            locale = LanguageCodeEnum.EN
                        )
                    ).execute().data!!
                },
            )
        }
        is ProductRepositoryContract.Inputs.GetProductByCategory -> {
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.productsByCategory },
                updateState = { ProductRepositoryContract.Inputs.UpdateProductByCategory(it) },
                doFetch = {
                    apolloClient.query(
                        CollectionBySlugQuery(
                            channel = defaultChannel,
                            locale = LanguageCodeEnum.EN,
                            slug = input.slug
                        )
                    ).execute().data!!
                },
            )
        }
        is ProductRepositoryContract.Inputs.UpdateProductByCategory -> {
            updateState { it.copy(productsByCategory = input.productsByCategory) }
        }
    }
}
