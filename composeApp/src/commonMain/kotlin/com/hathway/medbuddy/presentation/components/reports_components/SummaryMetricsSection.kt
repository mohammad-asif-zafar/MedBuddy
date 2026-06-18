package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryMetricsSection(
    avgGlucose: Int, hba1c: Double, timeInRange: Int, totalReadings: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7EE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Summary (30 Days)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryMiniCard(
                    Modifier.weight(1f),
                    "Average Glucose",
                    "$avgGlucose",
                    "mg/dL",
                    Color(0xFF1B5E20),
                    Icons.Default.WaterDrop
                )
                SummaryMiniCard(
                    Modifier.weight(1f),
                    "HbA1c",
                    "$hba1c%",
                    "Estimate",
                    Color(0xFFD32F2F),
                    Icons.Default.Bloodtype
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryMiniCard(
                    Modifier.weight(1f),
                    "Time In Range",
                    "$timeInRange%",
                    "",
                    Color(0xFF1B5E20),
                    Icons.Default.Adjust
                )
                SummaryMiniCard(
                    Modifier.weight(1f),
                    "Total Readings",
                    "$totalReadings",
                    "",
                    Color.Black,
                    Icons.Default.Assignment
                )
            }
        }
    }
}

@Composable
fun SummaryMiniCard(
    modifier: Modifier,
    title: String,
    value: String,
    subValue: String,
    valueColor: Color,
    icon: ImageVector
) {
    Surface(
        modifier = modifier.height(110.dp), shape = RoundedCornerShape(16.dp), color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = valueColor.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = valueColor)
            if (subValue.isNotEmpty()) {
                Text(subValue, fontSize = 11.sp, color = Color.Gray)
            }
            Text(title, fontSize = 11.sp, color = Color.Gray, maxLines = 1)
        }
    }
}
