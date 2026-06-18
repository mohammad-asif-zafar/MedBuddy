package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. Data model representation for chart segment slices
data class TimeInRangeData(
    val inRangePct: Float = 78f, val highPct: Float = 15f, val lowPct: Float = 7f
)

@Composable
fun TimeInRangeCard(data: TimeInRangeData = TimeInRangeData()) {
    val brandCream = Color(0xFFF7F7EE)

    // Segment segment hex values corresponding directly to the graphic design
    val colorInRange = Color(0xFF1B5E20) // Deep forest green
    val colorHigh = Color(0xFFFFA000)    // Amber orange
    val colorLow = Color(0xFFD32F2F)     // Warning red

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = brandCream)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Time In Range",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Donut Chart Wheel Stacked Area Container Box
                Box(
                    modifier = Modifier.size(130.dp), contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 24.dp.toPx()
                        val canvasSize = size.minDimension
                        val radius = (canvasSize - strokeWidth) / 2
                        val centerOffset = Offset(size.width / 2, size.height / 2)

                        // Scale absolute percentages out of a complete 360 degree revolution ring
                        val sweepInRange = (data.inRangePct / 100f) * 360f
                        val sweepHigh = (data.highPct / 100f) * 360f
                        val sweepLow = (data.lowPct / 100f) * 360f

                        val topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius)
                        val boundingSize = Size(radius * 2, radius * 2)

                        // Base starting point alignment position vector pointing straight up (-90 degrees)
                        var currentStartAngle = -90f

                        // Draw Segment Slice 1: In Range (Green)
                        drawArc(
                            color = colorInRange,
                            startAngle = currentStartAngle,
                            sweepAngle = sweepInRange,
                            useCenter = false,
                            topLeft = topLeft,
                            size = boundingSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        currentStartAngle += sweepInRange

                        // Draw Segment Slice 2: High (Orange)
                        drawArc(
                            color = colorHigh,
                            startAngle = currentStartAngle,
                            sweepAngle = sweepHigh,
                            useCenter = false,
                            topLeft = topLeft,
                            size = boundingSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        currentStartAngle += sweepHigh

                        // Draw Segment Slice 3: Low (Red)
                        drawArc(
                            color = colorLow,
                            startAngle = currentStartAngle,
                            sweepAngle = sweepLow,
                            useCenter = false,
                            topLeft = topLeft,
                            size = boundingSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }

                    // Center text readout panel overlaying the inner donut hole blank section
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${data.inRangePct.toInt()}%",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                        Text(
                            text = "In Range",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                    }
                }

                // Vertical Layout Segment Side Legends Block Component Alignment
                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LegendRowItem(
                        color = colorInRange,
                        label = "In Range",
                        percentage = "${data.inRangePct.toInt()}%"
                    )
                    LegendRowItem(
                        color = colorHigh, label = "High", percentage = "${data.highPct.toInt()}%"
                    )
                    LegendRowItem(
                        color = colorLow, label = "Low", percentage = "${data.lowPct.toInt()}%"
                    )
                }
            }
        }
    }
}

@Composable
fun LegendRowItem(color: Color, label: String, percentage: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.width(110.dp)
    ) {
        // Circle color accent status indicators
        Box(
            modifier = Modifier.size(10.dp)
                .background(color = color, shape = RoundedCornerShape(50))
        )

        Text(
            text = label, fontSize = 13.sp, color = Color.DarkGray, modifier = Modifier.weight(1f)
        )

        Text(
            text = percentage,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.End
        )
    }
}
