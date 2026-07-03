package com.hathway.medbuddy.presentation.ui_state

data class BpDashboardUiState(
    val date: String = "",
    val systolic: Int = 0,
    val diastolic: Int = 0,
    val pulse: Int = 0,
    val statusLabel: String = "",
    val trendData: List<BpTrendPoint> = emptyList()
)

data class BpTrendPoint(val label: String, val value: Int)
