@file:JvmName("PlatformKtJvm")

package com.musafira2z.store

import io.ktor.client.*
import kotlin.jvm.JvmName

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

val normalChannel = "default"
val baseUrl = "http://localhost:8080"
