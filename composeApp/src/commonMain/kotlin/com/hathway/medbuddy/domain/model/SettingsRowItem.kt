package com.hathway.medbuddy.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class SettingsRowItem(
    val icon: ImageVector? = null,
    val title: String,
    val subtitle: String? = null,
    val iconBgColor: Color = Color.Transparent,
    val iconColor: Color = Color.White,
    val onClick: () -> Unit = {}
)

data class GridThanksItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val iconBgColor: Color,
    val iconColor: Color
)
