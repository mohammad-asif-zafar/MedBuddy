package com.hathway.medbuddy.util

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
