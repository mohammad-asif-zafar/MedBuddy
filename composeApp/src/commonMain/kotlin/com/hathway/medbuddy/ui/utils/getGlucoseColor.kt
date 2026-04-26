package com.hathway.medbuddy.ui.utils

import androidx.compose.ui.FrameRateCategory.Companion.High
import androidx.compose.ui.FrameRateCategory.Companion.Normal
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality.Companion.Low

fun getGlucoseColor(value: Int?): Any {
    if (value == null) return Color.LightGray

    return when {
        value < 140 -> Low
        value in 140..199 -> Normal
        else -> High
    }
}