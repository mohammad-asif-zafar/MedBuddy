package com.hathway.medbuddy.presentation.components.calendar_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.theme.StatusHigh
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.StatusLow
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.LocalDate
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.average
import medbuddy.composeapp.generated.resources.glucose_unit
import medbuddy.composeapp.generated.resources.high
import medbuddy.composeapp.generated.resources.low
import medbuddy.composeapp.generated.resources.status_in_range
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarSummaryCard(
    selectedDate: LocalDate, records: List<GlucoseRecord>
) {
    val dayRecords = records.filter { parseDisplayDate(it.date) == selectedDate }
    val readings = dayRecords.flatMap {
        listOfNotNull(
            it.beforeBreakfast,
            it.afterBreakfast,
            it.beforeLunch,
            it.afterLunch,
            it.beforeDinner,
            it.afterDinner,
            it.bedtime
        )
    }

    val lowCount = readings.count { it < 70 }
    val inRangeCount = readings.count { it in 70..140 }
    val highCount = readings.count { it > 140 }

    val average = if (readings.isNotEmpty()) readings.average().toInt() else 0

    val (statusText, statusColor) = when {
        average == 0 -> "" to Color.Transparent
        average < 70 -> stringResource(Res.string.low) to StatusLow
        average <= 140 -> stringResource(Res.string.status_in_range) to StatusInRange
        else -> stringResource(Res.string.high) to StatusHigh
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = 0.5f
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${selectedDate.dayOfMonth} ${
                selectedDate.month.name.lowercase().replaceFirstChar { it.uppercase() }
            } ${selectedDate.year} Summary",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    value = readings.size.toString(),
                    label = "Readings",
                    backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    textColor = MaterialTheme.colorScheme.primary
                )

                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    value = lowCount.toString(),
                    label = stringResource(Res.string.low),
                    backgroundColor = StatusLow.copy(alpha = 0.1f),
                    textColor = StatusLow
                )

                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    value = inRangeCount.toString(),
                    label = stringResource(Res.string.status_in_range),
                    backgroundColor = StatusInRange.copy(alpha = 0.1f),
                    textColor = StatusInRange
                )

                SummaryStatCard(
                    modifier = Modifier.weight(1f),
                    value = highCount.toString(),
                    label = stringResource(Res.string.high),
                    backgroundColor = StatusHigh.copy(alpha = 0.1f),
                    textColor = StatusHigh
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.average),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = average.toString(),
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = " " + stringResource(Res.string.glucose_unit),
                        modifier = Modifier.padding(bottom = 6.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (statusText.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(50), color = statusColor.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = statusText,
                            color = statusColor,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

// MOCK DATA
private fun createPreviewRecords(): List<GlucoseRecord> {
    return listOf(
        GlucoseRecord(
            date = "2026-06-27", // Matches selectedDate for active mapping verification
            beforeBreakfast = 95,  // In Range
            afterBreakfast = 135,  // In Range
            beforeLunch = 64,      // Low
            afterLunch = 162,      // High
            beforeDinner = null, afterDinner = null, bedtime = null
        )
    )
}


// 1. Light Theme Workspace Configuration
@Preview
@Composable
fun CalendarSummaryCardLightPreview() {
    val previewDate = LocalDate(2026, 6, 27)
    val mockRecords = createPreviewRecords()

    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CalendarSummaryCard(
                selectedDate = previewDate, records = mockRecords
            )
        }
    }
}

// 2. Dark Theme Workspace Configuration
@Preview
@Composable
fun CalendarSummaryCardDarkPreview() {
    val previewDate = LocalDate(2026, 6, 27)
    val mockRecords = createPreviewRecords()

    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Box(
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CalendarSummaryCard(
                selectedDate = previewDate, records = mockRecords
            )
        }
    }
}

