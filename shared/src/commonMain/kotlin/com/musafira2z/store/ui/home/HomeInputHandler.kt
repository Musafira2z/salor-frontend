package com.musafira2z.store.ui.home

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.menu.MenuRepository
import com.musafira2z.store.repository.product.ProductRepository
import kotlinx.coroutines.flow.map

class HomeInputHandler(
    private val menuRepository: MenuRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : InputHandler<
        HomeContract.Inputs,
        HomeContract.Events,
        HomeContract.State> {
    override suspend fun InputHandlerScope<
            HomeContract.Inputs,
            HomeContract.Events,
            HomeContract.State>.handleInput(
        input: HomeContract.Inputs
    ) = when (input) {
        is HomeContract.Inputs.Initialize -> {
            postInput(HomeContract.Inputs.FetchHomeBlocks(true))
            postInput(HomeContract.Inputs.FetchHomeProducts(true))
            postInput(HomeContract.Inputs.FetchCarts(true))
        }
        is HomeContract.Inputs.GoBack -> {
            postEvent(HomeContract.Events.NavigateUp)
        }
        is HomeContract.Inputs.FetchHomeBlocks -> {
            observeFlows("FetchHomeBlocks") {
                listOf(
                    menuRepository.getHomeBlock(input.forceRefresh)
                        .map { HomeContract.Inputs.UpdateHomeBlocks(it) }
                )
            }
        }
        is HomeContract.Inputs.UpdateHomeBlocks -> {
            updateState { it.copy(blocks = input.blocks) }
        }
        is HomeContract.Inputs.FetchHomeProducts -> {
            observeFlows("FetchHomeProducts") {
                listOf(
                    productRepository.getDataList(input.forceRefresh)
                        .map { HomeContract.Inputs.UpdateHomeProducts(it) }
                )
            }
        }
        is HomeContract.Inputs.UpdateHomeProducts -> {
            updateState { it.copy(products = input.products) }
        }
        is HomeContract.Inputs.FetchCarts -> {
            observeFlows("FetchCarts") {
                listOf(
                    cartRepository.getCarts(input.forceRefresh)
                        .map { HomeContract.Inputs.UpdateCarts(it) }
                )
            }
        }
        is HomeContract.Inputs.UpdateCarts -> {
            updateState { it.copy(carts = input.carts) }
        }
    }
}
