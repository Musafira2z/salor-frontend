package com.musafira2z.store.ui.product_details

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.product.ProductRepository
import kotlinx.coroutines.flow.map

class ProductDetailInputHandler(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
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
        }
    }
}
