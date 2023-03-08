package com.musafira2z.store.repository.settings

import com.russhwolf.settings.Settings

class SettingsRepository(
    private val settings: Settings
) {
    val checkoutToken: String?
        get() = settings.getStringOrNull("checkout_token")

    val authToken: String?
        get() = settings.getStringOrNull("auth_token")
    val refreshToken: String?
        get() = settings.getStringOrNull("auth_token_refresh")
    val csrfToken: String?
        get() = settings.getStringOrNull("auth_token_csrf")

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

    fun saveCsrfToken(token: String?) {
        if (token != null) {
            settings.putString("auth_token_csrf", token)
            return
        }
        settings.remove("auth_token_csrf")
    }

    fun saveRefreshToken(token: String?) {
        if (token != null) {
            settings.putString("auth_token_refresh", token)
            return
        }
        settings.remove("auth_token_refresh")
    }
}