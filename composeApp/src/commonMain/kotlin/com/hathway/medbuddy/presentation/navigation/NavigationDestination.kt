package com.hathway.medbuddy.presentation.navigation

enum class NavigationDestination(
    val title: String,
    val icon: String? = null,
    val isFloatingActionButton: Boolean = false
) {
    HOME("Home", "🏠"),
    ADD("Add", "+", isFloatingActionButton = true),
    PROFILE("Profile", "👤")
}
