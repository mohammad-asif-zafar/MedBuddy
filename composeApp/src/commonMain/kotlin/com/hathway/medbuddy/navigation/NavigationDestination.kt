package com.hathway.medbuddy.navigation

import androidx.compose.ui.graphics.vector.ImageVector

// Simple icon representations using basic shapes
// In a real app, you would use proper Material Icons
enum class NavigationDestination(
    val title: String,
    val iconSymbol: String
) {
    HOME("Home", "🏠"),
    SEARCH("Search", "🔍"),
    ADD("Add", "➕"),
    NOTIFICATIONS("Notifications", "🔔"),
    PROFILE("Profile", "👤")
}
