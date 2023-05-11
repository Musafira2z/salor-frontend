package com.musafira2z.store.ui.category

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.data.categoryItems
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.cart.CartRepositoryContract
import com.musafira2z.store.repository.menu.MenuRepository
import com.musafira2z.store.repository.product.ProductRepository
import com.musafira2z.store.ui.home.HomeContract
import kotlinx.coroutines.flow.map

class CategoryInputHandler(
    private val menuRepository: MenuRepository,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : InputHandler<
        CategoryContract.Inputs,
        CategoryContract.Events,
        CategoryContract.State> {
    override suspend fun InputHandlerScope<
            CategoryContract.Inputs,
            CategoryContract.Events,
            CategoryContract.State>.handleInput(
        input: CategoryContract.Inputs
    ) = when (input) {
        is CategoryContract.Inputs.Initialize -> {
            updateState { it.copy(slug = input.slug) }
            postInput(CategoryContract.Inputs.FetchCategories)
            postInput(CategoryContract.Inputs.FetchCarts(true))
        }

        is CategoryContract.Inputs.GoBack -> {
            postEvent(CategoryContract.Events.NavigateUp)
        }

        CategoryContract.Inputs.FetchCategories -> {
            observeFlows("FetchCategories") {
                listOf(
                    menuRepository.getAllCategories(true)
                        .map { CategoryContract.Inputs.UpdateCategories(it) }
                )
            }
        }

        is CategoryContract.Inputs.UpdateCategories -> {
            updateState { it.copy(categories = input.categories) }
            val currentState = getCurrentState()
//            val categories = currentState.categories.getCachedOrNull()?.menu?.items?.categoryItems()
//                ?.map { item ->
//                    item.child.map { _child ->
//                        item.child.map { _child1 ->
//                            _child1
//                        }.toMutableList().apply {
//                            add(_child)
//                        }
//                    }.flatten().toMutableList().apply {
//                        add(item)
//                    }
//                }?.flatten()

            val category =
                currentState.categories.getCachedOrNull()?.menu?.items?.categoryItems()?.let {
                    val item = it.firstOrNull {
                        it.slug == currentState.slug
                    }
                    if (item != null) return@let item

                    val ii = it.mapNotNull { it.child.firstOrNull { it.slug == currentState.slug } }
                    if (ii.firstOrNull() != null) {
                        return@let ii.first()
                    }
                    null
                }

            postInput(CategoryContract.Inputs.UpdateCategory(category))
        }

        is CategoryContract.Inputs.UpdateCategory -> {
            updateState { it.copy(category = input.category) }
            val currentState = getCurrentState()

            println("category ${input.category}")

            input.category?.let {
                if (it.child.isEmpty()) {
                    postInput(
                        CategoryContract.Inputs.GetProductByCategory(
                            it.id,
                            forceRefresh = true
                        )
                    )
                }

            }

            Unit
        }

        is CategoryContract.Inputs.GetProductByCategory -> {
            val currentState = getCurrentState()
            val pageInfo = currentState.products.getCachedOrNull()?.products?.pageInfo
            observeFlows("GetProductByCategory") {
                listOf(
                    productRepository.getProductsByCategory(
                        slug = input.slug,
                        refreshCache = input.forceRefresh,
                        pageInfo = pageInfo
                    ).map { CategoryContract.Inputs.UpdateProductByCategory(it) }
                )
            }
        }

        is CategoryContract.Inputs.UpdateProductByCategory -> {
            updateState { it.copy(products = input.products) }
            postInput(CategoryContract.Inputs.AddHomeProducts(products = input.products))
        }

        is CategoryContract.Inputs.GoCategoryPage -> {
            postEvent(CategoryContract.Events.GoCategoryPage(input.slug))
        }

        is CategoryContract.Inputs.AddToCart -> {
            cartRepository.postInput(CartRepositoryContract.Inputs.AddItem(variantId = input.variantId))
        }

        is CategoryContract.Inputs.FetchCarts -> {
            observeFlows("FetchCarts") {
                listOf(
                    cartRepository.getCarts(input.forceRefresh)
                        .map { CategoryContract.Inputs.UpdateCarts(it) }
                )
            }
        }

        CategoryContract.Inputs.GoCheckoutPage -> {

        }

        is CategoryContract.Inputs.UpdateCarts -> {
            updateState { it.copy(carts = input.carts) }
        }

        is CategoryContract.Inputs.AddHomeProducts -> {
            val currentState = getCurrentState()
            updateState {
                it.copy(allProducts = currentState.allProducts.toMutableList().apply {
                    input.products.getCachedOrNull()?.products?.edges?.let {
                        addAll(it)
                    }
                })
            }
        }
        is CategoryContract.Inputs.GoProductDetailsPage -> {
            postEvent(CategoryContract.Events.GoProductDetailsPage(input.slug, input.variantId))
        }
    }
}
