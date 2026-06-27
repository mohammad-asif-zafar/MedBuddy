package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.CalendarDayStatus
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
fun CalendarGrid(
    currentMonth: LocalDate,
    selectedDate: LocalDate,
    dayStatuses: Map<LocalDate, CalendarDayStatus>,
    onDateSelected: (LocalDate) -> Unit
) {

    val firstDayOfMonth =
        LocalDate(currentMonth.year, currentMonth.month, 1)

    val lastDayOfMonth =
        firstDayOfMonth
            .plus(1, DateTimeUnit.MONTH)
            .minus(1, DateTimeUnit.DAY)

    val startDayOfWeek =
        (firstDayOfMonth.dayOfWeek.ordinal + 1) % 7

    val daysInMonth =
        lastDayOfMonth.dayOfMonth

    val weekDays = listOf(
        "Sun",
        "Mon",
        "Tue",
        "Wed",
        "Thu",
        "Fri",
        "Sat"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        weekDays.forEach {

            Text(
                text = it,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Spacer(
        modifier = Modifier.height(12.dp)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
    ) {

        items(startDayOfWeek) {
            Spacer(
                modifier = Modifier.aspectRatio(1f)
            )
        }

        items(daysInMonth) { index ->

            val day = index + 1

            val date = LocalDate(
                currentMonth.year,
                currentMonth.month,
                day
            )

            CalendarDayCell(
                day = day,
                status = dayStatuses[date],
                isSelected = date == selectedDate,
                onClick = {
                    onDateSelected(date)
                }
            )
        }
    }
}


// MOCK DATA
private fun createMockDayStatuses(currentMonth: LocalDate): Map<LocalDate, CalendarDayStatus> {
    return mapOf(
        LocalDate(currentMonth.year, currentMonth.month, 5) to CalendarDayStatus(
            date = LocalDate(currentMonth.year, currentMonth.month, 5),
            lowCount = 0, inRangeCount = 4, highCount = 0 // Normal/Healthy Day
        ),
        LocalDate(currentMonth.year, currentMonth.month, 12) to CalendarDayStatus(
            date = LocalDate(currentMonth.year, currentMonth.month, 12),
            lowCount = 1, inRangeCount = 2, highCount = 1 // Fluctuating Day
        ),
        LocalDate(currentMonth.year, currentMonth.month, 19) to CalendarDayStatus(
            date = LocalDate(currentMonth.year, currentMonth.month, 19),
            lowCount = 0, inRangeCount = 1, highCount = 3 // High-Alert Day
        )
    )
}


@Preview
@Composable
fun CalendarGridPreview() {
    val currentMonth = LocalDate(2026, 6, 1)
    val selectedDate = LocalDate(2026, 6, 12)
    val mockStatuses = createMockDayStatuses(currentMonth)

    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                // CRITICAL: Explicit height constraints protect the layout parser from grid calculation crashes
                .height(340.dp)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            CalendarGrid(
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                dayStatuses = mockStatuses,
                onDateSelected = {}
            )
        }
    }
}

