package com.hathway.medbuddy

sealed class AuthState {
    object Login : AuthState()
    object Loading : AuthState()
    object Home : AuthState()
}