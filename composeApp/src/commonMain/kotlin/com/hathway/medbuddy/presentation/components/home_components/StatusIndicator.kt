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
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.*
import com.hathway.medbuddy.presentation.viewmodel.GlucoseStatus
import com.hathway.medbuddy.ThemeMode
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun StatusIndicator(
    status: GlucoseStatus
) {
    val statusInfo = when (status) {
        GlucoseStatus.Low -> Triple("🔴", stringResource(Res.string.low), StatusLow)
        GlucoseStatus.Normal -> Triple("🟢", stringResource(Res.string.normal), StatusInRange)
        GlucoseStatus.AboveTarget -> Triple("🟡", stringResource(Res.string.above_target), StatusHigh)
        GlucoseStatus.High -> Triple("🔴", stringResource(Res.string.high), StatusLow)
    }

    Column(horizontalAlignment = Alignment.End) {
        Text(text = statusInfo.first, fontSize = 32.sp)
        Text(
            text = statusInfo.second,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = statusInfo.third
        )
    }
}

@Composable
fun StatusChip(text: String) {
    val statusColor = when {
        text.contains(stringResource(Res.string.normal), ignoreCase = true) -> StatusInRange
        text.contains(stringResource(Res.string.status_in_range), ignoreCase = true) -> StatusInRange
        text.contains(stringResource(Res.string.low), ignoreCase = true) -> StatusLow
        text.contains(stringResource(Res.string.high), ignoreCase = true) -> StatusHigh
        text.contains(stringResource(Res.string.above_target), ignoreCase = true) -> StatusHigh
        else -> StatusInRange
    }
    Surface(
        shape = RoundedCornerShape(50), 
        color = statusColor.copy(alpha = 0.15f)
    ) {
        Text(
            text = text, 
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = statusColor,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun StatusIndicatorPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        StatusIndicator(status = GlucoseStatus.Normal)
    }
}

@Preview
@Composable
fun StatusChipPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        StatusChip(text = "Normal")
    }
}
