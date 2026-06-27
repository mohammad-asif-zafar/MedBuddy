package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.CardBorder
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.Info
import com.hathway.medbuddy.presentation.theme.StatusLow

@Composable
fun BpOverviewCard(
    avgSys: Int,
    avgDia: Int,
    minSys: Int,
    minDia: Int,
    maxSys: Int,
    maxDia: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "BP Overview (30 Days)", style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
            ), modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                .border(1.dp, CardBorder, RoundedCornerShape(16.dp)).padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OverviewMetricColumn(
                label = "Average", value = "$avgSys/$avgDia", color = StatusInRange
            )
            OverviewMetricColumn(label = "Lowest", value = "$minSys/$minDia", color = Info)
            OverviewMetricColumn(label = "Highest", value = "$maxSys/$maxDia", color = StatusLow)
        }
    }
}

@Composable
private fun OverviewMetricColumn(
    label: String, value: String, color: androidx.compose.ui.graphics.Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label, style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value, style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold, color = color, fontSize = 22.sp
            )
        )
    }
}
