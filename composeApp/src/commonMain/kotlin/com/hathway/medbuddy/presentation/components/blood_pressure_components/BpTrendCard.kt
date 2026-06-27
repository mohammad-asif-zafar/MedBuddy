package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.CardBorder
import com.hathway.medbuddy.presentation.theme.Info
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.ui_state.BpDualTrendPoint
import com.hathway.medbuddy.presentation.ui_state.BpTrendPoint

@Composable
fun BpTrendCardGraph(
    points: List<BpTrendPoint>, onViewHistoryClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                .border(1.dp, CardBorder, RoundedCornerShape(16.dp)).padding(20.dp)
        ) {
            Text(
                text = "BP Trend (7 Days)", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Custom Grid & Trend Line Plot Implementation
            Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val stepX = size.width / (points.size - 1)
                    val maxY = 160f
                    val minY = 40f
                    val heightRange = maxY - minY

                    // Draw Horizontal Dotted Gridlines
                    val gridLines = listOf(160f, 120f, 80f, 40f)
                    gridLines.forEach { value ->
                        val y = size.height - ((value - minY) / heightRange * size.height)
                        drawLine(
                            color = CardBorder.copy(alpha = 0.4f),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // Plot Trend Splines
                    for (i in 0 until points.size - 1) {
                        val startX = i * stepX
                        val startY =
                            size.height - ((points[i].value - minY) / heightRange * size.height)
                        val endX = (i + 1) * stepX
                        val endY =
                            size.height - ((points[i + 1].value - minY) / heightRange * size.height)

                        drawLine(
                            color = StatusInRange,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    // Node Points
                    points.forEachIndexed { i, point ->
                        val x = i * stepX
                        val y = size.height - ((point.value - minY) / heightRange * size.height)
                        drawCircle(Color.White, radius = 6.dp.toPx(), center = Offset(x, y))
                        drawCircle(StatusInRange, radius = 4.dp.toPx(), center = Offset(x, y))
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                points.forEach { point ->
                    Text(
                        point.label,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "View History", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, color = StatusInRange
                ), modifier = Modifier.clickable { onViewHistoryClick() })
        }
    }
}

@Composable
fun BpTrendCard(
    points: List<BpDualTrendPoint>,
    modifier: Modifier = Modifier,
    onViewHistoryClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = "BP Trend (30 Days)",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                LegendItem(label = "Systolic", color = Info)
                Spacer(modifier = Modifier.width(24.dp))
                LegendItem(label = "Diastolic", color = StatusInRange)
            }

            Row(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                Column(
                    modifier = Modifier.fillMaxHeight().padding(end = 12.dp, bottom = 4.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    val yLabelStyle = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text("140", style = yLabelStyle)
                    Text("120", style = yLabelStyle)
                    Text("100", style = yLabelStyle)
                    Text("80", style = yLabelStyle)
                    Text("60", style = yLabelStyle)
                }

                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val stepX = if (points.size > 1) size.width / (points.size - 1) else size.width
                        val maxY = 140f
                        val minY = 60f
                        val heightRange = maxY - minY

                        val gridLines = listOf(140f, 120f, 100f, 80f, 60f)
                        gridLines.forEach { value ->
                            val y = size.height - ((value - minY) / heightRange * size.height)
                            drawLine(
                                color = CardBorder.copy(alpha = 0.4f),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 1.dp.toPx()
                            )
                        }

                        for (i in 0 until points.size - 1) {
                            val startX = i * stepX
                            val nextX = (i + 1) * stepX

                            val sysStartY = size.height - ((points[i].systolic - minY) / heightRange * size.height)
                            val sysEndY = size.height - ((points[i + 1].systolic - minY) / heightRange * size.height)
                            drawLine(
                                color = Info,
                                start = Offset(startX, sysStartY),
                                end = Offset(nextX, sysEndY),
                                strokeWidth = 3.dp.toPx(),
                                cap = StrokeCap.Round
                            )

                            val diaStartY = size.height - ((points[i].diastolic - minY) / heightRange * size.height)
                            val diaEndY = size.height - ((points[i + 1].diastolic - minY) / heightRange * size.height)
                            drawLine(
                                color = StatusInRange,
                                start = Offset(startX, diaStartY),
                                end = Offset(nextX, diaEndY),
                                strokeWidth = 3.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }

                        points.forEachIndexed { i, point ->
                            val x = i * stepX
                            val sysY = size.height - ((point.systolic - minY) / heightRange * size.height)
                            drawCircle(Color.White, radius = 5.dp.toPx(), center = Offset(x, sysY))
                            drawCircle(Info, radius = 3.5.dp.toPx(), center = Offset(x, sysY))

                            val diaY = size.height - ((point.diastolic - minY) / heightRange * size.height)
                            drawCircle(Color.White, radius = 5.dp.toPx(), center = Offset(x, diaY))
                            drawCircle(StatusInRange, radius = 3.5.dp.toPx(), center = Offset(x, diaY))
                        }
                    }
                }
            }

            // Finished X-Axis generation loop
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 36.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                points.forEach { point ->
                    Text(
                        text = point.label,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = CardBorder.copy(alpha = 0.3f))

            // NEW: Integrated "View History" interactive element
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onViewHistoryClick() }
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "View History",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A73E8) // Visual matching accent blue text link
                    )
                )
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Navigate",
                    tint = Color(0xFF1A73E8)
                )
            }
        }
    }
}

/*
@Composable
fun BpTrendCard(
    points: List<BpDualTrendPoint>, modifier: Modifier = Modifier, onViewHistoryClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
            Text(
                text = "BP Trend", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
                )
            )

            // Custom Legend Configuration
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                LegendItem(label = "Systolic", color = Info)
                Spacer(modifier = Modifier.width(24.dp))
                LegendItem(label = "Diastolic", color = StatusInRange)
            }

            Row(modifier = Modifier.fillMaxWidth().height(220.dp)) {

                // Y-Axis Value Labels Grid Sidebar
                Column(
                    modifier = Modifier.fillMaxHeight().padding(end = 12.dp, bottom = 4.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    val yLabelStyle = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text("140", style = yLabelStyle)
                    Text("120", style = yLabelStyle)
                    Text("100", style = yLabelStyle)
                    Text("80", style = yLabelStyle)
                    Text("60", style = yLabelStyle)
                }

                // Graph Plot Core Area
                Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val stepX = size.width / (points.size - 1)
                        val maxY = 140f
                        val minY = 60f
                        val heightRange = maxY - minY

                        // Horizontal reference baseline axes
                        val gridLines = listOf(140f, 120f, 100f, 80f, 60f)
                        gridLines.forEach { value ->
                            val y = size.height - ((value - minY) / heightRange * size.height)
                            drawLine(
                                color = CardBorder.copy(alpha = 0.4f),
                                start = Offset(0f, y),
                                end = Offset(size.width, y),
                                strokeWidth = 1.dp.toPx()
                            )
                        }

                        // Plot Line Arrays
                        for (i in 0 until points.size - 1) {
                            val startX = i * stepX
                            val nextX = (i + 1) * stepX

                            // Systolic Line Segments
                            val sysStartY =
                                size.height - ((points[i].systolic - minY) / heightRange * size.height)
                            val sysEndY =
                                size.height - ((points[i + 1].systolic - minY) / heightRange * size.height)
                            drawLine(
                                color = Info,
                                start = Offset(startX, sysStartY),
                                end = Offset(nextX, sysEndY),
                                strokeWidth = 3.dp.toPx(),
                                cap = StrokeCap.Round
                            )

                            // Diastolic Line Segments
                            val diaStartY =
                                size.height - ((points[i].diastolic - minY) / heightRange * size.height)
                            val diaEndY =
                                size.height - ((points[i + 1].diastolic - minY) / heightRange * size.height)
                            drawLine(
                                color = StatusInRange,
                                start = Offset(startX, diaStartY),
                                end = Offset(nextX, diaEndY),
                                strokeWidth = 3.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                        }

                        // Render Nodes
                        points.forEachIndexed { i, point ->
                            val x = i * stepX

                            val sysY =
                                size.height - ((point.systolic - minY) / heightRange * size.height)
                            drawCircle(Color.White, radius = 5.dp.toPx(), center = Offset(x, sysY))
                            drawCircle(Info, radius = 3.5.dp.toPx(), center = Offset(x, sysY))

                            val diaY =
                                size.height - ((point.diastolic - minY) / heightRange * size.height)
                            drawCircle(Color.White, radius = 5.dp.toPx(), center = Offset(x, diaY))
                            drawCircle(
                                StatusInRange, radius = 3.5.dp.toPx(), center = Offset(x, diaY)
                            )
                        }
                    }
                }
            }

            // X-Axis Text Target Timestamps Labels row
            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    top = 8.dp, start = 32.dp
                ), // Start padding aligns labels with the chart area
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                points.forEach { point ->
                    if (point.label.isNotEmpty()) {
                        Text(
                            text = point.label, style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp
                            )
                        )
                    } else {
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    }
}
*/

@Composable
private fun LegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label, style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium
            )
        )
    }
}


// Mock data matrix matching the provided image graph dataset
private val MockTrendPoints = listOf(
    BpDualTrendPoint("Apr 21", 115, 82),
    BpDualTrendPoint("", 116, 85),
    BpDualTrendPoint("Apr 28", 121, 87),
    BpDualTrendPoint("", 114, 81),
    BpDualTrendPoint("", 110, 80),
    BpDualTrendPoint("May 5", 117, 84),
    BpDualTrendPoint("", 121, 85),
    BpDualTrendPoint("", 114, 80),
    BpDualTrendPoint("May 12", 119, 82),
    BpDualTrendPoint("", 113, 81),
    BpDualTrendPoint("May 19", 123, 87)
)

@Preview(name = "Trend Card Light Mode", showBackground = true)
@Composable
fun BpTrendCardLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Surface(modifier = Modifier.padding(16.dp)) {
            BpTrendCard(
                points = MockTrendPoints, modifier = Modifier, onViewHistoryClick = {})
        }
    }
}

@Preview(name = "Trend Card Dark Mode", showBackground = true)
@Composable
fun BpTrendCardDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Surface(modifier = Modifier.padding(16.dp)) {
            BpTrendCard(
                points = MockTrendPoints, modifier = Modifier, onViewHistoryClick = { })
        }
    }
}

