package com.musafira2z.store

import io.ktor.client.*
import io.ktor.client.engine.*

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

val defaultChannel = "default"