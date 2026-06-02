package com.hathway.medbuddy.dashboard_home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HealthSummaryGrid(
    average: Int, hbA1c: Double, highest: Int, lowest: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard("Average", if (average > 0) average.toString() else "N/A", Modifier.weight(1f))
        SummaryCard(
            "HbA1c", if (hbA1c > 0) "${"%.1f".format(hbA1c)}%" else "N/A", Modifier.weight(1f)
        )
        SummaryCard("Highest", if (highest > 0) highest.toString() else "N/A", Modifier.weight(1f))
        SummaryCard("Lowest", if (lowest > 0) lowest.toString() else "N/A", Modifier.weight(1f))
    }
}