package com.hathway.medbuddy.util

import com.hathway.medbuddy.domain.model.TimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun getNowInstant(): Instant {
    return Instant.fromEpochMilliseconds(getNowEpochMillis())
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