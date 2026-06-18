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
import com.hathway.medbuddy.domain.usecase.RecentReading
import com.hathway.medbuddy.presentation.viewmodel.RecentRecord

import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecentRecordItem(
    record: RecentReading
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
                text = "${record.value} ${stringResource(Res.string.glucose_unit)}",
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
                record.value < 70 -> stringResource(Res.string.low)
                record.value > 180 -> stringResource(Res.string.high)
                else -> stringResource(Res.string.normal)
            }, color = statusColor, fontWeight = FontWeight.SemiBold
        )
    }
}
