package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.domain.usecase.RecentReading
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode

import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecentRecordItem(record: RecentReading) {
    // Standardize your clinical color alert statuses matching your custom scheme boundaries
    val statusColor = when {
        record.value < 70 -> Color(0xFFDB4437)      // Status Low Red
        record.value > 140 -> Color(0xFFE94235)     // Status High Dark Orange/Red
        else -> Color(0xFF0F9D58)                   // Status In-Range Healthy Green
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = record.value.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(Res.string.glucose_unit),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "${record.timePeriod} • ${record.time}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = when {
                record.value < 70 -> stringResource(Res.string.low)
                record.value > 140 -> stringResource(Res.string.high)
                else -> stringResource(Res.string.normal)
            },
            color = statusColor,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun RecentRecordItemPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        RecentRecordItem(
            record = RecentReading(
                date = "24 Oct 2024",
                timePeriod = "Before Breakfast",
                value = 95,
                time = "08:30 AM",
                status = "Normal"
            )
        )
    }
}
