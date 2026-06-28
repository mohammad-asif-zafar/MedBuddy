package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Fully closed and structurally fixed CounterSelector layout
@Composable
fun CounterSelector(
    label: String, value: Int, onValueChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label, style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Gray, fontWeight = FontWeight.Medium
            ), modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 6.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().height(56.dp)
                .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Decrement Left Action Box
            Box(
                modifier = Modifier.fillMaxHeight().width(56.dp).background(
                        color = Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                    ).clickable { onValueChange(value - 1) }, contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "-",
                    fontSize = 24.sp,
                    color = Color(0xFF2F66F6),
                    fontWeight = FontWeight.Medium
                )
            }

            // Central Measurement Metric Readout
            Text(
                text = value.toString(), style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 26.sp
                )
            )

            // Increment Right Action Box
            Box(
                modifier = Modifier.fillMaxHeight().width(56.dp).background(
                        color = Color(0xFFF1F5F9),
                        shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                    ).clickable { onValueChange(value + 1) }, contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    fontSize = 20.sp,
                    color = Color(0xFF2F66F6),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}@Preview(showBackground = true)
@Composable
fun CounterSelectorPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        CounterSelector(
            label = "Systolic",
            value = 120,
            onValueChange = {}
        )
    }
}
