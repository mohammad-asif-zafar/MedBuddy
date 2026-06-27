package com.hathway.medbuddy.domain.model

import androidx.compose.ui.graphics.Color

enum class BpStatus(val label: String, val color: Color) {
    NORMAL("Normal", Color(0xFF2E7D32)),
    ELEVATED("Elevated", Color(0xFFE65100))
}

data class BpReading(
    val time: String,
    val systolic: Int,
    val diastolic: Int,
    val pulse: Int,
    val status: BpStatus
)

// Grouped structure for the LazyColumn
val mockBpHistory = mapOf(
    "May 20, 2024" to listOf(
        BpReading("8:30 AM", 120, 80, 72, BpStatus.NORMAL)
    ),
    "May 19, 2024" to listOf(
        BpReading("7:45 PM", 122, 82, 74, BpStatus.NORMAL),
        BpReading("8:10 AM", 118, 76, 70, BpStatus.NORMAL)
    ),
    "May 18, 2024" to listOf(
        BpReading("9:00 PM", 130, 85, 76, BpStatus.ELEVATED),
        BpReading("8:15 AM", 115, 75, 68, BpStatus.NORMAL)
    )
)