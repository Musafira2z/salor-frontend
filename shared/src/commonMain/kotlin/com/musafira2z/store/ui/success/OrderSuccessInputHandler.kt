package com.musafira2z.store.ui.success

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope

class OrderSuccessInputHandler : InputHandler<
        OrderSuccessContract.Inputs,
        OrderSuccessContract.Events,
        OrderSuccessContract.State> {
    override suspend fun InputHandlerScope<
            OrderSuccessContract.Inputs,
            OrderSuccessContract.Events,
            OrderSuccessContract.State>.handleInput(
        input: OrderSuccessContract.Inputs
    ) = when (input) {
        is OrderSuccessContract.Inputs.Initialize -> {
            updateState { it.copy(slug = input.slug) }
        }
        is OrderSuccessContract.Inputs.GoBack -> {
            postEvent(OrderSuccessContract.Events.NavigateUp)
        }
    }
}
