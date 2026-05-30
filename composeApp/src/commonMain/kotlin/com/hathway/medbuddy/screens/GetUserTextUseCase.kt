package com.hathway.medbuddy.screens

import com.hathway.medbuddy.home.domain_layer.UserRepository

class GetUserTextUseCase(
    private val repository: UserRepository
) {

    operator fun invoke(input: String): String {
        return repository.getText(input)
    }
}