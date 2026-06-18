package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults.containerColor
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.presentation.components.history_components.DateNavigationHeader
import com.hathway.medbuddy.presentation.components.history_components.EmptyDayContent
import com.hathway.medbuddy.presentation.components.history_components.FullScreenCalendarSheet
import com.hathway.medbuddy.presentation.components.history_components.GlucoseReadingCard
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel
import com.hathway.medbuddy.util.getNowLocalDateTime
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.after_breakfast
import medbuddy.composeapp.generated.resources.after_dinner
import medbuddy.composeapp.generated.resources.after_lunch
import medbuddy.composeapp.generated.resources.bedtime
import medbuddy.composeapp.generated.resources.before_breakfast
import medbuddy.composeapp.generated.resources.before_dinner
import medbuddy.composeapp.generated.resources.before_lunch
import org.jetbrains.compose.resources.stringResource

/**
 * History Screen
 *
 * Responsibilities:
 * - Shows glucose records for selected date
 * - Allows day-to-day navigation
 * - Opens calendar picker
 * - Navigates to Add Reading screen
 *
 * Flow:
 * User selects date
 *      ↓
 * Find matching GlucoseRecord
 *      ↓
 * Show readings for that day
 *      ↓
 * If no record exists → EmptyDayContent()
 */

@Composable
fun GlucoseRecordHistory(
    records: List<GlucoseRecord>, viewModel: AddViewModel, onNavigateToAdd: () -> Unit
) {
    /**
     * Finds the glucose record matching
     * the currently selected date.
     *
     * Example:
     * Selected Date = 15 Jun 2026
     *
     * Returns:
     * GlucoseRecord(date = "15 June 2026")
     */

    var selectedDate by remember {
        mutableStateOf(getNowLocalDateTime().date)
    }
    var showCalendar by remember { mutableStateOf(false) }

    val selectedRecord = records.firstOrNull {
        parseDisplayDate(it.date) == selectedDate
    }
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                onNavigateToAdd()

            }, icon = {
                Icon(
                    Icons.Default.Add, contentDescription = null
                )
            }, text = {
                Text("")
            })
        }) { padding ->

        Column(
            modifier = Modifier.fillMaxSize().padding(
                bottom = padding.calculateBottomPadding()
            )
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
                    glucoseItem(
                        beforeBreakfastLabel, selectedRecord.beforeBreakfast, selectedRecord.time
                    )
                    glucoseItem(
                        afterBreakfastLabel, selectedRecord.afterBreakfast, selectedRecord.time
                    )
                    glucoseItem(beforeLunchLabel, selectedRecord.beforeLunch, selectedRecord.time)
                    glucoseItem(afterLunchLabel, selectedRecord.afterLunch, selectedRecord.time)
                    glucoseItem(beforeDinnerLabel, selectedRecord.beforeDinner, selectedRecord.time)
                    glucoseItem(afterDinnerLabel, selectedRecord.afterDinner, selectedRecord.time)
                    glucoseItem(bedtimeLabel, selectedRecord.bedtime, selectedRecord.time)
                }
            } else {
                EmptyDayContent()
            }
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
    label: String, value: Int?, selectedDateTime: String
) {
    if (value != null) {
        item {
            GlucoseReadingCard(
                label = label, value = value, time = selectedDateTime
            )
        }
    }
}