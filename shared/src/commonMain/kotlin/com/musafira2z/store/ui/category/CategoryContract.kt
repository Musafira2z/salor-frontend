package com.musafira2z.store.ui.category

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.MainMenuQuery
import com.musafira2z.store.ProductCollectionQuery
import com.musafira2z.store.data.CategoryItem
import com.musafira2z.store.fragment.CheckoutDetailsFragment

object CategoryContract {
    data class State(
        val loading: Boolean = false,
        val slug: String = "",
        val categories: Cached<MainMenuQuery.Data> = Cached.NotLoaded(),
        val category: CategoryItem? = null,
        val products: Cached<ProductCollectionQuery.Data> = Cached.NotLoaded(),
        val carts: Cached<CheckoutDetailsFragment> = Cached.NotLoaded()
    )

    sealed class Inputs {
        data class Initialize(val slug: String) : Inputs()
        object GoBack : Inputs()
        object FetchCategories : Inputs()
        data class UpdateCategories(val categories: Cached<MainMenuQuery.Data>) : Inputs()
        data class UpdateCategory(val category: CategoryItem?) : Inputs()
        data class GetProductByCategory(val slug: String, val forceRefresh: Boolean) : Inputs()
        data class UpdateProductByCategory(
            val products: Cached<ProductCollectionQuery.Data>
        ) : Inputs()

        data class GoCategoryPage(val slug: String) : Inputs()
        data class AddToCart(val variantId: String) : Inputs()
        object GoCheckoutPage : Inputs()

        data class FetchCarts(val forceRefresh: Boolean) : Inputs()
        data class UpdateCarts(val carts: Cached<CheckoutDetailsFragment>) : Inputs()

    }

    sealed class Events {
        object NavigateUp : Events()
        data class GoCategoryPage(val slug: String) : Events()
    }
}
