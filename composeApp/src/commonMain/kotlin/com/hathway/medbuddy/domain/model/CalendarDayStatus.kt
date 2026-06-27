package com.hathway.medbuddy.domain.model

import kotlinx.datetime.LocalDate

data class CalendarDayStatus(
    val date: LocalDate, val lowCount: Int, val inRangeCount: Int, val highCount: Int
)
