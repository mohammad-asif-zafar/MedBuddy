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
import com.hathway.medbuddy.domain.usecase.DailyAverageReading
import com.hathway.medbuddy.domain.usecase.RecentReading

@Composable
fun TrendChartCard(
    readings: List<DailyAverageReading>
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Glucose Trend (7 Days)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
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
fun GlucoseLineChart(
    readings: List<DailyAverageReading>
) {

    if (readings.isEmpty()) return

    Column {

        Canvas(
            modifier = Modifier.fillMaxWidth().height(180.dp)
        ) {

            val maxY = 200f
            val minY = 0f

            fun mapY(value: Float): Float {
                return size.height - ((value - minY) / (maxY - minY)) * size.height
            }

            // Grid lines
            listOf(
                0f, 50f, 100f, 150f, 200f
            ).forEach { value ->

                val y = mapY(value)

                drawLine(
                    color = Color(0xFFE5E7EB),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Target zone 70-100
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

            readings.forEachIndexed { index, reading ->

                val x = index * stepX
                val y = mapY(reading.averageValue)

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

            readings.forEachIndexed { index, reading ->

                val x = index * stepX
                val y = mapY(reading.averageValue)

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

            readings.forEach {

                Text(
                    text = it.date,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
