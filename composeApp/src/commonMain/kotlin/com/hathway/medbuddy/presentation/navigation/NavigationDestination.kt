package com.hathway.medbuddy.presentation.navigation

import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource

enum class NavigationDestination(
    val icon: DrawableResource? = null,
    val isFloatingActionButton: Boolean = false,
    val isVisibleInBottomBar: Boolean = true
) {
    HOME(
        icon = Res.drawable.ic_nav_home
    ),
    HISTORY(
        icon = Res.drawable.ic_nav_history
    ),

    ADD(
        icon = Res.drawable.ic_nav_add, isFloatingActionButton = true
    ),
    REPORTS(
        icon = Res.drawable.ic_nav_reports
    ),
    PROFILE(
        icon = Res.drawable.ic_nav_profile
    ),

    NOTIFICATIONS(
        icon = null, isVisibleInBottomBar = false
    )
}
