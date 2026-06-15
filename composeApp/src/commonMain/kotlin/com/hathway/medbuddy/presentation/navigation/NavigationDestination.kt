package com.hathway.medbuddy.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertChartOutlined
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationDestination(
    val icon: ImageVector, val isFloatingActionButton: Boolean = false
) {
    HOME(Icons.Outlined.Home),
    HISTORY(Icons.Outlined.Analytics),
    ADD(Icons.Default.Add, isFloatingActionButton = true),
    REPORTS(Icons.Outlined.InsertChartOutlined),
    PROFILE(Icons.Outlined.Person)
}
