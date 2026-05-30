package com.hathway.medbuddy.navigation

enum class NavigationDestination(
    val title: String,
    val icon: String? = null,
    val isFloatingActionButton: Boolean = false
) {
    HOME("Home", "🏠"),
    SEARCH("Search", "🔍"),
    ADD("Add", "+", isFloatingActionButton = true),
    NOTIFICATIONS("Notifications", "🔔"),
    PROFILE("Profile", "👤")
}
