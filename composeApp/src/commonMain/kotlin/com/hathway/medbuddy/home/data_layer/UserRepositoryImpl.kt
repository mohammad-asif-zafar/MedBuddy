package com.hathway.medbuddy.home.data_layer

import com.hathway.medbuddy.home.domain_layer.UserRepository

class UserRepositoryImpl : UserRepository {

    override fun getText(input: String): String {
        return "Hello $input"
    }
}