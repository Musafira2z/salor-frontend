package com.musafira2z.store.ui.checkout

import com.apollographql.apollo3.api.Optional
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.auth.AuthRepository
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.cart.CartRepositoryContract
import com.musafira2z.store.type.CountryCode
import kotlinx.coroutines.flow.map

class CheckoutInputHandler(
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : InputHandler<
        CheckoutContract.Inputs,
        CheckoutContract.Events,
        CheckoutContract.State> {
    override suspend fun InputHandlerScope<
            CheckoutContract.Inputs,
            CheckoutContract.Events,
            CheckoutContract.State>.handleInput(
        input: CheckoutContract.Inputs
    ) = when (input) {
        is CheckoutContract.Inputs.Initialize -> {
            postInput(CheckoutContract.Inputs.FetchCarts(true))
            postInput(CheckoutContract.Inputs.FetchAddress(true))
        }
        is CheckoutContract.Inputs.GoBack -> {
            postEvent(CheckoutContract.Events.NavigateUp)
        }
        is CheckoutContract.Inputs.FetchCarts -> {
            observeFlows("FetchCarts") {
                listOf(
                    cartRepository.getCarts(input.forceRefresh)
                        .map { CheckoutContract.Inputs.UpdateCarts(it) }
                )
            }
        }
        is CheckoutContract.Inputs.UpdateCarts -> {
            updateState { it.copy(carts = input.carts) }
        }
        is CheckoutContract.Inputs.FetchMe -> {
            observeFlows("FetchMe") {
                listOf(
                    authRepository.fetchMe(input.forceRefresh)
                        .map { CheckoutContract.Inputs.UpdateMe(it) }
                )
            }
        }
        is CheckoutContract.Inputs.UpdateMe -> {
            updateState { it.copy(me = input.me) }
        }
        is CheckoutContract.Inputs.FetchAddress -> {
            observeFlows("FetchAddress") {
                listOf(
                    authRepository.fetchAddress(input.forceRefresh)
                        .map { CheckoutContract.Inputs.UpdateAddress(it) }
                )
            }
        }
        is CheckoutContract.Inputs.UpdateAddress -> {
            updateState { it.copy(address = input.address) }
        }
        is CheckoutContract.Inputs.FetchOrders -> {
            observeFlows("FetchOrders") {
                listOf(
                    authRepository.fetchOrders(input.forceRefresh)
                        .map { CheckoutContract.Inputs.UpdateOrders(it) }
                )
            }
        }
        is CheckoutContract.Inputs.UpdateOrders -> {
            updateState { it.copy(orders = input.orders) }
        }
        is CheckoutContract.Inputs.SetBillingAddress -> {
            cartRepository.postInput(
                CartRepositoryContract.Inputs.SetBillingAddress(
                    address = input.address.copy(country = Optional.present(CountryCode.ZA))
                )
            )
        }
        is CheckoutContract.Inputs.UpdateBillingResponse -> {

        }
        is CheckoutContract.Inputs.SetShippingMethod -> {
            cartRepository.postInput(CartRepositoryContract.Inputs.SetShippingMethod(methodId = input.methodId))
        }
        is CheckoutContract.Inputs.PlaceOrder -> {
            cartRepository.postInput(CartRepositoryContract.Inputs.CreatePayment(input.paymentMethodId))
        }
    }
}
