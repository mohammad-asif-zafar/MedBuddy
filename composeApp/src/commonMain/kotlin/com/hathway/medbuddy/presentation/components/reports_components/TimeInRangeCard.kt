package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.TimeInRangeData
import com.hathway.medbuddy.presentation.theme.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimeInRangeCard(
    modifier: Modifier = Modifier, data: TimeInRangeData = TimeInRangeData()
) {
    val statusInRangeColor = StatusInRange
    val statusHighColor = StatusHigh
    val statusLowColor = StatusLow

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.time_in_range_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Donut Chart Wheel
                Box(
                    modifier = Modifier.size(130.dp), contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeWidth = 24.dp.toPx()
                        val canvasSize = size.minDimension
                        val radius = (canvasSize - strokeWidth) / 2
                        val centerOffset = Offset(size.width / 2, size.height / 2)

                        val sweepInRange = (data.inRangePct / 100f) * 360f
                        val sweepHigh = (data.highPct / 100f) * 360f
                        val sweepLow = (data.lowPct / 100f) * 360f

                        val topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius)
                        val boundingSize = Size(radius * 2, radius * 2)

                        var currentStartAngle = -90f

                        // Slice 1: In Range
                        drawArc(
                            color = statusInRangeColor,
                            startAngle = currentStartAngle,
                            sweepAngle = sweepInRange,
                            useCenter = false,
                            topLeft = topLeft,
                            size = boundingSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        currentStartAngle += sweepInRange

                        // Slice 2: High
                        drawArc(
                            color = statusHighColor,
                            startAngle = currentStartAngle,
                            sweepAngle = sweepHigh,
                            useCenter = false,
                            topLeft = topLeft,
                            size = boundingSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                        currentStartAngle += sweepHigh

                        // Slice 3: Low
                        drawArc(
                            color = statusLowColor,
                            startAngle = currentStartAngle,
                            sweepAngle = sweepLow,
                            useCenter = false,
                            topLeft = topLeft,
                            size = boundingSize,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${data.inRangePct.toInt()}%",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stringResource(Res.string.legend_in_range),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(end = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    LegendRowItem(
                        color = statusInRangeColor,
                        label = stringResource(Res.string.legend_in_range),
                        percentage = "${data.inRangePct.toInt()}%"
                    )
                    LegendRowItem(
                        color = statusHighColor,
                        label = stringResource(Res.string.legend_high),
                        percentage = "${data.highPct.toInt()}%"
                    )
                    LegendRowItem(
                        color = statusLowColor,
                        label = stringResource(Res.string.legend_low),
                        percentage = "${data.lowPct.toInt()}%"
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TimeInRangeCardPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        TimeInRangeCard(
            data = TimeInRangeData(
                inRangePct = 70f,
                highPct = 20f,
                lowPct = 10f
            )
        )
    }
}
