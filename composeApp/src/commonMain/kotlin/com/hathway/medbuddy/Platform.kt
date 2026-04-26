package com.hathway.medbuddy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform