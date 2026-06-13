package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HealthSummaryGrid(
    average: Int, hbA1c: Double, highest: Int, lowest: Int
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SummaryCard(
                title = "Average",
                value = if (average > 0) average.toString() else "N/A",
                modifier = Modifier.weight(1f)
            )

            SummaryCard(
                title = "HbA1c", value = if (hbA1c > 0) "${"%.1f".format(hbA1c)}%"
                else "N/A", modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SummaryCard(
                title = "Highest",
                value = if (highest > 0) highest.toString() else "N/A",
                modifier = Modifier.weight(1f)
            )

            SummaryCard(
                title = "Lowest",
                value = if (lowest > 0) lowest.toString() else "N/A",
                modifier = Modifier.weight(1f)
            )
        }
    }
}