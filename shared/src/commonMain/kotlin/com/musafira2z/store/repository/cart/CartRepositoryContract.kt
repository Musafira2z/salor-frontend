package com.musafira2z.store.repository.cart

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.AvailableShippingMethodsQuery
import com.musafira2z.store.CheckoutCompleteMutation
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.type.AddressInput
import com.musafira2z.store.type.CheckoutErrorCode

object CartRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val dataList: Cached<CheckoutDetailsFragment> = Cached.NotLoaded(),
        val shippingMethods: Cached<List<AvailableShippingMethodsQuery.AvailableShippingMethod>> = Cached.NotLoaded(),
        val lastOrder: CheckoutCompleteMutation.CheckoutComplete? = null
    )

    sealed class Inputs {
        object ClearCaches : Inputs()
        object Initialize : Inputs()
        object RefreshAllCaches : Inputs()

        data class RefreshCarts(val forceRefresh: Boolean) : Inputs()
        data class DataListUpdated(val dataList: Cached<CheckoutDetailsFragment>) : Inputs()

        data class RefreshShippingMethod(val forceRefresh: Boolean) : Inputs()
        data class UpdateShippingMethod(val shippingMethods: Cached<List<AvailableShippingMethodsQuery.AvailableShippingMethod>>) :
            Inputs()

        data class CreateCheckout(
            val variantId: String,
            val email: String? = null
        ) : Inputs()

        data class AddItem(
            val variantId: String
        ) : Inputs()

        data class Increment(
            val variantId: String
        ) : Inputs()

        data class Decrement(
            val variantId: String,
            val qty: Int
        ) : Inputs()

        data class AddUserToCart(
            val token: String,
            val email: String
        ) : Inputs()

        data class UpdateQuantity(
            val itemId: String,
            val qty: Int
        ) : Inputs()

        data class RemoveCartItem(
            val itemId: String
        ) : Inputs()

        data class Clear(val token: String) : Inputs()
        data class ApplyPromoCode(val code: String, val checkoutId: String) : Inputs()
        data class SetBillingAddress(val address: AddressInput) : Inputs()
        data class SetShippingAddress(val address: AddressInput) : Inputs()
        data class SetShippingMethod(val methodId: String) : Inputs()
        object Checkout : Inputs()
        data class CreatePayment(val paymentMethodId: String) : Inputs()
        data class UpdateLastOrder(val lastOrder: CheckoutCompleteMutation.CheckoutComplete?) :
            Inputs()

        data class HandleCartError(val errorCodes: List<CheckoutErrorCode>) : Inputs()
        data class CheckoutCustomerAttach(val checkoutEmail: String) : Inputs()
        data class UpdateCheckoutEmail(val checkoutEmail: String) : Inputs()
    }
}
