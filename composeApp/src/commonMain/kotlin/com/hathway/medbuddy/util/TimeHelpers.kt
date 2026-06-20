package com.hathway.medbuddy.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.hathway.medbuddy.domain.model.GlucoseTarget
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.ValidationResult
import com.hathway.medbuddy.presentation.theme.AfternoonOrange
import com.hathway.medbuddy.presentation.theme.EveningIndigo
import com.hathway.medbuddy.presentation.theme.MorningAmber
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getNowInstant(): Instant {
    return Instant.fromEpochMilliseconds(getNowEpochMillis())
}

fun formatDisplayDate(date: LocalDate): String {
    val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }

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
        hour < 12 -> MorningAmber
        hour < 17 -> AfternoonOrange
        else -> EveningIndigo
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

fun parseDisplayDate(date: String): LocalDate {
    // 1. Instantly parse if the string matches standard ISO format (e.g., "2026-06-19")
    if (date.contains("-")) {
        return try {
            LocalDate.parse(date)
        } catch (e: Exception) {
            // Fallback default token to protect your app from corrupted strings
            LocalDate(2026, 1, 1)
        }
    }

    // 2. Legacy parsing engine fallback for old strings (e.g., "9 April 2026")
    val parts = date.split(" ")
    if (parts.size < 3) return LocalDate(2026, 1, 1) // Layout structure safety guard

    val day = parts[0].toIntOrNull() ?: 1
    val monthName = parts[1].lowercase()
    val year = parts[2].toIntOrNull() ?: 2026

    val month = when (monthName) {
        "january" -> 1
        "february" -> 2
        "march" -> 3
        "april" -> 4
        "may" -> 5
        "june" -> 6
        "july" -> 7
        "august" -> 8
        "september" -> 9
        "october" -> 10
        "november" -> 11
        "december" -> 12
        else -> 1
    }

    return LocalDate(year, month, day)
}


fun calculateGlucoseTargets(
    valueMgMl: Double, mealType: TimePeriod, hasDiabetes: Boolean
): GlucoseTarget {
    val (min, max) = if (hasDiabetes) {
        when (mealType) {
            TimePeriod.BEFORE_BREAKFAST, TimePeriod.BEFORE_LUNCH, TimePeriod.BEFORE_DINNER -> 0.80 to 1.30
            TimePeriod.AFTER_BREAKFAST, TimePeriod.AFTER_LUNCH, TimePeriod.AFTER_DINNER -> 0.0 to 1.80
            TimePeriod.BEDTIME -> 0.90 to 1.50
        }
    } else {
        when (mealType) {
            TimePeriod.BEFORE_BREAKFAST -> 0.0 to 1.00
            TimePeriod.BEFORE_LUNCH, TimePeriod.BEFORE_DINNER -> 0.70 to 0.99
            TimePeriod.AFTER_BREAKFAST, TimePeriod.AFTER_LUNCH, TimePeriod.AFTER_DINNER -> 0.0 to 1.40
            TimePeriod.BEDTIME -> 1.00 to 1.40
        }
    }

    // Check if the current reading falls inside the bounds
    val isInRange = if (min == 0.0) valueMgMl < max else valueMgMl in min..max

    // Generate the status text string
    val statusText = if (isInRange) "Normal" else if (valueMgMl > max) "High" else "Low"

    return GlucoseTarget(minTarget = min, maxTarget = max, statusText = statusText)
}


fun checkTargetRange(
    valueMgMl: Double, timeTag: TimePeriod, hasDiabetes: Boolean
): ValidationResult {
    return if (hasDiabetes) {
        when (timeTag) {
            TimePeriod.BEFORE_BREAKFAST, TimePeriod.BEFORE_LUNCH, TimePeriod.BEFORE_DINNER -> ValidationResult(
                valueMgMl in 0.80..1.30, "Target: 0.80 - 1.30 mg/mL"
            )

            TimePeriod.AFTER_BREAKFAST, TimePeriod.AFTER_LUNCH, TimePeriod.AFTER_DINNER -> ValidationResult(
                valueMgMl < 1.80, "Target: Under 1.80 mg/mL"
            )

            TimePeriod.BEDTIME -> ValidationResult(
                valueMgMl in 0.90..1.50, "Target: 0.90 - 1.50 mg/mL"
            )
        }
    } else {
        when (timeTag) {
            TimePeriod.BEFORE_BREAKFAST -> ValidationResult(
                valueMgMl < 1.00, "Normal: Under 1.00 mg/mL"
            )

            TimePeriod.BEFORE_LUNCH, TimePeriod.BEFORE_DINNER -> ValidationResult(
                valueMgMl in 0.70..0.99, "Normal: 0.70 - 0.99 mg/mL"
            )

            TimePeriod.AFTER_BREAKFAST, TimePeriod.AFTER_LUNCH, TimePeriod.AFTER_DINNER -> ValidationResult(
                valueMgMl < 1.40, "Normal: Under 1.40 mg/mL"
            )

            TimePeriod.BEDTIME -> ValidationResult(
                valueMgMl in 1.00..1.40, "Normal: 1.00 - 1.40 mg/mL"
            )
        }
    }
}
// Add this simple helper utility function at the bottom or top of your file
fun formatOneDecimal(value: Double): String {
    val rounded = (value * 10).toInt() / 10.0
    return rounded.toString()
}
