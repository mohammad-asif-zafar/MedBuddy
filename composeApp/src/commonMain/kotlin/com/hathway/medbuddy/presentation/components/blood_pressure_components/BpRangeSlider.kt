package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.theme.StatusHigh
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.StatusLow

@Composable
fun BpRangeSlider(modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = "BP Range", style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
                ), modifier = Modifier.padding(bottom = 12.dp)
            )
            Box(
                modifier = Modifier.fillMaxWidth().height(36.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Canvas(modifier = Modifier.fillMaxWidth().height(10.dp)) {
                    val segmentWidth = size.width / 4f
                    val spacing = 4.dp.toPx()

                    // Color Segments representing standard BP ranges
                    drawRoundRect(
                        Color(0xFF81C784),
                        Offset(0f, 0f),
                        Size(segmentWidth - spacing, size.height),
                        CornerRadius(10f, 10f)
                    )
                    drawRoundRect(
                        StatusInRange,
                        Offset(segmentWidth, 0f),
                        Size(segmentWidth - spacing, size.height),
                        CornerRadius(10f, 10f)
                    )
                    drawRoundRect(
                        StatusHigh,
                        Offset(segmentWidth * 2, 0f),
                        Size(segmentWidth - spacing, size.height),
                        CornerRadius(10f, 10f)
                    )
                    drawRoundRect(
                        StatusLow,
                        Offset(segmentWidth * 3, 0f),
                        Size(segmentWidth, size.height),
                        CornerRadius(10f, 10f)
                    )
                }

                // Marker Pointer Indicator Positioned over current target index segment
                Box(
                    modifier = Modifier.padding(start = 110.dp) // Dynamic offset bound calculation mapping
                        .size(18.dp)
                        .background(Color.White, androidx.compose.foundation.shape.CircleShape)
                        .background(StatusInRange, androidx.compose.foundation.shape.CircleShape)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val labelStyle =
                    MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("90/60", style = labelStyle)
                Text("120/80", style = labelStyle, fontWeight = FontWeight.Bold)
                Text("140/90", style = labelStyle)
                Text("180/110", style = labelStyle)
            }
        }
    }
}



@Preview(name = "Range Slider Light Mode", showBackground = true)
@Composable
fun BpRangeSliderLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Surface(modifier = Modifier.padding(16.dp)) {
            BpRangeSlider()
        }
    }
}

@Preview(name = "Range Slider Dark Mode", showBackground = true)
@Composable
fun BpRangeSliderDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Surface(modifier = Modifier.padding(16.dp)) {
            BpRangeSlider()
        }
    }
}
