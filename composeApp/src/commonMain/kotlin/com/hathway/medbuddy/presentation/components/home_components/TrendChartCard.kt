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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.*

@Composable
fun TrendChartCard(
    readings: List<Float>, average: Double, highest: Double, lowest: Double
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = stringResource(Res.string.glucose_trend),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = stringResource(Res.string.last_7_days),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TrendMetric(
                    title = stringResource(Res.string.average_short),
                    value = average.toInt().toString()
                )

                TrendMetric(
                    title = stringResource(Res.string.high_short),
                    value = highest.toInt().toString()
                )

                TrendMetric(
                    title = stringResource(Res.string.low_short), value = lowest.toInt().toString()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            GlucoseLineChart(
                readings = readings
            )
        }
    }
}

@Composable
private fun GlucoseLineChart(
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