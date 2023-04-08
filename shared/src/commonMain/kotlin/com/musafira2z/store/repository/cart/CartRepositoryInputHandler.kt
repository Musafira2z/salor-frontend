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
import com.musafira2z.store.type.*

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
                                val error = it.data?.checkoutLinesAdd?.errors
                                if (error?.isNotEmpty() == true) {
                                    postInput(CartRepositoryContract.Inputs.HandleCartError(error.map { it.code }))
                                }
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
                if (settingsRepository.checkoutToken != null) {
                    try {
                        val addPaymentToOrderMutation = CheckoutCompleteMutation(
                            checkoutToken = settingsRepository.checkoutToken!!,
                            paymentData = Optional.absent()
                        )
                        apolloClient.mutation(addPaymentToOrderMutation).execute().let {
                            if (it.data == null || it.hasErrors()) {
                                throw Exception(it.errors?.first()?.message)
                            } else {
                                if (it.data?.checkoutComplete?.errors?.isNotEmpty() == true || it.data?.checkoutComplete?.order == null) {
                                    throw Exception(it.data?.checkoutComplete?.errors?.first()?.message)
                                }
                                postInput(CartRepositoryContract.Inputs.UpdateLastOrder(it.data?.checkoutComplete))
                            }
                        }
                    } catch (connectionException: Exception) {
                        throw connectionException
                    }
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
                            channel = settingsRepository.channel ?: normalChannel
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
                if (settingsRepository.checkoutToken != null) {
                    try {
                        apolloClient.mutation(
                            RemoveProductFromCheckoutMutation(
                                checkoutToken = settingsRepository.checkoutToken!!,
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
        }
        is CartRepositoryContract.Inputs.UpdateQuantity -> {
            sideJob("UpdateQuantity") {
                if (settingsRepository.checkoutToken != null) {
                    val lineUpdate = CheckoutLineUpdateInput(
                        quantity = Optional.presentIfNotNull(input.qty),
                        variantId = input.itemId
                    )

                    try {
                        apolloClient.mutation(
                            CheckoutLineUpdateMutation(
                                token = Optional.present(settingsRepository.checkoutToken),
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
        }
        is CartRepositoryContract.Inputs.SetBillingAddress -> {
            sideJob("SetBillingAddress") {
                if (settingsRepository.checkoutToken != null) {
                    try {
                        val billingAddressMutation = CheckoutBillingAddressUpdateMutation(
                            address = input.address,
                            token = settingsRepository.checkoutToken!!,
                            locale = LanguageCodeEnum.EN
                        )
                        apolloClient.mutation(billingAddressMutation).execute().let {
                            if (it.data == null || it.hasErrors()) {
                                throw Exception(it.errors?.first()?.message)
                            } else {
                                postInput(CartRepositoryContract.Inputs.RefreshCarts(true))
                                postInput(CartRepositoryContract.Inputs.SetShippingAddress(input.address))
                            }
                        }
                    } catch (connectionException: Exception) {
                        throw connectionException
                    }
                }
            }
        }
        is CartRepositoryContract.Inputs.SetShippingAddress -> {
            sideJob("SetShippingAddress") {
                if (settingsRepository.checkoutToken != null) {
                    try {
                        val shippingAddressUpdateMutation = CheckoutShippingAddressUpdateMutation(
                            address = input.address,
                            token = settingsRepository.checkoutToken!!,
                            locale = LanguageCodeEnum.EN
                        )
                        apolloClient.mutation(shippingAddressUpdateMutation).execute().let {
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
        }
        is CartRepositoryContract.Inputs.SetShippingMethod -> {
            sideJob("SetShippingMethod") {
                if (settingsRepository.checkoutToken != null) {
                    try {
                        val shippingMethodMutation = CheckoutShippingMethodUpdateMutation(
                            token = settingsRepository.checkoutToken!!,
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
        }
        is CartRepositoryContract.Inputs.UpdateLastOrder -> {
            updateState { it.copy(lastOrder = input.lastOrder) }
            sideJob("ClearCheckoutToken") {
                input.lastOrder?.order?.id?.let {
                    settingsRepository.saveCheckoutToken(null)
                }
            }
        }
        is CartRepositoryContract.Inputs.Decrement -> {
            postInput(
                CartRepositoryContract.Inputs.UpdateQuantity(
                    itemId = input.variantId,
                    qty = input.qty
                )
            )
        }
        is CartRepositoryContract.Inputs.Increment -> {
            postInput(CartRepositoryContract.Inputs.AddItem(input.variantId))
        }
        is CartRepositoryContract.Inputs.RefreshShippingMethod -> {
            settingsRepository.checkoutToken?.let { _checkoutToken ->
                fetchWithCache(
                    input = input,
                    forceRefresh = input.forceRefresh,
                    getValue = { it.shippingMethods },
                    updateState = { CartRepositoryContract.Inputs.UpdateShippingMethod(it) },
                    doFetch = {
                        apolloClient.query(
                            AvailableShippingMethodsQuery(
                                channel = settingsRepository.channel ?: normalChannel,
                                locale = LanguageCodeEnum.EN
                            )
                        ).execute().let {
                            if (it.data == null || it.hasErrors()) {
                                throw Exception(it.errors?.first()?.message)
                            } else {
                                it.data?.shop?.availableShippingMethods!!
                            }
                        }
                    },
                )
            }
            Unit
        }
        is CartRepositoryContract.Inputs.UpdateShippingMethod -> {
            updateState { it.copy(shippingMethods = input.shippingMethods) }
        }
        is CartRepositoryContract.Inputs.CreatePayment -> {
            sideJob("CreatePayment") {
                if (settingsRepository.checkoutToken != null) {
                    try {
                        val addPaymentToOrderMutation = CheckoutPaymentCreateMutation(
                            checkoutToken = settingsRepository.checkoutToken!!,
                            paymentInput = PaymentInput(
                                gateway = input.paymentMethodId,
                                token = Optional.presentIfNotNull("ksdafjsdfkasdafj")
                            )
                        )

                        apolloClient.mutation(addPaymentToOrderMutation).execute().let {
                            if (it.data == null || it.hasErrors()) {
                                throw Exception(it.errors?.first()?.message)
                            } else {
                                if (it.data?.checkoutPaymentCreate?.errors?.isNotEmpty() == true || it.data?.checkoutPaymentCreate?.payment == null) {
                                    throw Exception(it.data?.checkoutPaymentCreate?.errors?.first()?.message)
                                }
                                postInput(CartRepositoryContract.Inputs.Checkout)
                            }
                        }
                    } catch (connectionException: Exception) {
                        throw connectionException
                    }
                }
            }
        }
        is CartRepositoryContract.Inputs.HandleCartError -> {
            input.errorCodes.first().let {
                when (it) {
                    CheckoutErrorCode.NOT_FOUND -> {
                        settingsRepository.saveCheckoutToken(null)
                    }
                    else -> {

                    }
                }
            }
        }
    }
}
