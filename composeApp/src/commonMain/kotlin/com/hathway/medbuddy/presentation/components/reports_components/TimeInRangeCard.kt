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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.model.TimeInRangeData
import com.hathway.medbuddy.presentation.theme.*
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.*

@Composable
fun TimeInRangeCard(
    modifier: Modifier = Modifier, data: TimeInRangeData = TimeInRangeData()
) {

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
            Text(
                text = stringResource(Res.string.time_in_range_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
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

                        val sweepInRange = (data.inRangePct / 100f) * 360f
                        val sweepHigh = (data.highPct / 100f) * 360f
                        val sweepLow = (data.lowPct / 100f) * 360f

                        val topLeft = Offset(centerOffset.x - radius, centerOffset.y - radius)
                        val boundingSize = Size(radius * 2, radius * 2)

                        var currentStartAngle = -90f

                        // Draw Segment Slice 1: In Range (Green)
                        drawArc(
                            color = Primary,
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
                            color = Secondary,
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
                            color = Error,
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
                        color = Primary,
                        label = stringResource(Res.string.legend_in_range),
                        percentage = "${data.inRangePct.toInt()}%"
                    )
                    LegendRowItem(
                        color = Secondary,
                        label = stringResource(Res.string.legend_high),
                        percentage = "${data.highPct.toInt()}%"
                    )
                    LegendRowItem(
                        color = Error,
                        label = stringResource(Res.string.legend_low),
                        percentage = "${data.lowPct.toInt()}%"
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode - Custom Warm Cream")
@Composable
fun TimeInRangeCardCreamPreview() {
    TimeInRangeMockTheme(darkTheme = false) {
        TimeInRangeCard(
            data = TimeInRangeData(
                inRangePct = 75f, highPct = 15f, lowPct = 10f
            )
        )
    }
}

@Preview(name = "Dark Mode - High Contrast Check")
@Composable
fun TimeInRangeCardDarkPreview() {
    TimeInRangeMockTheme(darkTheme = true) {
        TimeInRangeCard(
            data = TimeInRangeData(
                inRangePct = 75f, highPct = 15f, lowPct = 10f
            )
        )
    }
}

@Composable
private fun TimeInRangeMockTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val systemColorScheme = if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            surface = PreviewSurfaceDark,
            surfaceVariant = PreviewSurfaceVariantDark,
            onSurface = PreviewSurfaceVariantCream,
            onSurfaceVariant = PreviewOnSurfaceDark
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            surface = LightBackground,
            surfaceVariant = PreviewSurfaceVariantCream,
            onSurface = Color(0xFF1A1A17),
            onSurfaceVariant = Color(0xFF42423E)
        )
    }

    MaterialTheme(colorScheme = systemColorScheme, content = content)
}
