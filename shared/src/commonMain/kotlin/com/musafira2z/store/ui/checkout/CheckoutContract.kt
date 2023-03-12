package com.musafira2z.store.ui.checkout

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.CurrentUserAddressesQuery
import com.musafira2z.store.CurrentUserDetailsQuery
import com.musafira2z.store.OrdersQuery
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.type.AddressInput
import com.musafira2z.store.utils.ResponseResource

object CheckoutContract {
    data class State(
        val loading: Boolean = false,
        val carts: Cached<CheckoutDetailsFragment> = Cached.NotLoaded(),
        val me: Cached<CurrentUserDetailsQuery.Me> = Cached.NotLoaded(),
        val address: Cached<CurrentUserAddressesQuery.Me> = Cached.NotLoaded(),
        val orders: Cached<OrdersQuery.Me> = Cached.NotLoaded(),
        val setBillingResponse: ResponseResource<Boolean> = ResponseResource.Idle
    )

    sealed class Inputs {
        object Initialize : Inputs()
        data class FetchCarts(val forceRefresh: Boolean) : Inputs()
        data class UpdateCarts(val carts: Cached<CheckoutDetailsFragment>) : Inputs()
        data class FetchMe(val forceRefresh: Boolean) : Inputs()
        data class UpdateMe(val me: Cached<CurrentUserDetailsQuery.Me>) : Inputs()

        data class FetchAddress(val forceRefresh: Boolean) : Inputs()
        data class UpdateAddress(val address: Cached<CurrentUserAddressesQuery.Me>) : Inputs()
        data class FetchOrders(val forceRefresh: Boolean) : Inputs()
        data class UpdateOrders(val orders: Cached<OrdersQuery.Me>) : Inputs()

        data class SetBillingAddress(val address: AddressInput) : Inputs()
        data class SetShippingMethod(val methodId: String) : Inputs()
        data class UpdateBillingResponse(val billingResponse: ResponseResource<Boolean>) : Inputs()

        data class PlaceOrder(val paymentMethodId: String) : Inputs()

        object GoBack : Inputs()

    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
