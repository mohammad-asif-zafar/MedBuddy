@file:Suppress("DEPRECATION")

package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.data.local.FakeGlucoseRepository
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.usecase.RecentReading
import com.hathway.medbuddy.presentation.components.home_components.MedBuddyTopBar
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.viewmodel.FullHistoryViewModel
import com.hathway.medbuddy.presentation.viewmodel.HistorySummary
import com.hathway.medbuddy.util.getNowLocalDateTime
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun FullHistoryScreen(
    viewModel: FullHistoryViewModel,
    onBack: () -> Unit
) {
    val readings by viewModel.readings.collectAsState()
    val summary by viewModel.summary.collectAsState()

    val groupedReadings = remember(readings) {
        readings.groupBy { it.date }
    }

    val today = remember { getNowLocalDateTime().date }
    val yesterday = remember { today.minus(1, DateTimeUnit.DAY) }

    BackHandler(enabled = true) {
        onBack()
    }

    Scaffold(
        topBar = {
            MedBuddyTopBar(
                title = stringResource(Res.string.recent_readings),
                leftIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onLeftClick = onBack,
                titleColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background), // FIX: Dynamic canvas background
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            groupedReadings.forEach { (dateString, dateReadings) ->
                item(key = dateString) {
                    DateHeader(dateString, today, yesterday)
                }

                items(
                    items = dateReadings,
                    key = { "${it.date}_${it.timePeriod}_${it.value}_${it.time}" }
                ) { reading ->
                    HistoryReadingItem(reading)
                }
            }

            item(key = "summary_card") {
                HistorySummaryCard(summary)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DateHeader(dateString: String, today: LocalDate, yesterday: LocalDate) {
    val date = remember(dateString) {
        try { parseDisplayDate(dateString) } catch (e: Exception) { null }
    }

    val displayTitle = when (date) {
        today -> "Today, $dateString"
        yesterday -> "Yesterday, $dateString"
        else -> dateString
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary, // FIX: Dynamic brand tinting
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = displayTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary // FIX: Dynamic text contrast
        )
    }
}

@Composable
fun HistoryReadingItem(reading: RecentReading) {
    // FIX: Adaptive dynamic categorization styling for Morning, Afternoon, Evening & Night configurations
    val (icon, iconColor, bgColor) = remember(reading.category) {
        when (reading.category) {
            "Morning" -> Triple(Icons.Outlined.LightMode, Color(0xFFFFA726), Color(0xFFFF9800).copy(alpha = 0.12f))
            "Afternoon" -> Triple(Icons.Outlined.WbSunny, Color(0xFFFFB300), Color(0xFFFFC107).copy(alpha = 0.12f))
            "Evening" -> Triple(Icons.Outlined.WbSunny, Color(0xFF7E57C2), Color(0xFF673AB7).copy(alpha = 0.12f))
            else -> Triple(Icons.Outlined.Bedtime, Color(0xFF5C6BC0), Color(0xFF3F51B5).copy(alpha = 0.12f))
        }
    }

    // FIX: Dynamic alert metrics binding utilizing M3 token variables
    val statusColor = remember(reading.status) {
        when (reading.status) {
            "High" -> Color(0xFFE57373) // High Alert Red (Safe for Dark/Light surfaces)
            "Low" -> Color(0xFF64B5F6)  // Low Alert Blue
            else -> Color(0xFF81C784)  // Normal Target Green
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), // FIX: Dynamic token surface
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = bgColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = reading.abbreviatedPeriod,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface // FIX: Text contrast drops protection
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = bgColor,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = reading.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = iconColor
                        )
                    }
                }

                Text(
                    text = "${reading.time} • ${reading.mealTimingLabel}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant // FIX: Adaptive metadata grey text token
                )

                if (reading.notes.isNotEmpty()) {
                    Text(
                        text = reading.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        maxLines = 1,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${reading.value}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface // FIX: Clear visibility contrast matching
                )
                Text(
                    text = "mg/dL",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = statusColor.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = reading.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outlineVariant // FIX: Dynamic arrow indicator styling
            )
        }
    }
}

@Composable
fun HistorySummaryCard(summary: HistorySummary) {
    // ADAPTIVE SURFACE THEMING SYSTEM
    val containerBgColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
    val iconContainerColor = MaterialTheme.colorScheme.primary
    val iconTintColor = MaterialTheme.colorScheme.onPrimary

    val primaryTextColor = MaterialTheme.colorScheme.onSurface
    val secondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val accentHighlightColor = MaterialTheme.colorScheme.primary
    val dividerColor = MaterialTheme.colorScheme.outlineVariant

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerBgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Trend Icon Ring Frame Block
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = iconContainerColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.TrendingUp,
                        contentDescription = null,
                        tint = iconTintColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Average Value Text Information Node
            Column(modifier = Modifier.weight(1.2f)) {
                Text(
                    text = "Average (30 Days)",
                    style = MaterialTheme.typography.labelSmall,
                    color = secondaryTextColor
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${summary.averageValue}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = accentHighlightColor
                    )
                    Text(
                        text = " " + stringResource(Res.string.glucose_unit),
                        style = MaterialTheme.typography.labelSmall,
                        color = secondaryTextColor,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            // Central Boundary Separator Line
            VerticalDivider(
                modifier = Modifier.height(40.dp).padding(horizontal = 4.dp),
                color = dividerColor
            )

            // Proportional Health Aggregates Distribution Row
            Row(
                modifier = Modifier.weight(2f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Adaptive health status profiles mapping directly to scheme parameters
                SummaryStatItem(
                    count = summary.normalCount,
                    label = "Normal",
                    color = Color(0xFF0F9D58) // Status In-Range Green
                )
                SummaryStatItem(
                    count = summary.highCount,
                    label = "High",
                    color = MaterialTheme.colorScheme.error // Adaptive System High Red
                )
                SummaryStatItem(
                    count = summary.lowCount,
                    label = "Low",
                    color = Color(0xFFF4B400) // Status Caution Yellow
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = secondaryTextColor
            )
        }
    }
}

@Composable
fun SummaryStatItem(count: Int, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(8.dp).background(color, CircleShape)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "$count",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
fun FullHistoryScreenPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        FullHistoryScreen(
            viewModel = FullHistoryViewModel(FakeGlucoseRepository()), onBack = {})
    }
}

@Preview
@Composable
fun FullHistoryScreenPreviewDark() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        FullHistoryScreen(
            viewModel = FullHistoryViewModel(FakeGlucoseRepository()), onBack = {})
    }
}