package com.musafira2z.store.repository.auth

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.*
import com.musafira2z.store.utils.ResponseResource

object AuthRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val dataList: Cached<List<String>> = Cached.NotLoaded(),
        val loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?> = ResponseResource.Idle,
        val signupResponse: ResponseResource<RegisterCustomerMutation.AccountRegister?> = ResponseResource.Idle,
        val isLoggedIn: Boolean = false,
        val me: Cached<CurrentUserDetailsQuery.Me> = Cached.NotLoaded(),
        val address: Cached<CurrentUserAddressesQuery.Me> = Cached.NotLoaded(),
        val orders: Cached<OrdersQuery.Me> = Cached.NotLoaded()
    )

    sealed class Inputs {
        object ClearCaches : Inputs()
        object Initialize : Inputs()
        object RefreshAllCaches : Inputs()

        data class RefreshDataList(val forceRefresh: Boolean) : Inputs()
        data class DataListUpdated(val dataList: Cached<List<String>>) : Inputs()
        data class LoginUser(val username: String, val password: String) : Inputs()

        object SignOut : Inputs()
        data class UpdateLoginResponse(
            val loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?>
        ) : Inputs()

        data class SignupUser(val fullName: String, val username: String, val password: String) :
            Inputs()

        data class UpdateSignupResponse(
            val loginResponse: ResponseResource<RegisterCustomerMutation.AccountRegister?>
        ) : Inputs()

        object FetchLoginStatus : Inputs()
        data class UpdateLoginStatus(val isLoggedIn: Boolean) : Inputs()

        data class GetMe(val forceRefresh: Boolean) : Inputs()
        data class UpdateMe(val me: Cached<CurrentUserDetailsQuery.Me>) : Inputs()

        data class GetAddress(val forceRefresh: Boolean) : Inputs()
        data class UpdateAddress(val address: Cached<CurrentUserAddressesQuery.Me>) : Inputs()

        data class GetOrders(val forceRefresh: Boolean) : Inputs()
        data class UpdateOrders(val orders: Cached<OrdersQuery.Me>) : Inputs()
    }
}
