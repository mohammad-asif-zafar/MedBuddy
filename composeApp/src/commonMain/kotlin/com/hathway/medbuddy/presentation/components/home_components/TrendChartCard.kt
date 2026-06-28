package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.usecase.DailyAverageReading
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.glucose_trend
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrendChartCard(
    readings: List<DailyAverageReading>, selectedFilterDays: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.glucose_trend, selectedFilterDays),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
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
    val axisTextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium
    )

    val chartLineColor = MaterialTheme.colorScheme.primary
    val gridLineColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    val circleInnerColor = MaterialTheme.colorScheme.surface

    // DYNAMIC FILTERING STEP: Compute label skip interval based on list size
    // 1-7 days: show all. 30 days: show roughly every 5th day. 90 days: show roughly every 15th day.
    val labelStep = when {
        readings.size <= 7 -> 1
        readings.size <= 31 -> 5
        else -> 15
    }

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
                        text = value.toInt().toString(), style = axisTextStyle
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

                // 2. Draw Target Boundary Alert Baseline
                val alertY = mapY(70f)
                drawLine(
                    color = chartLineColor.copy(alpha = 0.4f),
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

                    // 4. Fill Area Gradient Shading Layer
                    val fillPath = Path().apply {
                        addPath(strokePath)
                        lineTo(connectionPointsList.last().x, height)
                        lineTo(connectionPointsList.first().x, height)
                        close()
                    }

                    drawPath(
                        path = fillPath, brush = Brush.verticalGradient(
                            colors = listOf(
                                chartLineColor.copy(alpha = 0.15f),
                                chartLineColor.copy(alpha = 0.00f)
                            ), startY = mapY(200f), endY = height
                        )
                    )

                    // 5. Draw Core Top Line Curve
                    drawPath(
                        path = strokePath,
                        color = chartLineColor,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // 6. Anchor Dot Highlights (Hide them on high data counts to prevent visual clutter)
                    if (readings.size <= 15) {
                        connectionPointsList.forEach { point ->
                            drawCircle(color = chartLineColor, radius = 4.dp.toPx(), center = point)
                            drawCircle(color = circleInnerColor, radius = 2.dp.toPx(), center = point)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 7. FIXED X-AXIS: Uses a single Box layout to place filtered elements by exact X coordinate
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 12.dp)
        ) {
            readings.forEachIndexed { index, reading ->
                // Always show first, last, and items falling exactly on the calculated step interval
                if (index == 0 || index == readings.lastIndex || index % labelStep == 0) {

                    Layout(
                        content = {
                            Text(
                                text = reading.date,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1
                            )
                        }
                    ) { measurables, constraints ->
                        val placeable = measurables.first().measure(constraints)

                        // Calculate X placement based on total available width matching the canvas
                        layout(constraints.maxWidth, placeable.height) {
                            val xPosition = (index.toFloat() / (readings.size - 1)) * constraints.maxWidth

                            // Center the text bounding box right over its actual data point coordinate
                            val centeredX = (xPosition - (placeable.width / 2f))
                                .coerceIn(0f, (constraints.maxWidth - placeable.width).toFloat())

                            placeable.placeRelative(x = centeredX.toInt(), y = 0)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TrendChartCardPreview() {
    val mockReadings = listOf(
        DailyAverageReading("Mon", 110f),
        DailyAverageReading("Tue", 125f),
        DailyAverageReading("Wed", 115f),
        DailyAverageReading("Thu", 140f),
        DailyAverageReading("Fri", 130f),
        DailyAverageReading("Sat", 110f),
        DailyAverageReading("Sun", 120f)
    )
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.padding(16.dp)) {
            TrendChartCard(readings = mockReadings, selectedFilterDays = "7 Days")
        }
    }
}
