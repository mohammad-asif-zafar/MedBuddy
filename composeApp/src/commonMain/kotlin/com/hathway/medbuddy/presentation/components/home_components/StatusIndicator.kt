package com.hathway.medbuddy.presentation.components.home_components

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
import com.hathway.medbuddy.presentation.theme.Danger
import com.hathway.medbuddy.presentation.theme.Info
import com.hathway.medbuddy.presentation.theme.Success
import com.hathway.medbuddy.presentation.theme.Warning
import com.hathway.medbuddy.presentation.viewmodel.GlucoseStatus

import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatusIndicator(
    status: GlucoseStatus
) {
    val statusInfo = when (status) {
        GlucoseStatus.Low -> Triple("🔴", stringResource(Res.string.low), Color(0xFFE53935))
        GlucoseStatus.Normal -> Triple("🟢", stringResource(Res.string.normal), Color(0xFF34C759))
        GlucoseStatus.AboveTarget -> Triple(
            "🟡", stringResource(Res.string.above_target), Color(0xFFFF9500)
        )

        GlucoseStatus.High -> Triple("🔴", stringResource(Res.string.high), Color(0xFFE53935))
    }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = statusInfo.first, fontSize = 32.sp
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
    val statusColor = when (text.lowercase()) {
        stringResource(Res.string.normal).lowercase() -> Success
        stringResource(Res.string.low).lowercase() -> Warning
        stringResource(Res.string.above_target).lowercase() -> Danger
        stringResource(Res.string.high).lowercase() -> Danger
        else -> Success
    }
    Surface(
        shape = RoundedCornerShape(50), color = statusColor.copy(alpha = 0.15f)
    ) {
        Text(
            text = text, modifier = Modifier.padding(
                horizontal = 12.dp, vertical = 6.dp
            )
        )
    }
}