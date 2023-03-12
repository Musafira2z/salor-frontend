package com.musafira2z.store.ui.checkout

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.musafira2z.store.repository.auth.AuthRepository
import com.musafira2z.store.repository.cart.CartRepository
import kotlinx.coroutines.delay

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

        }
        is CheckoutContract.Inputs.GoBack -> {
            postEvent(CheckoutContract.Events.NavigateUp)
        }
    }
}
