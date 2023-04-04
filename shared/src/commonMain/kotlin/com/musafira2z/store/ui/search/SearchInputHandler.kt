package com.musafira2z.store.ui.search

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.cart.CartRepositoryContract
import com.musafira2z.store.repository.menu.MenuRepository
import com.musafira2z.store.repository.product.ProductRepository
import kotlinx.coroutines.flow.map

class SearchInputHandler(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val menuRepository: MenuRepository
) : InputHandler<SearchContract.Inputs, SearchContract.Events, SearchContract.State> {
    override suspend fun InputHandlerScope<SearchContract.Inputs, SearchContract.Events, SearchContract.State>.handleInput(
        input: SearchContract.Inputs
    ) = when (input) {
        is SearchContract.Inputs.Initialize -> {
            updateState { it.copy(filter = input.filter) }
            postInput(SearchContract.Inputs.FetchProducts(true, input.filter ?: ""))
            postInput(SearchContract.Inputs.FetchCarts(true))
            postInput(SearchContract.Inputs.FetchCategories)
        }

        is SearchContract.Inputs.GoBack -> {
            postEvent(SearchContract.Events.NavigateUp)
        }

        is SearchContract.Inputs.AddToCart -> {
            cartRepository.postInput(CartRepositoryContract.Inputs.AddItem(variantId = input.variantId))
        }

        is SearchContract.Inputs.FetchCarts -> {
            observeFlows("FetchCarts") {
                listOf(cartRepository.getCarts(input.forceRefresh).map { SearchContract.Inputs.UpdateCarts(it) })
            }
        }

        SearchContract.Inputs.FetchCategories -> {
            observeFlows("FetchCategories") {
                listOf(menuRepository.getAllCategories(false).map { SearchContract.Inputs.UpdateCategories(it) })
            }
        }

        is SearchContract.Inputs.GoCategoryPage -> {
            postEvent(SearchContract.Events.GoCategoryPage(input.slug))
        }

        SearchContract.Inputs.GoCheckoutPage -> {
            postEvent(SearchContract.Events.GoCheckoutPage)
        }

        is SearchContract.Inputs.GoProductDetailsPage -> {
            postEvent(SearchContract.Events.GoProductDetailsPage(input.slug, input.variantId))
        }

        is SearchContract.Inputs.UpdateCarts -> {
            updateState { it.copy(carts = input.carts) }
        }

        is SearchContract.Inputs.UpdateCategories -> {
            updateState { it.copy(categories = input.categories) }
        }

        is SearchContract.Inputs.FetchProducts -> {
            updateState { it.copy(filter = input.filter) }
            observeFlows("FetchProducts") {
                listOf(productRepository.searchProducts(input.forceRefresh, input.filter)
                    .map { SearchContract.Inputs.UpdateProducts(it) })
            }
        }

        is SearchContract.Inputs.UpdateProducts -> {
            updateState { it.copy(products = input.products) }
        }
    }
}
