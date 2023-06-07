package com.musafira2z.store.ui.product_details

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.repository.auth.AuthRepository
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.cart.CartRepositoryContract
import com.musafira2z.store.repository.product.ProductRepository
import com.musafira2z.store.ui.home.HomeContract
import kotlinx.coroutines.flow.map

class ProductDetailInputHandler(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    private val authRepository: AuthRepository
) : InputHandler<
        ProductDetailContract.Inputs,
        ProductDetailContract.Events,
        ProductDetailContract.State> {
    override suspend fun InputHandlerScope<
            ProductDetailContract.Inputs,
            ProductDetailContract.Events,
            ProductDetailContract.State>.handleInput(
        input: ProductDetailContract.Inputs
    ) = when (input) {
        is ProductDetailContract.Inputs.Initialize -> {
            updateState { it.copy(slug = input.slug, variantId = input.variantId) }
            postInput(ProductDetailContract.Inputs.GetProduct(forceRefresh = true, input.slug))
            postInput(ProductDetailContract.Inputs.FetchCarts(true))
            postInput(ProductDetailContract.Inputs.FetchLoginStatus)
        }
        is ProductDetailContract.Inputs.GoBack -> {
            postEvent(ProductDetailContract.Events.NavigateUp)
        }
        is ProductDetailContract.Inputs.GetProduct -> {
            observeFlows("GetProduct") {
                listOf(
                    productRepository.getProductBySlug(input.forceRefresh, input.slug)
                        .map { ProductDetailContract.Inputs.UpdateProduct(it) }
                )
            }
        }
        is ProductDetailContract.Inputs.UpdateProduct -> {
            updateState { it.copy(product = input.product) }
            input.product.getCachedOrNull()?.product?.productDetailsFragment?.let {

            }
            Unit
        }
        is ProductDetailContract.Inputs.GetRelatedProducts -> {

        }
        is ProductDetailContract.Inputs.UpdateRelatedProducts -> {

        }
        is ProductDetailContract.Inputs.AddToCart -> {
            cartRepository.postInput(CartRepositoryContract.Inputs.AddItem(variantId = input.variantId))
        }
        is ProductDetailContract.Inputs.FetchCarts -> {
            observeFlows("FetchCarts") {
                listOf(
                    cartRepository.getCarts(input.forceRefresh)
                        .map { ProductDetailContract.Inputs.UpdateCarts(it) })
            }
        }
        ProductDetailContract.Inputs.GoCheckoutPage -> {
            postEvent(ProductDetailContract.Events.GoCheckoutPage)
        }
        is ProductDetailContract.Inputs.UpdateCarts -> {
            updateState { it.copy(carts = input.carts) }
        }
        ProductDetailContract.Inputs.FetchLoginStatus -> {
            observeFlows("FetchLoginStatus") {
                listOf(
                    authRepository.isLoggedIn()
                        .map { ProductDetailContract.Inputs.UpdateLoginStatus(it) }
                )
            }
        }
        is ProductDetailContract.Inputs.UpdateLoginStatus -> {
            updateState { it.copy(isLoggedIn = input.isLoggedIn) }
        }
    }
}
