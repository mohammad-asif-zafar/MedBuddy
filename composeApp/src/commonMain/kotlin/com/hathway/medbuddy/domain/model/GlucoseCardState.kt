package com.hathway.medbuddy.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

// Define a structured configuration payload matching your dataset
data class GlucoseCardState(
    val timePeriod: TimePeriod, val titleLabel: String,     // e.g., "Before Breakfast (BBF)"
    val value: Int, val timeString: String,     // e.g., "07:57 AM"
    val icon: ImageVector, val iconContainerColor: Color, val iconTint: Color
)