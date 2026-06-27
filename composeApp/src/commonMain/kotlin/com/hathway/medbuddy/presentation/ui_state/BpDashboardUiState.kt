package com.hathway.medbuddy.presentation.ui_state

data class BpDashboardUiState(
    val date: String = "23 Jun 2026, 08:30 AM",
    val systolic: Int = 120,
    val diastolic: Int = 80,
    val pulse: Int = 72,
    val statusLabel: String = "Normal",
    val trendData: List<BpTrendPoint> = listOf(
        BpTrendPoint("17 Jun", 88),
        BpTrendPoint("18 Jun", 120),
        BpTrendPoint("19 Jun", 92),
        BpTrendPoint("20 Jun", 82),
        BpTrendPoint("21 Jun", 120),
        BpTrendPoint("22 Jun", 80),
        BpTrendPoint("23 Jun", 118)
    )
)

data class BpTrendPoint(val label: String, val value: Int)
