package com.musafira2z.store


class BrowserPlatform : Platform {
    override val name: String = "Browser"
}

actual fun getPlatform(): Platform = BrowserPlatform()