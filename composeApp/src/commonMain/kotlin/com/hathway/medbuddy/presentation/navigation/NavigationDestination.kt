package com.hathway.medbuddy.presentation.navigation

enum class NavigationDestination(
    val icon: String? = null,
    val isFloatingActionButton: Boolean = false
) {
    HOME("🏠"),
    ADD("+", isFloatingActionButton = true),
    PROFILE("👤")
}
