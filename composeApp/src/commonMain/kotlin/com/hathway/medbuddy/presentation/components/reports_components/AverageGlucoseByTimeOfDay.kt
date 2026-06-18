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
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.outlined.SoupKitchen
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AverageGlucoseByTimeOfDay(
    beforeBreakfast: Int,
    afterBreakfast: Int,
    beforeLunch: Int,
    afterLunch: Int,
    beforeDinner: Int,
    afterDinner: Int,
    bedtime: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7EE))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Average Glucose by Time of Day",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimeOfDayItem(
                    Modifier.weight(1f),
                    "BBF",
                    beforeBreakfast,
                    Icons.Default.WbSunny,
                    Color(0xFFFFA000)
                )
                TimeOfDayItem(
                    Modifier.weight(1f),
                    "ABF",
                    afterBreakfast,
                    Icons.Default.WbTwilight,
                    Color(0xFFE64A19)
                )
                TimeOfDayItem(
                    Modifier.weight(1f),
                    "BF",
                    beforeLunch,
                    Icons.Default.LightMode,
                    Color(0xFF388E3C)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimeOfDayItem(
                    Modifier.weight(1f),
                    "AL",
                    afterLunch,
                    Icons.Outlined.SoupKitchen,
                    Color(0xFFD32F2F)
                ) // Custom icon or placeholder
                TimeOfDayItem(
                    Modifier.weight(1f),
                    "BD",
                    beforeDinner,
                    Icons.Default.WbTwilight,
                    Color(0xFF7B1FA2)
                )
                TimeOfDayItem(
                    Modifier.weight(1f), "BT", bedtime, Icons.Default.NightsStay, Color(0xFF303F9F)
                )
            }
        }
    }
}

@Composable
fun TimeOfDayItem(
    modifier: Modifier, label: String, value: Int, icon: ImageVector, iconColor: Color
) {
    val isHigh = value > 140 // Simplified status warning threshold logic sample
    Surface(
        modifier = modifier.height(115.dp), shape = RoundedCornerShape(12.dp), color = Color.White
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            Text(
                label,
                fontSize = 10.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "$value",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isHigh) Color(0xFFD32F2F) else Color(0xFF1B5E20)
                )
                Text("mg/dL", fontSize = 9.sp, color = Color.Gray)
            }
        }
    }
}
