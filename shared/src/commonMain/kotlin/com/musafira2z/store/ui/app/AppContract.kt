package com.musafira2z.store.ui.app

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.LoginCustomerMutation
import com.musafira2z.store.MainMenuQuery
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.utils.ResponseResource

object AppContract {
    data class State(
        val loading: Boolean = false,
        val carts: Cached<CheckoutDetailsFragment> = Cached.NotLoaded(),
        val categories: Cached<MainMenuQuery.Data> = Cached.NotLoaded(),
        val isLoggedIn: Boolean = false,
        val loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?> = ResponseResource.Idle
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object GoBack : Inputs()
        data class FetchCarts(val forceRefresh: Boolean) : Inputs()
        data class UpdateCarts(val carts: Cached<CheckoutDetailsFragment>) : Inputs()

        data class FetchCategories(val forceRefresh: Boolean) : Inputs()
        data class UpdateCategories(val categories: Cached<MainMenuQuery.Data>) : Inputs()

        data class LoginUser(val username: String, val password: String) : Inputs()
        data class UpdateLoginResponse(val loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?>) :
            Inputs()

        data class UpdateLoginStatus(val isLoggedIn: Boolean) : Inputs()
        object FetchLoginStatus : Inputs()

        data class Increment(val lineId: String) : Inputs()
        data class Decrement(val lineId: String) : Inputs()
    }

    sealed class Events {
        object NavigateUp : Events()
    }
}
