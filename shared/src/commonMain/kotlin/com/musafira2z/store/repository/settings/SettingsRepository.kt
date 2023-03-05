package com.musafira2z.store.repository.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.nullableString

class SettingsRepository(
    private val settings: Settings
) {
    val checkoutToken
        get() = settings.nullableString("checkout_token")
    val authToken
        get() = settings.nullableString("auth_token")

    fun saveCheckoutToken(token: String?) {
        if (token != null) {
            settings.putString("checkout_token", token)
            return
        }
        settings.remove("checkout_token")
    }

    fun saveAuthToken(token: String?) {
        if (token != null) {
            settings.putString("auth_token", token)
            return
        }
        settings.remove("auth_token")
    }
}