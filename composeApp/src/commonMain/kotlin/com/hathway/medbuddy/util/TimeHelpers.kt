package com.hathway.medbuddy.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.hathway.medbuddy.domain.model.TimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getNowInstant(): Instant {
    return Instant.fromEpochMilliseconds(getNowEpochMillis())
}

fun formatDisplayDate(date: LocalDate): String {
    val month = date.month.name
        .lowercase()
        .replaceFirstChar { it.uppercase() }

    return "${date.dayOfMonth} $month ${date.year}"
}
fun getGreetingIcon(): String {
    val hour = getNowLocalDateTime().hour
    return when {
        hour < 12 -> "☀️"
        hour < 17 -> "🌤️"
        else -> "🌙"
    }
}
fun getGreetingIconImageVector(): ImageVector {
    val hour = getNowLocalDateTime().hour
    return when {
        hour < 12 -> Icons.Outlined.WbSunny
        hour < 17 -> Icons.Outlined.LightMode
        else -> Icons.Outlined.Bedtime
    }


}

fun greetingIconColor(): Color {
    val hour = getNowLocalDateTime().hour
    return when {
        hour < 12 -> Color(0xFFF59E0B) // Morning Amber
        hour < 17 -> Color(0xFFFB923C) // Afternoon Orange
        else -> Color(0xFF6366F1) // Evening Indigo
    }


}



fun getNowLocalDateTime(): LocalDateTime {
    return getNowInstant().toLocalDateTime(TimeZone.currentSystemDefault())
}

fun getCurrentTime12Hour(): String {

    val currentTime = getNowLocalDateTime().time

    val hour24 = currentTime.hour
    val minute = currentTime.minute

    val amPm = if (hour24 >= 12) "PM" else "AM"

    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }

    return "${hour12}:${minute.toString().padStart(2, '0')} $amPm"
}

fun TimePeriod.getDisplayName(): String {
    return this.name.lowercase().split("_")
        .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
        // Handle custom overrides if enum doesn't map 1:1 to UI text
        .replace("Bedtime", "Before Bed")
}