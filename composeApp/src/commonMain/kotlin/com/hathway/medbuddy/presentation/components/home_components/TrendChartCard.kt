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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.viewmodel.RecentRecord
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.*

@Composable
fun TrendChartCard(
    readings: List<Float>, average: Double, highest: Double, lowest: Double,  recentRecords: List<RecentRecord>
) {

    Card(
        shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Glucose Trend (7 Days)", fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            GlucoseLineChart(
                readings = readings
            )
        }
    }
}

@Composable
private fun GlucoseLineChart2(
    readings: List<Float>
) {

    if (readings.isEmpty()) return

    val lineColor = MaterialTheme.colorScheme.primary

    Canvas(
        modifier = Modifier.fillMaxWidth().height(140.dp)
    ) {

        val maxValue = readings.maxOrNull() ?: 1f
        val minValue = readings.minOrNull() ?: 0f
        val range = (maxValue - minValue).takeIf { it > 0 } ?: 1f

        val stepX = size.width / (readings.size - 1)

        val path = Path()

        readings.forEachIndexed { index, value ->

            val x = index * stepX

            val y = size.height - ((value - minValue) / range) * size.height

            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path, color = lineColor, style = Stroke(
                width = 6f, cap = StrokeCap.Round
            )
        )

        readings.forEachIndexed { index, value ->

            val x = index * stepX

            val y = size.height - ((value - minValue) / range) * size.height

            drawCircle(
                color = lineColor, radius = 8f, center = Offset(x, y)
            )
        }
    }
}


@Composable
fun GlucoseLineChart(
    readings: List<Float>
) {

    if (readings.isEmpty()) return

    val labels = listOf(
        "9 Jun", "10 Jun", "11 Jun", "12 Jun", "13 Jun", "14 Jun", "15 Jun"
    )

    Column {

        Canvas(
            modifier = Modifier.fillMaxWidth().height(180.dp)
        ) {

            val maxY = 200f
            val minY = 0f

            fun mapY(value: Float): Float {
                return size.height - ((value - minY) / (maxY - minY)) * size.height
            }

            // Grid Lines
            listOf(0f, 50f, 100f, 150f, 200f).forEach { value ->

                val y = mapY(value)

                drawLine(
                    color = Color(0xFFE5E7EB),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Target Zone 70-100
            val zoneTop = mapY(100f)
            val zoneBottom = mapY(70f)

            drawRect(
                color = Color(0xFFDFF3DF), topLeft = Offset(
                    0f, zoneTop
                ), size = androidx.compose.ui.geometry.Size(
                    size.width, zoneBottom - zoneTop
                )
            )

            val stepX = size.width / (readings.size - 1)

            val path = Path()

            readings.forEachIndexed { index, value ->

                val x = index * stepX
                val y = mapY(value)

                if (index == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }

            drawPath(
                path = path, color = Color(0xFF2E7D32), style = Stroke(
                    width = 3.dp.toPx(), cap = StrokeCap.Round
                )
            )

            readings.forEachIndexed { index, value ->

                val x = index * stepX
                val y = mapY(value)

                drawCircle(
                    color = Color(0xFF2E7D32), radius = 5.dp.toPx(), center = Offset(x, y)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {

            labels.forEach {

                Text(
                    text = it, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}