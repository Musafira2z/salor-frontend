package com.musafira2z.store.ui.app

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.musafira2z.store.repository.auth.AuthRepository
import com.musafira2z.store.repository.auth.AuthRepositoryContract
import com.musafira2z.store.repository.cart.CartRepository
import com.musafira2z.store.repository.cart.CartRepositoryContract
import com.musafira2z.store.repository.menu.MenuRepository
import com.musafira2z.store.repository.settings.SettingsRepository
import com.musafira2z.store.utils.ResponseResource
import kotlinx.coroutines.flow.map

class AppInputHandler(
    private val cartRepository: CartRepository,
    private val menuRepository: MenuRepository,
    private val authRepository: AuthRepository,
    private val settingsRepository: SettingsRepository
) : InputHandler<
        AppContract.Inputs,
        AppContract.Events,
        AppContract.State> {
    override suspend fun InputHandlerScope<
            AppContract.Inputs,
            AppContract.Events,
            AppContract.State>.handleInput(
        input: AppContract.Inputs
    ) = when (input) {
        is AppContract.Inputs.Initialize -> {
            postInput(AppContract.Inputs.FetchCarts(true))
            postInput(AppContract.Inputs.FetchCategories(true))
            postInput(AppContract.Inputs.FetchLoginStatus)
        }
        is AppContract.Inputs.GoBack -> {
            postEvent(AppContract.Events.NavigateUp)
        }
        is AppContract.Inputs.FetchCarts -> {
            observeFlows("FetchCarts") {
                listOf(
                    cartRepository.getCarts(input.forceRefresh)
                        .map { AppContract.Inputs.UpdateCarts(it) }
                )
            }
        }
        is AppContract.Inputs.UpdateCarts -> {
            updateState { it.copy(carts = input.carts) }
        }
        is AppContract.Inputs.FetchCategories -> {
            observeFlows("FetchCategories") {
                listOf(
                    menuRepository.getAllCategories(input.forceRefresh)
                        .map { AppContract.Inputs.UpdateCategories(it) }
                )
            }
        }
        is AppContract.Inputs.UpdateCategories -> {
            updateState { it.copy(categories = input.categories) }
        }
        is AppContract.Inputs.LoginUser -> {
            updateState { it.copy(loginResponse = ResponseResource.Loading) }
            observeFlows("LoginUser") {
                listOf(
                    authRepository.loginUser(username = input.username, password = input.password)
                        .map { AppContract.Inputs.UpdateLoginResponse(it) }
                )
            }
        }
        is AppContract.Inputs.UpdateLoginResponse -> {
            updateState { it.copy(loginResponse = input.loginResponse) }
            when (val data = input.loginResponse) {
                is ResponseResource.Error -> {

                }
                ResponseResource.Idle -> {

                }
                ResponseResource.Loading -> {

                }
                is ResponseResource.Success -> {
                    val tokens = data.data
                    settingsRepository.saveAuthToken(tokens?.token)
                    settingsRepository.saveRefreshToken(tokens?.refreshToken)
                    settingsRepository.saveCsrfToken(tokens?.csrfToken)
                    postInput(AppContract.Inputs.FetchLoginStatus)
                }
            }
        }
        AppContract.Inputs.FetchLoginStatus -> {
            observeFlows("FetchLoginStatus") {
                listOf(
                    authRepository.isLoggedIn().map { AppContract.Inputs.UpdateLoginStatus(it) }
                )
            }
        }
        is AppContract.Inputs.UpdateLoginStatus -> {
            updateState { it.copy(isLoggedIn = input.isLoggedIn) }
        }
        is AppContract.Inputs.Decrement -> {
            cartRepository.postInput(CartRepositoryContract.Inputs.Decrement(input.lineId))
        }
        is AppContract.Inputs.Increment -> {
            cartRepository.postInput(CartRepositoryContract.Inputs.Increment(input.lineId))
        }
        is AppContract.Inputs.SignUpUser -> {
            updateState { it.copy(signupResponse = ResponseResource.Loading) }
            observeFlows("SignUpUser") {
                listOf(
                    authRepository.signupUser(
                        username = input.username,
                        password = input.password,
                        fullName = input.fullName
                    ).map { AppContract.Inputs.UpdateSignupResponse(it) }
                )
            }
        }
        is AppContract.Inputs.UpdateSignupResponse -> {
            updateState { it.copy(signupResponse = input.signupResponse) }
            postInput(AppContract.Inputs.FetchLoginStatus)
        }
        AppContract.Inputs.SignOut -> {
            sideJob("SignOut") {
                authRepository.postInput(AuthRepositoryContract.Inputs.SignOut)
                postInput(AppContract.Inputs.FetchLoginStatus)
            }
        }
    }
}
