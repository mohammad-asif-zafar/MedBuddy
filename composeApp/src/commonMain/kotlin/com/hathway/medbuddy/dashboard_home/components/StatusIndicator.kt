package com.hathway.medbuddy.dashboard_home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.dashboard_home.GlucoseStatus

@Composable
fun StatusIndicator(
    status: GlucoseStatus
) {
    val statusInfo = when (status) {
        GlucoseStatus.Low -> Triple("🔴", "Low", Color(0xFFE53935))
        GlucoseStatus.Normal -> Triple("🟢", "Normal", Color(0xFF34C759))
        GlucoseStatus.AboveTarget -> Triple("🟡", "Above Target", Color(0xFFFF9500))
        GlucoseStatus.High -> Triple("🔴", "High", Color(0xFFE53935))
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = statusInfo.first,
            fontSize = 32.sp
        )
        Text(
            text = statusInfo.second,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = statusInfo.third
        )
    }
}

@Composable
fun StatusChip(
    text: String
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
        )
    }
}