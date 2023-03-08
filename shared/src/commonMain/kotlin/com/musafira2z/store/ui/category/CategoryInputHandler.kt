package com.musafira2z.store.ui.category

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.cache.getCachedOrNull
import com.musafira2z.store.repository.menu.MenuRepository
import com.musafira2z.store.repository.product.ProductRepository
import kotlinx.coroutines.flow.map

class CategoryInputHandler(
    private val menuRepository: MenuRepository,
    private val productRepository: ProductRepository
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
        }
        is CategoryContract.Inputs.GoBack -> {
            postEvent(CategoryContract.Events.NavigateUp)
        }
        CategoryContract.Inputs.FetchCategories -> {
            observeFlows("FetchCategories") {
                listOf(
                    menuRepository.getAllCategories(false)
                        .map { CategoryContract.Inputs.UpdateCategories(it) }
                )
            }
        }
        is CategoryContract.Inputs.UpdateCategories -> {
            val currentState = getCurrentState()
            updateState { it.copy(categories = input.categories) }
            sideJob("GetCategories") {
                val category = input.categories.getCachedOrNull()?.menu?.items?.firstOrNull {
                    it.menuItemWithChildrenFragment.category?.slug == currentState.slug
                }
                postInput(CategoryContract.Inputs.UpdateCategory(category?.menuItemWithChildrenFragment))
            }
        }
        is CategoryContract.Inputs.UpdateCategory -> {
            updateState { it.copy(category = input.category) }
            if (input.category?.children.isNullOrEmpty()) {
                input.category?.category?.let {
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
            observeFlows("GetProductByCategory") {
                listOf(
                    productRepository.getProductsByCategory(
                        slug = input.slug,
                        refreshCache = input.forceRefresh
                    ).map { CategoryContract.Inputs.UpdateProductByCategory(it) }
                )
            }
        }
        is CategoryContract.Inputs.UpdateProductByCategory -> {
            updateState { it.copy(products = input.products) }
        }
    }
}
