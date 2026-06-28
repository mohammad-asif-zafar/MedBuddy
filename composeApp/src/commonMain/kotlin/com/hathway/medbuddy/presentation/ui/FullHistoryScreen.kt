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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FullHistoryScreen(
    viewModel: FullHistoryViewModel, onBack: () -> Unit
) {
    val readings by viewModel.readings.collectAsState()
    val summary by viewModel.summary.collectAsState()

    // OPTIMIZATION: Memoize the grouping logic.
    val groupedReadings = remember(readings) {
        readings.groupBy { it.date }
    }

    val today = remember { getNowLocalDateTime().date }
    val yesterday = remember { today.minus(1, DateTimeUnit.DAY) }

    // Intercept hardware button or swipe back gesture
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
        }) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding)
                .background(Color(0xFFF8F9FA)), // Light gray background
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            groupedReadings.forEach { (dateString, dateReadings) ->
                item(key = dateString) {
                    DateHeader(dateString, today, yesterday)
                }

                // OPTIMIZATION: Use a stable unique key for better scrolling performance
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
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            tint = Color(0xFF00796B),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = displayTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00796B)
        )
    }
}

@Composable
fun HistoryReadingItem(reading: RecentReading) {
    val (icon, iconColor, bgColor) = remember(reading.category) {
        when (reading.category) {
            "Morning" -> Triple(Icons.Outlined.LightMode, Color(0xFFFF9800), Color(0xFFFFF3E0))
            "Afternoon" -> Triple(Icons.Outlined.WbSunny, Color(0xFFFFC107), Color(0xFFFFF8E1))
            "Evening" -> Triple(Icons.Outlined.WbSunny, Color(0xFF673AB7), Color(0xFFEDE7F6))
            else -> Triple(Icons.Outlined.Bedtime, Color(0xFF3F51B5), Color(0xFFE8EAF6))
        }
    }

    val statusColor = remember(reading.status) {
        when (reading.status) {
            "High" -> Color(0xFFEF5350)
            "Low" -> Color(0xFF42A5F5)
            else -> Color(0xFF66BB6A)
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            // Leading Icon
            Surface(
                modifier = Modifier.size(48.dp), shape = CircleShape, color = bgColor
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

            // Info Column
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = reading.abbreviatedPeriod,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = bgColor, shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = reading.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = iconColor.copy(alpha = 0.8f)
                        )
                    }
                }

                Text(
                    text = "${reading.time} • ${reading.mealTimingLabel}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                if (reading.notes.isNotEmpty()) {
                    Text(
                        text = reading.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.DarkGray,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Value Column
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${reading.value}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF263238)
                )
                Text(
                    text = "mg/dL", style = MaterialTheme.typography.labelSmall, color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = statusColor.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)
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
                tint = Color.LightGray
            )
        }
    }
}

@Composable
fun HistorySummaryCard(summary: HistorySummary) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp), shape = CircleShape, color = Color(0xFF009688)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.TrendingUp,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1.2f)) {
                Text(
                    text = "Average (30 Days)",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${summary.averageValue}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00796B)
                    )
                    Text(
                        text = " mg/dL",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            VerticalDivider(
                modifier = Modifier.height(40.dp).padding(horizontal = 8.dp),
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            Row(
                modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryStatItem(summary.normalCount, "Normal", Color(0xFF4CAF50))
                SummaryStatItem(summary.highCount, "High", Color(0xFFFF9800))
                SummaryStatItem(summary.lowCount, "Low", Color(0xFFF44336))
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
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
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$count",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
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
