package com.musafira2z.store.repository.auth

import com.apollographql.apollo3.ApolloClient
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.observeFlows
import com.copperleaf.ballast.postInput
import com.copperleaf.ballast.repository.bus.EventBus
import com.copperleaf.ballast.repository.bus.observeInputsFromBus
import com.copperleaf.ballast.repository.cache.fetchWithCache
import com.musafira2z.store.LoginCustomerMutation
import com.musafira2z.store.repository.settings.SettingsRepository
import com.musafira2z.store.utils.ResponseResource

class AuthRepositoryInputHandler(
    private val eventBus: EventBus,
    private val settingsRepository: SettingsRepository,
    private val apolloClient: ApolloClient
) : InputHandler<
        AuthRepositoryContract.Inputs,
        Any,
        AuthRepositoryContract.State> {
    override suspend fun InputHandlerScope<
            AuthRepositoryContract.Inputs,
            Any,
            AuthRepositoryContract.State>.handleInput(
        input: AuthRepositoryContract.Inputs
    ) = when (input) {
        is AuthRepositoryContract.Inputs.ClearCaches -> {
            updateState { AuthRepositoryContract.State() }
        }
        is AuthRepositoryContract.Inputs.Initialize -> {
            val previousState = getCurrentState()

            if (!previousState.initialized) {
                updateState { it.copy(initialized = true) }
                // start observing flows here
                logger.debug("initializing")
                observeFlows(
                    key = "Observe account changes",
                    eventBus
                        .observeInputsFromBus<AuthRepositoryContract.Inputs>(),
                )
            } else {
                logger.debug("already initialized")
                noOp()
            }
        }
        is AuthRepositoryContract.Inputs.RefreshAllCaches -> {
            // then refresh all the caches in this repository
            val currentState = getCurrentState()
            if (currentState.dataListInitialized) {
                postInput(AuthRepositoryContract.Inputs.RefreshDataList(true))
            }

            Unit
        }

        is AuthRepositoryContract.Inputs.DataListUpdated -> {
            updateState { it.copy(dataList = input.dataList) }
        }
        is AuthRepositoryContract.Inputs.RefreshDataList -> {
            updateState { it.copy(dataListInitialized = true) }
            fetchWithCache(
                input = input,
                forceRefresh = input.forceRefresh,
                getValue = { it.dataList },
                updateState = { AuthRepositoryContract.Inputs.DataListUpdated(it) },
                doFetch = { TODO() },
            )
        }
        is AuthRepositoryContract.Inputs.LoginUser -> {
            sideJob("LoginUser") {
                val resource = try {
                    val authenticateMutation =
                        LoginCustomerMutation(email = input.username, password = input.password)
                    val response = apolloClient.mutation(authenticateMutation).execute()
                    if (response.data == null || response.errors?.isNotEmpty() == true) {
                        throw Exception("Login failed")
                    }
                    ResponseResource.Success(response.data!!.tokenCreate)
                } catch (e: Exception) {
                    e.printStackTrace()
                    ResponseResource.Error(exception = e)
                }
                postInput(AuthRepositoryContract.Inputs.UpdateLoginResponse(resource))
            }
        }
        is AuthRepositoryContract.Inputs.UpdateLoginResponse -> {
            updateState { it.copy(loginResponse = input.loginResponse) }
            when (val data = input.loginResponse) {
                is ResponseResource.Error -> {

                }
                ResponseResource.Idle -> {

                }
                ResponseResource.Loading -> {

                }
                is ResponseResource.Success -> {
                    postInput(AuthRepositoryContract.Inputs.FetchLoginStatus)
                }
            }
        }
        AuthRepositoryContract.Inputs.FetchLoginStatus -> {
            postInput(AuthRepositoryContract.Inputs.UpdateLoginStatus(settingsRepository.authToken != null))
        }
        is AuthRepositoryContract.Inputs.UpdateLoginStatus -> {
            updateState { it.copy(isLoggedIn = input.isLoggedIn) }
        }
    }
}
