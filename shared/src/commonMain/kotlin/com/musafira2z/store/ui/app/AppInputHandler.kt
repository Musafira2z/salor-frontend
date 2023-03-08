package com.musafira2z.store.ui.app

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.menu.MenuRepository
import kotlinx.coroutines.flow.map

class AppInputHandler(
    private val cartRepository: CartRepository,
    private val menuRepository: MenuRepository
) : InputHandler<
        AppContract.Inputs,
        AppContract.Events,
        AppContract.State> {
    override suspend fun InputHandlerScope<
            AppContract.Inputs,
            AppContract.Events,
            AppContract.State>.handleInput(
        input: AppContract.Inputs
    ) = when (input) {
        is AppContract.Inputs.Initialize -> {
            postInput(AppContract.Inputs.FetchCarts(true))
            postInput(AppContract.Inputs.FetchCategories(true))
        }
        is AppContract.Inputs.GoBack -> {
            postEvent(AppContract.Events.NavigateUp)
        }
        is AppContract.Inputs.FetchCarts -> {
            observeFlows("FetchCarts") {
                listOf(
                    cartRepository.getCarts(input.forceRefresh)
                        .map { AppContract.Inputs.UpdateCarts(it) }
                )
            }
        }
        is AppContract.Inputs.UpdateCarts -> {
            updateState { it.copy(carts = input.carts) }
        }
        is AppContract.Inputs.FetchCategories -> {
            observeFlows("FetchCategories") {
                listOf(
                    menuRepository.getAllCategories(input.forceRefresh)
                        .map { AppContract.Inputs.UpdateCategories(it) }
                )
            }
        }
        is AppContract.Inputs.UpdateCategories -> {
            updateState { it.copy(categories = input.categories) }
        }
    }
}
