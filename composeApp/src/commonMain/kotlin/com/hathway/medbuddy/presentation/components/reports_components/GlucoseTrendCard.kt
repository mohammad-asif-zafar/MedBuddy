package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.usecase.DailyAverageReading
import com.hathway.medbuddy.presentation.theme.MiniCardBackground
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrendChartCard(
    modifier: Modifier = Modifier,
    readings: List<DailyAverageReading>
) {
    // ✅ Dynamic Theme Colors
    val brandGreen = MaterialTheme.colorScheme.primary
    val gridLineColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)
    val cardBackground = MaterialTheme.colorScheme.surfaceVariant
    val primaryTextColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val innerDotRingColor = MaterialTheme.colorScheme.surface

    // Calculate real dynamic average straight from the passed list safely
    val calculatedAvg = remember(readings) {
        if (readings.isEmpty()) 0 else readings.map { it.averageValue }.average().toInt()
    }

    val textMeasurer = rememberTextMeasurer()
    val yAxisTextStyle = TextStyle(
        color = secondaryTextColor,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium
    )

    Card(
        modifier = modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MiniCardBackground
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
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
                        text = stringResource(Res.string.glucose_trend_title),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryTextColor
                    )
                    Text(
                        text = stringResource(Res.string.glucose_unit_mg_dl),
                        fontSize = 11.sp,
                        color = secondaryTextColor
                    )
                }

                // Average Floating Pill Badge
                if (readings.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = brandGreen.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = stringResource(Res.string.avg_glucose_pill_format, calculatedAvg),
                            color = brandGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // ✅ Core Visual Conditional Engine
            if (readings.isEmpty()) {
                // Renders the functional Empty State template design block
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShowChart,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(Res.string.trend_chart_empty_title),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(Res.string.trend_chart_empty_desc),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                }
            } else {
                // Graph Canvas Frame Rendering Layout View
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(start = 32.dp, top = 16.dp)
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

                        // 1. Plot Y-Axis Reference Readouts & Guidelines
                        val referenceValues = listOf(50f, 100f, 150f, 200f)
                        referenceValues.forEach { refVal ->
                            val yPos = getCanvasY(refVal)

                            val textLayoutResult = textMeasurer.measure(
                                text = refVal.toInt().toString(),
                                style = yAxisTextStyle
                            )

                            drawText(
                                textLayoutResult = textLayoutResult,
                                topLeft = Offset(
                                    x = -32.dp.toPx(),
                                    y = yPos - (textLayoutResult.size.height / 2f)
                                )
                            )

                            drawLine(
                                color = gridLineColor,
                                start = Offset(x = 0f, y = yPos),
                                end = Offset(x = width, y = yPos),
                                strokeWidth = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                        }

                        // 2. Plotting Trend Lines and Vectors
                        // Prevent division by zero if there is only 1 point
                        val distanceX = if (readings.size > 1) width / (readings.size - 1) else width
                        val linePath = Path()

                        readings.forEachIndexed { index, point ->
                            val currentX = index * distanceX
                            val currentY = getCanvasY(point.averageValue)

                            if (index == 0) {
                                linePath.moveTo(currentX, currentY)
                            } else {
                                linePath.lineTo(currentX, currentY)
                            }
                        }

                        drawPath(
                            path = linePath,
                            color = brandGreen,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )

                        // 3. Draw Endpoint Tracking Rings
                        readings.forEachIndexed { index, point ->
                            val currentX = index * distanceX
                            val currentY = getCanvasY(point.averageValue)

                            // Outer ring using theme color
                            drawCircle(
                                color = brandGreen,
                                radius = 5.dp.toPx(),
                                center = Offset(currentX, currentY)
                            )
                            // Inner core using surface background token
                            drawCircle(
                                color = innerDotRingColor,
                                radius = 2.5.dp.toPx(),
                                center = Offset(currentX, currentY)
                            )
                        }
                    }
                }
            }
        }
    }
}
