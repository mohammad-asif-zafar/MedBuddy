package com.hathway.medbuddy.presentation.ui_state

data class BpHistoryUiState(
    val avgSystolic: Int = 118,
    val avgDiastolic: Int = 78,
    val lowestSystolic: Int = 110,
    val lowestDiastolic: Int = 70,
    val highestSystolic: Int = 130,
    val highestDiastolic: Int = 85,
    val trendData: List<BpDualTrendPoint> = listOf(
        BpDualTrendPoint("Apr 21", 115, 82),
        BpDualTrendPoint("", 116, 85),
        BpDualTrendPoint("Apr 28", 121, 87),
        BpDualTrendPoint("", 114, 81),
        BpDualTrendPoint("", 110, 80),
        BpDualTrendPoint("May 5", 117, 84),
        BpDualTrendPoint("", 121, 85),
        BpDualTrendPoint("", 114, 80),
        BpDualTrendPoint("May 12", 119, 82),
        BpDualTrendPoint("", 113, 81),
        BpDualTrendPoint("May 19", 123, 87)
    )
)

data class BpDualTrendPoint(
    val label: String, val systolic: Int, val diastolic: Int
)
