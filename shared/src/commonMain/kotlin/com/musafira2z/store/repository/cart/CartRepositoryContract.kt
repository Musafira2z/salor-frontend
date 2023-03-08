package com.musafira2z.store.repository.cart

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.CheckoutCompleteMutation
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.type.AddressInput

object CartRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val dataList: Cached<CheckoutDetailsFragment> = Cached.NotLoaded(),
        val lastOrder: CheckoutCompleteMutation.CheckoutComplete? = null
    )

    sealed class Inputs {
        object ClearCaches : Inputs()
        object Initialize : Inputs()
        object RefreshAllCaches : Inputs()

        data class RefreshCarts(val forceRefresh: Boolean) : Inputs()
        data class DataListUpdated(val dataList: Cached<CheckoutDetailsFragment>) : Inputs()

        data class CreateCheckout(
            val variantId: String,
            val email: String? = null
        ) : Inputs()

        data class AddItem(
            val variantId: String
        ) : Inputs()

        data class Increment(
            val lineId: String
        ) : Inputs()

        data class Decrement(
            val lineId: String
        ) : Inputs()

        data class AddUserToCart(
            val token: String,
            val email: String
        ) : Inputs()

        data class UpdateQuantity(
            val itemId: String,
            val qty: Int,
            val token: String
        ) : Inputs()

        data class RemoveCartItem(
            val itemId: String,
            val token: String
        ) : Inputs()

        data class Clear(val token: String) : Inputs()
        data class ApplyPromoCode(val code: String, val checkoutId: String) : Inputs()
        data class SetBillingAddress(val address: AddressInput, val checkoutId: String) : Inputs()
        data class SetShippingMethod(val methodId: String, val token: String) : Inputs()
        data class Checkout(val token: String) : Inputs()
        data class UpdateLastOrder(val lastOrder: CheckoutCompleteMutation.CheckoutComplete?) :
            Inputs()
    }
}
