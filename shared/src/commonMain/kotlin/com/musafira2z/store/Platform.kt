package com.musafira2z.store

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform