package com.musafira2z.store.repository.settings

import com.russhwolf.settings.Settings

class SettingsRepository(
    private val settings: Settings
) {
    val checkoutToken: String?
        get() = settings.getStringOrNull("checkout_token")
    val checkoutId: String?
        get() = settings.getStringOrNull("checkout_id")

    val authToken: String?
        get() = settings.getStringOrNull("auth_token")
    val authTokenExpire: Long?
        get() = settings.getLongOrNull("auth_token_expire")
    val refreshToken: String?
        get() = settings.getStringOrNull("auth_token_refresh")
    val csrfToken: String?
        get() = settings.getStringOrNull("auth_token_csrf")

    val channel: String?
        get() = settings.getStringOrNull("auth_channel")

    fun saveCheckoutToken(token: String?) {
        if (token != null) {
            settings.putString("checkout_token", token)
            return
        }
        settings.remove("checkout_token")
    }

    fun saveCheckoutId(token: String?) {
        if (token != null) {
            settings.putString("checkout_id", token)
            return
        }
        settings.remove("checkout_id")
    }

    fun saveAuthToken(token: String?) {
        if (token != null) {
            settings.putString("auth_token", token)
            return
        }
        settings.remove("auth_token")
    }
    fun saveAuthTokenExpire(token: Long?) {
        if (token != null) {
            settings.putLong("auth_token_expire", token)
            return
        }
        settings.remove("auth_token_expire")
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

    fun saveAuthChannel(token: String?) {
        if (token != null) {
            settings.putString("auth_channel", token)
            return
        }
        settings.putString("auth_channel", "default")
    }
}