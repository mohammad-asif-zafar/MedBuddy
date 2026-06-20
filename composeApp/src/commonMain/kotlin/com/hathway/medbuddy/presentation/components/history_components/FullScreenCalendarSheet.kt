package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.presentation.theme.StatusHigh
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.StatusLow
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import medbuddy.composeapp.generated.resources.*
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
                    record.beforeBreakfast, record.afterBreakfast,
                    record.beforeLunch, record.afterLunch,
                    record.beforeDinner, record.afterDinner,
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
                    text = "${currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${currentMonth.year}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

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

@Composable
fun CalendarSummaryCard(
    selectedDate: LocalDate, records: List<GlucoseRecord>
) {
    val dayRecords = records.filter { parseDisplayDate(it.date) == selectedDate }
    val readings = dayRecords.flatMap {
        listOfNotNull(
            it.beforeBreakfast, it.afterBreakfast, it.beforeLunch,
            it.afterLunch, it.beforeDinner, it.afterDinner, it.bedtime
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${selectedDate.dayOfMonth} ${selectedDate.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${selectedDate.year} Summary", 
                fontWeight = FontWeight.Bold, 
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

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

            Text(text = stringResource(Res.string.average), color = MaterialTheme.colorScheme.onSurfaceVariant)

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
                        shape = RoundedCornerShape(50), 
                        color = statusColor.copy(alpha = 0.12f)
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

data class CalendarDayStatus(
    val date: LocalDate, val lowCount: Int, val inRangeCount: Int, val highCount: Int
)
