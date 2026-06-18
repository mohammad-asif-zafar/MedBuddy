package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer


@Composable
fun GlucoseTrendCard(
    modifier: Modifier = Modifier, avgGlucose: Int = 124, points: List<TrendPoint> = listOf(
        TrendPoint("May 16", 115),
        TrendPoint("", 100),
        TrendPoint("May 23", 145),
        TrendPoint("", 115),
        TrendPoint("May 30", 135),
        TrendPoint("", 90),
        TrendPoint("", 140),
        TrendPoint("Jun 15", 154)
    )
) {
    val brandCream = Color(0xFFF7F7EE)
    val brandGreen = Color(0xFF1B5E20)
    val gridLineColor = Color.LightGray.copy(alpha = 0.4f)

    // Core utility used to render text inside a Canvas block
    val textMeasurer = rememberTextMeasurer()
    val yAxisTextStyle = TextStyle(
        color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Medium
    )

    Card(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = brandCream)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Title Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Glucose Trend",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "mg/dL", fontSize = 11.sp, color = Color.Gray
                    )
                }

                // Average Floating Pill Badge
                Surface(
                    shape = RoundedCornerShape(50), color = Color(0xFFE8F5E9)
                ) {
                    Text(
                        text = "Avg $avgGlucose mg/dL",
                        color = brandGreen,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Graph Layout Frame (Adds explicit left padding to clear room for Y-Axis Text labels)
            Box(
                modifier = Modifier.fillMaxWidth().height(160.dp).padding(start = 32.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height

                    val minY = 50f
                    val maxY = 200f
                    val yRange = maxY - minY

                    fun getCanvasY(value: Float): Float {
                        val percentage = (value - minY) / yRange
                        return height - (percentage * height)
                    }

                    // 1. Draw Y-Axis Value Labels & Dashed Grid Guidelines
                    val referenceValues = listOf(50f, 100f, 150f, 200f)
                    referenceValues.forEach { refVal ->
                        val yPos = getCanvasY(refVal)

                        // Render the numbers text directly inside the canvas space tracker
                        val textLayoutResult = textMeasurer.measure(
                            text = refVal.toInt().toString(), style = yAxisTextStyle
                        )

                        // Draw numbers slightly offset to the left edge of the grid boundaries
                        drawText(
                            textLayoutResult = textLayoutResult, topLeft = Offset(
                                x = -32.dp.toPx(), y = yPos - (textLayoutResult.size.height / 2f)
                            )
                        )

                        // Draw Grid Lines
                        drawLine(
                            color = gridLineColor,
                            start = Offset(x = 0f, y = yPos),
                            end = Offset(x = width, y = yPos),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                    }

                    // 2. Plotting Trend Lines
                    if (points.isNotEmpty()) {
                        val distanceX = width / (points.size - 1)
                        val linePath = Path()

                        points.forEachIndexed { index, point ->
                            val currentX = index * distanceX
                            val currentY = getCanvasY(point.glucoseValue.toFloat())

                            if (index == 0) {
                                linePath.moveTo(currentX, currentY)
                            } else {
                                linePath.lineTo(currentX, currentY)
                            }
                        }

                        // Draw Stroke Pathway using verified parameters
                        drawPath(
                            path = linePath, color = brandGreen, style = Stroke(
                                width = 3.dp.toPx(),
                                cap = StrokeCap.Round // Parameter name is strictly 'cap'
                            )
                        )

                        // Draw Endpoint Circles
                        points.forEachIndexed { index, point ->
                            val currentX = index * distanceX
                            val currentY = getCanvasY(point.glucoseValue.toFloat())

                            drawCircle(
                                color = brandGreen,
                                radius = 4.dp.toPx(),
                                center = Offset(currentX, currentY)
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 2.dp.toPx(),
                                center = Offset(currentX, currentY)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 3. Horizontal X-Axis Text Labels Row
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 32.dp), // Offsets starting position to line up with graph track
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                points.forEach { point ->
                    if (point.dateLabel.isNotEmpty()) {
                        Text(
                            text = point.dateLabel,
                            fontSize = 11.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}


data class TrendPoint(
    val dateLabel: String, // e.g., "May 16"
    val glucoseValue: Int  // e.g., 120
)
