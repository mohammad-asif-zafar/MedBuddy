package com.hathway.medbuddy

interface Platform {
    val name: String
}
expect fun getPlatform(): Platform
expect fun openPlatformWebUrl(context: Any, url: String)
