package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.usecase.DailyAverageReading

@Composable
fun TrendChartCard(
    readings: List<DailyAverageReading>
) {
    val brandCream = Color(0xFFFFFFFF)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = brandCream),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Glucose Trend (7 Days)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            GlucoseLineChart(readings = readings)
        }
    }
}

@Composable
fun GlucoseLineChart(readings: List<DailyAverageReading>) {
    if (readings.isEmpty()) return

    val textMeasurer = rememberTextMeasurer()
    val yAxisTextStyle = TextStyle(
        color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Medium
    )

    val brandGreen = Color(0xFF1B5E20)
    val gridLineColor = Color.LightGray.copy(alpha = 0.2f)

    Column {
        Box(
            modifier = Modifier.fillMaxWidth().height(180.dp).padding(start = 32.dp, end = 12.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                val maxY = 200f
                val minY = 0f

                fun mapY(value: Float): Float = height - ((value - minY) / (maxY - minY)) * height

                // 1. Draw Clean Subtle Horizontal Background Gridlines & Text Labels
                val referenceValues = listOf(0f, 50f, 100f, 150f, 200f)
                referenceValues.forEach { value ->
                    val y = mapY(value)

                    val textLayoutResult = textMeasurer.measure(
                        text = value.toInt().toString(), style = yAxisTextStyle
                    )
                    drawText(
                        textLayoutResult = textLayoutResult, topLeft = Offset(
                            x = -32.dp.toPx(), y = y - (textLayoutResult.size.height / 2f)
                        )
                    )

                    drawLine(
                        color = gridLineColor,
                        start = Offset(0f, y),
                        end = Offset(width, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // 2. Draw Target Boundary Alert Baseline (Dashed Line at 70 mg/dL as shown in reference image)
                val alertY = mapY(70f)
                drawLine(
                    color = brandGreen.copy(alpha = 0.4f),
                    start = Offset(0f, alertY),
                    end = Offset(width, alertY),
                    strokeWidth = 1.5.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f)
                )

                // 3. Compute Coordinates & Smooth Cubic Bezier Curves Dynamically
                val stepX = width / if (readings.size > 1) (readings.size - 1) else 1

                val connectionPointsList = readings.mapIndexed { index, reading ->
                    Offset(index * stepX, mapY(reading.averageValue))
                }

                if (connectionPointsList.size >= 2) {
                    val strokePath = Path().apply {
                        moveTo(connectionPointsList.first().x, connectionPointsList.first().y)

                        for (i in 0 until connectionPointsList.size - 1) {
                            val p0 = connectionPointsList[i]
                            val p1 = connectionPointsList[i + 1]

                            // Cubic control vectors create elegant organic curve lines between values
                            val controlPointX1 = p0.x + (p1.x - p0.x) / 2f
                            val controlPointY1 = p0.y
                            val controlPointX2 = p0.x + (p1.x - p0.x) / 2f
                            val controlPointY2 = p1.y

                            cubicTo(
                                controlPointX1,
                                controlPointY1,
                                controlPointX2,
                                controlPointY2,
                                p1.x,
                                p1.y
                            )
                        }
                    }

                    // 4. Fill Area Gradient Shading Layer Beneath the Smooth Path Track
                    val fillPath = Path().apply {
                        addPath(strokePath)
                        // Close loop bounding down to the canvas ground baseline layout line safely
                        lineTo(connectionPointsList.last().x, height)
                        lineTo(connectionPointsList.first().x, height)
                        close()
                    }

                    drawPath(
                        path = fillPath, brush = Brush.verticalGradient(
                            colors = listOf(
                                brandGreen.copy(alpha = 0.15f), // Soft transparency blend down
                                brandGreen.copy(alpha = 0.00f)
                            ), startY = mapY(200f), endY = height
                        )
                    )

                    // 5. Draw Core Top Line Curve
                    drawPath(
                        path = strokePath,
                        color = brandGreen,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // 6. Anchor Dot Highlights Overlay matching exactly the layout junctions
                    connectionPointsList.forEach { point ->
                        drawCircle(
                            color = brandGreen, radius = 5.dp.toPx(), center = point
                        )
                        drawCircle(
                            color = Color.White, radius = 2.5.dp.toPx(), center = point
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 7. Horizontal X-Axis Text Date Label Row Section Tracking
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 32.dp, end = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            readings.forEach { reading ->
                Text(
                    text = reading.date,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

