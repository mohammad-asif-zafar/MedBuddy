package com.hathway.medbuddy.domain.model


data class TrendPoint(
    val dateLabel: String, // e.g., "May 16"
    val glucoseValue: Int  // e.g., 120
)