package com.hathway.medbuddy.ui.utils

import androidx.compose.ui.graphics.Color
import com.hathway.medbuddy.ui.Danger
import com.hathway.medbuddy.ui.Success
import com.hathway.medbuddy.ui.Warning

data class GlucoseStatus(
    val color: Color,
    val label: String,
    val emoji: String
)

fun getGlucoseStatus(value: Int?, timePeriod: String): GlucoseStatus {
    if (value == null) return GlucoseStatus(Color.Gray, "No Data", "⚪")

    // Different ranges based on time period
    val ranges = when (timePeriod) {
        "BEFORE_BREAKFAST" -> Pair(70, 100)
        "AFTER_BREAKFAST" -> Pair(80, 180)
        "BEFORE_LUNCH" -> Pair(80, 130)
        "AFTER_LUNCH" -> Pair(80, 180)
        "BEFORE_DINNER" -> Pair(80, 130)
        "AFTER_DINNER" -> Pair(80, 180)
        "BEDTIME" -> Pair(100, 140)
        else -> Pair(70, 140)
    }

    val (min, max) = ranges

    return when {
        value < min -> GlucoseStatus(Warning, "Low", "🟡")
        value in min..max -> GlucoseStatus(Success, "Normal", "🟢")
        value in (max + 1)..(max + 40) -> GlucoseStatus(Warning, "Slightly High", "🟡")
        else -> GlucoseStatus(Danger, "High", "🔴")
    }
}