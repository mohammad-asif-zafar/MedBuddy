package com.hathway.medbuddy.domain.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.hathway.medbuddy.ThemeMode


data class ThemeOptionData(
    val mode: ThemeMode,
    val title: String,
    val description: String,
    val icon: ImageVector
)
