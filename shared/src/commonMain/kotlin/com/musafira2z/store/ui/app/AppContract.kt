package com.musafira2z.store.ui.app

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.CurrentUserDetailsQuery
import com.musafira2z.store.LoginCustomerMutation
import com.musafira2z.store.MainMenuQuery
import com.musafira2z.store.RegisterCustomerMutation
import com.musafira2z.store.fragment.CheckoutDetailsFragment
import com.musafira2z.store.utils.ResponseResource

object AppContract {
    data class State(
        val loading: Boolean = false,
        val carts: Cached<CheckoutDetailsFragment> = Cached.NotLoaded(),
        val categories: Cached<MainMenuQuery.Data> = Cached.NotLoaded(),
        val isLoggedIn: Boolean = false,
        val loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?> = ResponseResource.Idle,
        val signupResponse: ResponseResource<RegisterCustomerMutation.AccountRegister?> = ResponseResource.Idle,
        val me: Cached<CurrentUserDetailsQuery.Me> = Cached.NotLoaded(),
    )

    sealed class Inputs {
        object Initialize : Inputs()
        object GoBack : Inputs()
        data class FetchCarts(val forceRefresh: Boolean) : Inputs()
        data class UpdateCarts(val carts: Cached<CheckoutDetailsFragment>) : Inputs()

        data class FetchCategories(val forceRefresh: Boolean) : Inputs()
        data class UpdateCategories(val categories: Cached<MainMenuQuery.Data>) : Inputs()

        data class LoginUser(val username: String, val password: String) : Inputs()
        data class ForgetPassword(val username: String) : Inputs()
        data class SignUpUser(val fullName: String, val username: String, val password: String) :
            Inputs()

        data class UpdateLoginResponse(val loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?>) :
            Inputs()

        data class UpdateSignupResponse(val signupResponse: ResponseResource<RegisterCustomerMutation.AccountRegister?>) :
            Inputs()

        data class UpdateLoginStatus(val isLoggedIn: Boolean) : Inputs()
        object FetchLoginStatus : Inputs()
        object SignOut : Inputs()

        data class Increment(val variantId: String) : Inputs()
        data class Decrement(val variantId: String, val qty: Int) : Inputs()
        data class RemoveLine(val lineId: String) : Inputs()

        data class GoSearchPage(val filter: String?) : Inputs()


        data class GetMe(val forceRefresh: Boolean) : Inputs()
        data class UpdateMe(val me: Cached<CurrentUserDetailsQuery.Me>) : Inputs()

    }

    sealed class Events {
        object NavigateUp : Events()
        data class GoSearchPage(val filter: String?) : Events()
    }
}
