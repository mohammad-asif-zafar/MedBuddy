package com.hathway.medbuddy.presentation.components.calendar_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.CalendarDayStatus
import com.hathway.medbuddy.presentation.components.history_components.CalendarDayCell
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.util.getNowLocalDateTime

@Composable
fun CalendarGridBloodPressure(
    currentMonth: LocalDate,
    selectedDate: LocalDate,
    dayStatuses: Map<LocalDate, CalendarDayStatus>,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = LocalDate(currentMonth.year, currentMonth.month, 1)
    val lastDayOfMonth = firstDayOfMonth.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)
    val daysInMonth = lastDayOfMonth.dayOfMonth

    // FIX: Match the sequence exactly to the target design mockup image (Monday first)
    val weekDays = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    // FIX: Calculate offset based on ISO standard where Monday = 1 and Sunday = 7
    val startDayOfWeek = (firstDayOfMonth.dayOfWeek.ordinal - 1)

    Column(modifier = Modifier.fillMaxWidth()) {
        // Weekday Name Row Header Node
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weekDays.forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Fixed height box matches your preview layout bounds cleanly
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth().height(260.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Renders empty leading structural spaces
            items(startDayOfWeek) {
                Spacer(modifier = Modifier.aspectRatio(1f))
            }

            // Renders monthly day entries
            items(daysInMonth) { index ->
                val day = index + 1
                val date = LocalDate(currentMonth.year, currentMonth.month, day)

                CalendarDayCell(
                    day = day,
                    status = dayStatuses[date],
                    isSelected = date == selectedDate,
                    onClick = { onDateSelected(date) }
                )
            }
        }
    }
}

@Preview
@Composable
fun CalendarGridBloodPressurePreview() {
    val today = getNowLocalDateTime().date
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        CalendarGridBloodPressure(
            currentMonth = today,
            selectedDate = today,
            dayStatuses = emptyMap(),
            onDateSelected = {}
        )
    }
}
