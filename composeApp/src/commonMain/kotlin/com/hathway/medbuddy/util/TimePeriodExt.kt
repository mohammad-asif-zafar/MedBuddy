package com.hathway.medbuddy.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.DinnerDining
import androidx.compose.material.icons.outlined.FreeBreakfast
import androidx.compose.material.icons.outlined.LunchDining
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.hathway.medbuddy.domain.model.TimePeriod

fun TimePeriod.iconColor(): Color {
    return when (this) {
        TimePeriod.BEFORE_BREAKFAST -> Color(0xFFF59E0B)
        TimePeriod.AFTER_BREAKFAST -> Color(0xFFFB923C)

        TimePeriod.BEFORE_LUNCH -> Color(0xFF22C55E)
        TimePeriod.AFTER_LUNCH -> Color(0xFF14B8A6)

        TimePeriod.BEFORE_DINNER -> Color(0xFF8B5CF6)
        TimePeriod.BEDTIME -> Color(0xFF6366F1)
        else -> {
            Color(0xFF6366F1)
        }
    }
}

fun TimePeriod.icon(): ImageVector {
    return when (this) {

        TimePeriod.BEFORE_BREAKFAST -> Icons.Outlined.WbSunny

        TimePeriod.AFTER_BREAKFAST -> Icons.Outlined.FreeBreakfast

        TimePeriod.BEFORE_LUNCH -> Icons.Outlined.Restaurant

        TimePeriod.AFTER_LUNCH -> Icons.Outlined.LunchDining

        TimePeriod.BEFORE_DINNER -> Icons.Outlined.DinnerDining

        TimePeriod.BEDTIME -> Icons.Outlined.Bedtime

        else -> {
            Icons.Outlined.Bedtime

        }
    }
}