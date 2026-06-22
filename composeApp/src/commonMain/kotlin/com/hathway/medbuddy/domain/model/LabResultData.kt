package com.hathway.medbuddy.domain.model

import androidx.compose.ui.graphics.Color


data class LabResultData(
    val name: String,
    val value: String,
    val status: String,
    val statusColor: Color,
    val date: String
)
