package com.hathway.medbuddy.ui.datasoure

data class GlucoseRecord(
    val date: String,
    val bbf: Int?,
    val abf: Int?,
    val afternoon: Int?,
    val night: Int?
)