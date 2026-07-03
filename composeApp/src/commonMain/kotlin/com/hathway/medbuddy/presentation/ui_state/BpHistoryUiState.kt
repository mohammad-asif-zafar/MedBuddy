package com.hathway.medbuddy.presentation.ui_state

data class BpHistoryUiState(
    val avgSystolic: Int = 0,
    val avgDiastolic: Int = 0,
    val lowestSystolic: Int = 0,
    val lowestDiastolic: Int = 0,
    val highestSystolic: Int = 0,
    val highestDiastolic: Int = 0,
    val trendData: List<BpDualTrendPoint> = emptyList()
)

data class BpDualTrendPoint(
    val label: String, val systolic: Int, val diastolic: Int
)
