package com.hathway.medbuddy.domain.model


data class CalendarDay(
    val day: Int, val hasRecord: Boolean, val isSelected: Boolean = false
)
