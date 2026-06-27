package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.CalendarDayStatus
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.presentation.components.calendar_components.CalendarSummaryCard
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.next_symbol
import medbuddy.composeapp.generated.resources.prev_symbol
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenCalendarSheet(
    selectedDate: LocalDate,
    records: List<GlucoseRecord>,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var currentMonth by remember { mutableStateOf(selectedDate) }

    val dayStatuses = remember(records, currentMonth) {
        records.groupBy {
            parseDisplayDate(it.date)
        }.mapValues { (_, dayRecords) ->
            var low = 0
            var inRange = 0
            var high = 0
            dayRecords.forEach { record ->
                listOfNotNull(
                    record.beforeBreakfast,
                    record.afterBreakfast,
                    record.beforeLunch,
                    record.afterLunch,
                    record.beforeDinner,
                    record.afterDinner,
                    record.bedtime
                ).forEach { value ->
                    when {
                        value < 70 -> low++
                        value <= 140 -> inRange++
                        else -> high++
                    }
                }
            }
            CalendarDayStatus(
                date = parseDisplayDate(dayRecords.first().date),
                lowCount = low,
                inRangeCount = inRange,
                highCount = high
            )
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        ) {
            // Month navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(40.dp).clickable {
                        currentMonth = currentMonth.minus(1, DateTimeUnit.MONTH)
                    }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.prev_symbol),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "${
                        currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }
                    } ${currentMonth.year}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface)

                Box(
                    modifier = Modifier.size(40.dp).clickable {
                        currentMonth = currentMonth.plus(1, DateTimeUnit.MONTH)
                    }, contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.next_symbol),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Calendar grid
            CalendarGrid(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                dayStatuses = dayStatuses,
                onDateSelected = { date ->
                    onDateSelected(date)
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) onDismiss()
                    }
                })

            Spacer(modifier = Modifier.height(6.dp))

            // Calendar legend
            CalendarLegend()

            Spacer(modifier = Modifier.height(6.dp))

            CalendarSummaryCard(
                selectedDate = selectedDate, records = records
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// MOCK DATA

// 1. Mock Data Source Setup
private fun createMockRecords(): List<GlucoseRecord> {
    return listOf(
        GlucoseRecord(
            date = "2026-06-27",
            beforeBreakfast = 95,
            afterBreakfast = 130,
            beforeLunch = 65,
            afterLunch = 155,
            beforeDinner = null,
            afterDinner = null,
            bedtime = null
        ),
        GlucoseRecord(
            date = "2026-06-26",
            beforeBreakfast = 110,
            afterBreakfast = 145,
            beforeLunch = 90,
            afterLunch = 120,
            beforeDinner = 105,
            afterDinner = 150,
            bedtime = 115
        ),
        GlucoseRecord(
            date = "2026-06-25",
            beforeBreakfast = 60,
            afterBreakfast = 110,
            beforeLunch = 75,
            afterLunch = 135,
            beforeDinner = 85,
            afterDinner = 125,
            bedtime = 95
        ),
        GlucoseRecord(
            date = "2026-06-24",
            beforeBreakfast = 150,
            afterBreakfast = 210,
            beforeLunch = 140,
            afterLunch = 195,
            beforeDinner = 160,
            afterDinner = 220,
            bedtime = 180
        ),
        GlucoseRecord(
            date = "2026-06-20",
            beforeBreakfast = 95,
            afterBreakfast = 125,
            beforeLunch = 100,
            afterLunch = 140,
            beforeDinner = 110,
            afterDinner = 135,
            bedtime = 120
        ),
        GlucoseRecord(
            date = "2026-06-15",
            beforeBreakfast = 180,
            afterBreakfast = 250,
            beforeLunch = 165,
            afterLunch = 220,
            beforeDinner = 190,
            afterDinner = 240,
            bedtime = 210
        )
    )
}

// 2. Light Theme Bottom Sheet Workspace Preview
@Preview
@Composable
fun FullScreenCalendarSheetLightPreview() {
    val previewDate = LocalDate(2026, 6, 27)
    val mockRecords = createMockRecords()

    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        // Parent container establishes sheet view anchor points
        Box(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f))
        ) {
            FullScreenCalendarSheet(
                selectedDate = previewDate,
                records = mockRecords,
                onDateSelected = {},
                onDismiss = {})
        }
    }
}

// 3. Dark Theme Bottom Sheet Workspace Preview
@Preview
@Composable
fun FullScreenCalendarSheetDarkPreview() {
    val previewDate = LocalDate(2026, 6, 27)
    val mockRecords = createMockRecords()

    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
        ) {
            FullScreenCalendarSheet(
                selectedDate = previewDate,
                records = mockRecords,
                onDateSelected = {},
                onDismiss = {})
        }
    }
}
