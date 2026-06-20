package com.hathway.medbuddy.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertChartOutlined
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationDestination(
    // ✅ Make icon nullable with a default value of null for full-screen targets
    val icon: ImageVector? = null,
    val isFloatingActionButton: Boolean = false,
    // ✅ Add a flag to easily control visibility in the bottom navigation item loop
    val isVisibleInBottomBar: Boolean = true
) {
    HOME(icon = Icons.Outlined.Home),
    HISTORY(icon = Icons.Outlined.Analytics),
    ADD(icon = Icons.Default.Add, isFloatingActionButton = true),
    REPORTS(icon = Icons.Outlined.InsertChartOutlined),
    PROFILE(icon = Icons.Outlined.Person),

    // ✅ Uses default constructor parameters seamlessly without breaking compilation rules
    NOTIFICATIONS(icon = Icons.Outlined.Notifications, isVisibleInBottomBar = false)
}
