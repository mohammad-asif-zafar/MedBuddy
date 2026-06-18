package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
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
fun BestAndWorstDaysSection(
    bestDate: String, bestAvg: Int, worstDate: String, worstAvg: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7EE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Best & Worst Days",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Best Day Card
                DaySummaryItem(
                    modifier = Modifier.weight(1f),
                    title = "Best Day",
                    date = bestDate,
                    value = bestAvg,
                    containerColor = Color(0xFFE8F5E9),
                    accentColor = Color(0xFF2E7D32),
                    icon = Icons.Default.CheckCircle
                )
                // Worst Day Card
                DaySummaryItem(
                    modifier = Modifier.weight(1f),
                    title = "Worst Day",
                    date = worstDate,
                    value = worstAvg,
                    containerColor = Color(0xFFFFEBEE),
                    accentColor = Color(0xFFC62828),
                    icon = Icons.Default.Cancel
                )
            }
        }
    }
}

@Composable
fun DaySummaryItem(
    modifier: Modifier,
    title: String,
    date: String,
    value: Int,
    containerColor: Color,
    accentColor: Color,
    icon: ImageVector
) {
    Surface(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(28.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()
            ) {
                Column {
                    Text(
                        title, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = accentColor
                    )
                    Text(date, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("Average ", fontSize = 10.sp, color = Color.Gray)
                    Text(
                        "$value ",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text("mg/dL", fontSize = 10.sp, color = Color.Gray)
                }
            }
        }
    }
}
