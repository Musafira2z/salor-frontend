package com.musafira2z.store

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*


class BrowserPlatform : Platform {
    override val name: String = "Browser"
}

actual fun getPlatform(): Platform = BrowserPlatform()

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Js) {
    config(this)
}