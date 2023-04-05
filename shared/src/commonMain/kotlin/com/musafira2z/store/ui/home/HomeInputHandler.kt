package com.musafira2z.store.ui.home

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.cart.CartRepositoryContract
import com.musafira2z.store.repository.menu.MenuRepository
import com.musafira2z.store.repository.product.ProductRepository
import kotlinx.coroutines.flow.map

class HomeInputHandler(
    private val menuRepository: MenuRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : InputHandler<HomeContract.Inputs, HomeContract.Events, HomeContract.State> {
    override suspend fun InputHandlerScope<HomeContract.Inputs, HomeContract.Events, HomeContract.State>.handleInput(
        input: HomeContract.Inputs
    ) = when (input) {
        is HomeContract.Inputs.Initialize -> {
            postInput(HomeContract.Inputs.FetchHomeBlocks(true))
            postInput(HomeContract.Inputs.FetchHomeProducts(true))
            postInput(HomeContract.Inputs.FetchCarts(true))
            postInput(HomeContract.Inputs.FetchBanners(true))
            postInput(HomeContract.Inputs.FetchCategories)
        }

        is HomeContract.Inputs.GoBack -> {
            postEvent(HomeContract.Events.NavigateUp)
        }

        is HomeContract.Inputs.FetchHomeBlocks -> {
            observeFlows("FetchHomeBlocks") {
                listOf(menuRepository.getHomeBlock(input.forceRefresh).map { HomeContract.Inputs.UpdateHomeBlocks(it) })
            }
        }

        is HomeContract.Inputs.UpdateHomeBlocks -> {
            updateState { it.copy(blocks = input.blocks) }
        }

        is HomeContract.Inputs.FetchHomeProducts -> {
            val currentState = getCurrentState()
            val pageInfo = currentState.products.getCachedOrNull()?.products?.pageInfo
            observeFlows("FetchHomeProducts") {
                listOf(productRepository.getProducts(input.forceRefresh, pageInfo = pageInfo)
                    .map { HomeContract.Inputs.UpdateHomeProducts(it) })
            }
        }

        is HomeContract.Inputs.UpdateHomeProducts -> {
            updateState {
                it.copy(
                    products = input.products
                )
            }
            postInput(HomeContract.Inputs.AddHomeProducts(input.products))
        }

        is HomeContract.Inputs.FetchCarts -> {
            observeFlows("FetchCarts") {
                listOf(cartRepository.getCarts(input.forceRefresh).map { HomeContract.Inputs.UpdateCarts(it) })
            }
        }

        is HomeContract.Inputs.UpdateCarts -> {
            updateState { it.copy(carts = input.carts) }
        }

        is HomeContract.Inputs.FetchBanners -> {
            observeFlows("FetchBanners") {
                listOf(menuRepository.getHomeBanner(input.forceRefresh).map { HomeContract.Inputs.UpdateBanners(it) })
            }
        }

        is HomeContract.Inputs.UpdateBanners -> {
            updateState { it.copy(banners = input.banners) }
        }

        is HomeContract.Inputs.AddToCart -> {
            cartRepository.postInput(CartRepositoryContract.Inputs.AddItem(variantId = input.variantId))
        }

        HomeContract.Inputs.GoCheckoutPage -> {
            postEvent(HomeContract.Events.GoCheckoutPage)
        }

        HomeContract.Inputs.FetchCategories -> {
            observeFlows("FetchCategories") {
                listOf(menuRepository.getAllCategories(false).map { HomeContract.Inputs.UpdateCategories(it) })
            }
        }

        is HomeContract.Inputs.UpdateCategories -> {
            updateState { it.copy(categories = input.categories) }
        }

        is HomeContract.Inputs.GoCategoryPage -> {
            postEvent(HomeContract.Events.GoCategoryPage(input.slug))
        }

        is HomeContract.Inputs.GoProductDetailsPage -> {
            println("clicked on details")
            postEvent(HomeContract.Events.GoProductDetailsPage(input.slug, input.variantId))
        }

        is HomeContract.Inputs.AddHomeProducts -> {
            val currentState = getCurrentState()
            updateState {
                it.copy(allProducts = currentState.allProducts.toMutableList().apply {
                    input.products.getCachedOrNull()?.products?.edges?.let {
                        addAll(it)
                    }
                })
            }
        }
    }
}
