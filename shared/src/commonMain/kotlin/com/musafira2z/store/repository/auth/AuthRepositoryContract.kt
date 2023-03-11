package com.musafira2z.store.repository.auth

import com.copperleaf.ballast.repository.cache.Cached
import com.musafira2z.store.LoginCustomerMutation
import com.musafira2z.store.RegisterCustomerMutation
import com.musafira2z.store.utils.ResponseResource

object AuthRepositoryContract {
    data class State(
        val initialized: Boolean = false,

        val dataListInitialized: Boolean = false,
        val dataList: Cached<List<String>> = Cached.NotLoaded(),
        val loginResponse: ResponseResource<LoginCustomerMutation.TokenCreate?> = ResponseResource.Idle,
        val signupResponse: ResponseResource<RegisterCustomerMutation.AccountRegister?> = ResponseResource.Idle,
        val isLoggedIn: Boolean = false
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
    }
}
