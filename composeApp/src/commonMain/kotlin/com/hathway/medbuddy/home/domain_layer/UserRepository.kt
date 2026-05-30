package com.hathway.medbuddy.home.domain_layer

interface UserRepository {
    fun getText(input: String): String
}