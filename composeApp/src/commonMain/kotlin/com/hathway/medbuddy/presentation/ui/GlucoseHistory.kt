package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun GlucoseRecordHistory(
    records: List<GlucoseRecord>, viewModel: AddViewModel
) {
    var selectedDate by remember {
        mutableStateOf(getNowLocalDateTime().date)
    }
    var showCalendar by remember { mutableStateOf(false) }

    val selectedRecord = records.firstOrNull {
        parseDisplayDate(it.date) == selectedDate
    }

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        // GlucoseRecordHistory(records = uiState.records)

        // Floating Action Button
        ExtendedFloatingActionButton(onClick = {
            viewModel.onShowDialog()
        }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp), icon = {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = stringResource(
                    Res.string.add_glucose_reading
                )
            )
        }, text = {
            Text(
                stringResource(
                    Res.string.add_reading
                )
            )
        })
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Date Navigation Header
        DateNavigationHeader(selectedDate = selectedDate, onPreviousDay = {
            selectedDate = selectedDate.minus(1, DateTimeUnit.DAY)
        }, onNextDay = {
            selectedDate = selectedDate.plus(1, DateTimeUnit.DAY)
        }, onDateClick = {
            showCalendar = true
        })

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        if (selectedRecord != null) {
            val beforeBreakfastLabel = stringResource(Res.string.before_breakfast)
            val afterBreakfastLabel = stringResource(Res.string.after_breakfast)
            val beforeLunchLabel = stringResource(Res.string.before_lunch)
            val afterLunchLabel = stringResource(Res.string.after_lunch)
            val beforeDinnerLabel = stringResource(Res.string.before_dinner)
            val afterDinnerLabel = stringResource(Res.string.after_dinner)
            val bedtimeLabel = stringResource(Res.string.bedtime)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                glucoseItem(beforeBreakfastLabel, selectedRecord.beforeBreakfast)
                glucoseItem(afterBreakfastLabel, selectedRecord.afterBreakfast)
                glucoseItem(beforeLunchLabel, selectedRecord.beforeLunch)
                glucoseItem(afterLunchLabel, selectedRecord.afterLunch)
                glucoseItem(beforeDinnerLabel, selectedRecord.beforeDinner)
                glucoseItem(afterDinnerLabel, selectedRecord.afterDinner)
                glucoseItem(bedtimeLabel, selectedRecord.bedtime)
            }
        } else {
            EmptyDayContent()
        }
    }

    // Full-screen calendar sheet
    if (showCalendar) {
        FullScreenCalendarSheet(selectedDate = selectedDate, records = records, onDateSelected = {
            selectedDate = it
            showCalendar = false
        }, onDismiss = {
            showCalendar = false
        })
    }
}

fun LazyListScope.glucoseItem(
    label: String, value: Int?
) {

    if (value != null) {

        item {

            GlucoseReadingCard(
                label = label, value = value
            )
        }
    }
}

@Composable
fun DateNavigationHeader(
    selectedDate: LocalDate,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onDateClick: () -> Unit
) {
    // need to chnage for other languages
    val dayName =
        selectedDate.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val monthName = selectedDate.month.name.lowercase().replaceFirstChar { it.uppercase() }

    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Previous day button
        Box(
            modifier = Modifier.size(40.dp).clickable { onPreviousDay() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.prev_symbol),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Date display (clickable to open calendar)
        Box(
            modifier = Modifier.clickable { onDateClick() }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$dayName, ${selectedDate.dayOfMonth} $monthName ${selectedDate.year}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // Next day button
        Box(
            modifier = Modifier.size(40.dp).clickable { onNextDay() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.next_symbol),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

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

    val daysWithRecords = remember(records, currentMonth) {
        records.mapNotNull {
            try {
                parseDisplayDate(it.date)
            } catch (e: Exception) {
                null
            }
        }.filter {
            it.year == currentMonth.year && it.month == currentMonth.month
        }.map { it.dayOfMonth }.toSet()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
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
                    fontWeight = FontWeight.Bold)

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

            Spacer(modifier = Modifier.height(16.dp))

            // Calendar grid
            CalendarGrid(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                daysWithRecords = daysWithRecords,
                onDateSelected = { date ->
                    onDateSelected(date)
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                })

            Spacer(modifier = Modifier.height(16.dp))

            // Calendar legend
            CalendarLegend()

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CalendarGrid(
    currentMonth: LocalDate,
    selectedDate: LocalDate,
    daysWithRecords: Set<Int>,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = LocalDate(currentMonth.year, currentMonth.month, 1)
    val lastDayOfMonth = firstDayOfMonth.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)
    val startDayOfWeek = (firstDayOfMonth.dayOfWeek.ordinal + 1) % 7 // 0 = Sunday

    val daysInMonth = lastDayOfMonth.dayOfMonth

    val weekDays = listOf(
        stringResource(Res.string.sun_short),
        stringResource(Res.string.mon_short),
        stringResource(Res.string.tue_short),
        stringResource(Res.string.wed_short),
        stringResource(Res.string.thu_short),
        stringResource(Res.string.fri_short),
        stringResource(Res.string.sat_short)
    )

    // Day headers
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        weekDays.forEach { day ->
            Text(
                text = day,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Calendar days
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Empty cells for days before the first day of the month
        items(startDayOfWeek) {
            Spacer(modifier = Modifier.aspectRatio(1f))
        }

        // Days of the month
        items((1..daysInMonth).toList()) { day ->
            val date = LocalDate(currentMonth.year, currentMonth.month, day)
            val hasRecord = day in daysWithRecords
            val isSelected = date == selectedDate

            CalendarDayCell(
                day = day,
                hasRecord = hasRecord,
                isSelected = isSelected,
                onClick = { onDateSelected(date) })
        }
    }
}

@Composable
fun CalendarDayCell(
    day: Int, hasRecord: Boolean, isSelected: Boolean, onClick: () -> Unit
) {
    Box(
        modifier = Modifier.aspectRatio(1f).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier.size(40.dp).background(
                    MaterialTheme.colorScheme.primary, CircleShape
                ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.toString(), color = Color.White, fontWeight = FontWeight.Bold
                )
            }
        } else if (hasRecord) {
            Text(
                text = day.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        } else {
            Text(
                text = day.toString(),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun CalendarLegend() {

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        LegendItem(
            color = MaterialTheme.colorScheme.surfaceVariant,
            label = stringResource(Res.string.no_record)
        )

        LegendItem(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
            label = stringResource(Res.string.has_record)
        )

        LegendItem(
            color = MaterialTheme.colorScheme.primary, label = stringResource(Res.string.selected)
        )
    }
}

@Composable
fun LegendItem(
    color: Color, label: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(
            modifier = Modifier.size(12.dp).clip(CircleShape).background(color)
        )

        Text(
            text = label, style = MaterialTheme.typography.bodySmall
        )
    }
}


@Composable
fun GlucoseReadingCard(
    label: String, value: Int
) {

    val (status, color) = when {
        value < 70 -> stringResource(Res.string.low) to Color(0xFFE53935)
        value <= 140 -> stringResource(Res.string.normal) to Color(0xFF34C759)
        else -> stringResource(Res.string.high) to Color(0xFFFF9500)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Box(
                    modifier = Modifier.size(10.dp).background(color, CircleShape)
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = value.toString(), fontSize = 32.sp, fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = stringResource(Res.string.glucose_unit),
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                Text(
                    text = status, color = color, fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

fun parseDisplayDate(date: String): LocalDate {
    // Simple parsing assuming format "d MMMM yyyy" like "9 April 2026"
    val parts = date.split(" ")
    val day = parts[0].toInt()
    val monthName = parts[1].lowercase()
    val year = parts[2].toInt()

    val month = when (monthName) {
        "january" -> 1
        "february" -> 2
        "march" -> 3
        "april" -> 4
        "may" -> 5
        "june" -> 6
        "july" -> 7
        "august" -> 8
        "september" -> 9
        "october" -> 10
        "november" -> 11
        "december" -> 12
        else -> 1
    }

    return LocalDate(year, month, day)
}

@Composable
fun MonthCalendar(
    selectedDay: Int, daysWithRecords: Set<Int>, onDaySelected: (Int) -> Unit
) {

    val days = (1..31).map {

        CalendarDay(
            day = it, hasRecord = it in daysWithRecords, isSelected = it == selectedDay
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = stringResource(
                    Res.string.calendar_days_header
                ), color = Color.Gray, modifier = Modifier.padding(top = 16.dp)
            )

            val weekDays = listOf(
                stringResource(Res.string.sun_short),
                stringResource(Res.string.mon_short),
                stringResource(Res.string.tue_short),
                stringResource(Res.string.wed_short),
                stringResource(Res.string.thu_short),
                stringResource(Res.string.fri_short),
                stringResource(Res.string.sat_short)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                weekDays.forEach { day ->
                    Text(
                        text = day, color = Color.Gray, modifier = Modifier.weight(1f)
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(7)
            ) {

                items(days) { day ->

                    CalendarDayCell(
                        day = day, onClick = {
                            onDaySelected(day.day)
                        })
                }
            }
        }
    }
}

@Composable
fun CalendarDayCell(
    day: CalendarDay, onClick: () -> Unit
) {

    Box(
        modifier = Modifier.aspectRatio(1f).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {

        if (day.isSelected) {
            Box(
                modifier = Modifier.size(42.dp).background(
                    Color(0xFF3A7AFE), CircleShape
                )
            )
        }

        if (!day.hasRecord) {

            Canvas(
                modifier = Modifier.size(36.dp)
            ) {
                drawLine(
                    color = Color.LightGray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, 0f),
                    strokeWidth = 4f
                )
            }
        }

        Text(
            text = day.day.toString(), color = if (day.isSelected) Color.White
            else Color.Black, fontWeight = if (day.isSelected) FontWeight.Bold
            else FontWeight.Normal
        )
    }
}

data class CalendarDay(
    val day: Int, val hasRecord: Boolean, val isSelected: Boolean = false
)

@Composable
fun EmptyDayContent() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(Res.string.calendar_emoji), fontSize = 64.sp
        )

        Spacer(
            Modifier.height(16.dp)
        )

        Text(
            text = stringResource(
                Res.string.no_glucose_records_day
            ), color = Color.Gray
        )
    }
}