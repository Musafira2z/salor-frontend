package com.musafira2z.store.repository.cart

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.observeInputsFromBus
import com.copperleaf.ballast.repository.cache.fetchWithCache
import com.musafira2z.store.*
import com.musafira2z.store.repository.settings.SettingsRepository
import com.musafira2z.store.type.CheckoutLineInput
import com.musafira2z.store.type.CheckoutLineUpdateInput
import com.musafira2z.store.type.LanguageCodeEnum

class CartRepositoryInputHandler(
    private val eventBus: EventBus,
    private val apolloClient: ApolloClient,
    private val settingsRepository: SettingsRepository
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
                postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
            }

            Unit
        }

        is CartRepositoryContract.Inputs.DataListUpdated -> {
            updateState { it.copy(dataList = input.dataList) }
        }
        is CartRepositoryContract.Inputs.RefreshCarts -> {
            updateState { it.copy(dataListInitialized = true) }
            settingsRepository.checkoutToken?.let { _checkoutToken ->
                fetchWithCache(
                    input = input,
                    forceRefresh = input.forceRefresh,
                    getValue = { it.dataList },
                    updateState = { CartRepositoryContract.Inputs.DataListUpdated(it) },
                    doFetch = {
                        apolloClient.query(
                            CheckoutByTokenQuery(
                                checkoutToken = _checkoutToken,
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
            Unit
        }
        is CartRepositoryContract.Inputs.AddItem -> {
            if (settingsRepository.checkoutToken != null) {
                sideJob("AddItem") {
                    try {
                        apolloClient.mutation(
                            CheckoutAddProductLineMutation(
                                checkoutToken = settingsRepository.checkoutToken!!,
                                variantId = input.variantId,
                                locale = LanguageCodeEnum.EN
                            )
                        ).execute().let {
                            if (it.data == null || it.hasErrors()) {
                                throw Exception(it.errors?.first()?.message)
                            } else {
                                //fetch checkout by token
                                postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                            }
                        }
                    } catch (e: Exception) {
                        throw e
                    }
                }
            } else {
                postInput(CartRepositoryContract.Inputs.CreateCheckout(variantId = input.variantId))
            }
        }
        is CartRepositoryContract.Inputs.AddUserToCart -> {
            sideJob("AddUserToCart") {
                try {
                    apolloClient.mutation(
                        CheckoutEmailUpdateMutation(
                            token = input.token,
                            email = input.email,
                            locale = LanguageCodeEnum.EN
                        )
                    ).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                        }
                    }
                } catch (connectionException: Exception) {
                    throw connectionException
                }
            }
        }
        is CartRepositoryContract.Inputs.ApplyPromoCode -> {
            sideJob("ApplyPromoCode") {
                try {
                    val shippingAddressMutation = CheckoutAddPromoCodeMutation(
                        token = input.checkoutId,
                        promoCode = input.code,
                        locale = LanguageCodeEnum.EN
                    )
                    apolloClient.mutation(shippingAddressMutation).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            if (it.data?.checkoutAddPromoCode?.checkout == null) {
                                throw Exception(it.data?.checkoutAddPromoCode?.errors?.first()?.message)
                            }
                            postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                        }
                    }
                } catch (connectionException: Exception) {
                    throw connectionException
                }
            }
        }
        is CartRepositoryContract.Inputs.Checkout -> {
            sideJob("CheckoutComplete") {
                try {
                    val addPaymentToOrderMutation = CheckoutCompleteMutation(
                        checkoutToken = input.token, paymentData = Optional.absent()
                    )
                    apolloClient.mutation(addPaymentToOrderMutation).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            if (it.data?.checkoutComplete?.errors?.isNotEmpty() == true || it.data?.checkoutComplete?.order == null) {
                                throw Exception(it.data?.checkoutComplete?.errors?.first()?.message)
                            }

                            postInput(CartRepositoryContract.Inputs.UpdateLastOrder(it.data?.checkoutComplete))

                            return@let it.data?.checkoutComplete
                        }
                    }
                } catch (connectionException: Exception) {
                    throw connectionException
                }
            }
        }
        is CartRepositoryContract.Inputs.Clear -> {
            sideJob("ClearCart") {
                /*try {
                    val removeAllOrderLineMutation = RemoveAllOrderLineMutation()
                    apolloClient.mutation(removeAllOrderLineMutation).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            _activeOrderSharedFlow.emit(CartUiModel.initCart())
                        }
                    }
                } catch (connectionException: java.net.UnknownHostException) {
                    throw connectionException
                }*/
            }
        }
        is CartRepositoryContract.Inputs.CreateCheckout -> {
            sideJob("CreateCheckout") {
                try {
                    val checkoutLineInput = CheckoutLineInput(
                        quantity = 1, variantId = input.variantId
                    )
                    apolloClient.mutation(
                        CreateCheckoutMutation(
                            email = Optional.present(input.email),
                            lines = listOf(
                                checkoutLineInput
                            ),
                            channel = defaultChannel
                        )
                    ).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            //fetch checkout by token
                            val token = it.data?.checkoutCreate?.checkout?.token as String?
                            settingsRepository.saveCheckoutToken(token)
                            postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                        }
                    }
                } catch (e: Exception) {
                    throw e
                }
            }
        }
        is CartRepositoryContract.Inputs.RemoveCartItem -> {
            sideJob("RemoveCartItem") {
                try {
                    apolloClient.mutation(
                        RemoveProductFromCheckoutMutation(
                            checkoutToken = input.token,
                            lineId = input.itemId,
                            locale = LanguageCodeEnum.EN
                        )
                    ).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                        }
                    }
                } catch (connectionException: Exception) {
                    throw connectionException
                }
            }
        }
        is CartRepositoryContract.Inputs.UpdateQuantity -> {
            sideJob("UpdateQuantity") {
                val lineUpdate = CheckoutLineUpdateInput(
                    quantity = Optional.presentIfNotNull(input.qty),
                    variantId = input.itemId
                )

                try {
                    apolloClient.mutation(
                        CheckoutLineUpdateMutation(
                            token = Optional.present(input.token),
                            lines = listOf(
                                lineUpdate
                            ),
                            locale = LanguageCodeEnum.EN
                        )
                    ).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                        }
                    }
                } catch (connectionException: Exception) {
                    throw connectionException
                }
            }
        }
        is CartRepositoryContract.Inputs.SetBillingAddress -> {
            sideJob("SetBillingAddress") {
                try {
                    val billingAddressMutation = CheckoutBillingAddressUpdateMutation(
                        address = input.address,
                        token = input.checkoutId,
                        locale = LanguageCodeEnum.EN
                    )
                    apolloClient.mutation(billingAddressMutation).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                        }
                    }
                } catch (connectionException: Exception) {
                    throw connectionException
                }
            }
        }
        is CartRepositoryContract.Inputs.SetShippingMethod -> {
            sideJob("SetShippingMethod") {
                try {
                    val shippingMethodMutation = CheckoutShippingMethodUpdateMutation(
                        token = input.token,
                        shippingMethodId = input.methodId,
                        locale = LanguageCodeEnum.EN
                    )
                    apolloClient.mutation(shippingMethodMutation).execute().let {
                        if (it.data == null || it.hasErrors()) {
                            throw Exception(it.errors?.first()?.message)
                        } else {
                            if (it.data?.checkoutShippingMethodUpdate?.errors?.isNotEmpty() == true || it.data?.checkoutShippingMethodUpdate?.checkout == null) {
                                throw Exception(it.data?.checkoutShippingMethodUpdate?.errors?.first()?.message)
                            }
                            postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                        }
                    }
                } catch (connectionException: Exception) {
                    throw connectionException
                }
            }
        }
        is CartRepositoryContract.Inputs.UpdateLastOrder -> {
            updateState { it.copy(lastOrder = input.lastOrder) }
        }
        is CartRepositoryContract.Inputs.Decrement -> {

        }
        is CartRepositoryContract.Inputs.Increment -> {

        }
    }
}
