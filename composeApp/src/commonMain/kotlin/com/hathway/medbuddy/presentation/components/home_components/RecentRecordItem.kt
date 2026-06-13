package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.hathway.medbuddy.presentation.viewmodel.RecentRecord

@Composable
fun RecentRecordItem(
    record: RecentRecord
) {

    val statusColor = when {
        record.value < 70 -> MaterialTheme.colorScheme.error
        record.value > 180 -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.primary
    }

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {

            Text(
                text = "${record.value} mg/dL",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = statusColor
            )

            Text(
                text = "${record.timePeriod} • ${record.time}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = when {
                record.value < 70 -> "Low"
                record.value > 180 -> "High"
                else -> "Normal"
            }, color = statusColor, fontWeight = FontWeight.SemiBold
        )
    }
}