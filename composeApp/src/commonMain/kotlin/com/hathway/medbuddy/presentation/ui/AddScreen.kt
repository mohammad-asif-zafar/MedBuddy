package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.DinnerDining
import androidx.compose.material.icons.outlined.FreeBreakfast
import androidx.compose.material.icons.outlined.LunchDining
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.UserGlucoseRecord
import com.hathway.medbuddy.presentation.components.glucose_components.GlucoseInputField
import com.hathway.medbuddy.presentation.components.glucose_components.NativeDatePickerDialog
import com.hathway.medbuddy.presentation.components.glucose_components.PrimaryButton
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.datetime.LocalDate
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.add_glucose_record_title
import medbuddy.composeapp.generated.resources.calendar_emoji
import medbuddy.composeapp.generated.resources.cancel
import medbuddy.composeapp.generated.resources.change
import medbuddy.composeapp.generated.resources.date
import medbuddy.composeapp.generated.resources.notes_optional
import medbuddy.composeapp.generated.resources.save_reading
import medbuddy.composeapp.generated.resources.saving
import medbuddy.composeapp.generated.resources.time_device_time
import medbuddy.composeapp.generated.resources.track_glucose_reading_desc
import org.jetbrains.compose.resources.stringResource


@Composable
fun AddScreen(
    viewModel: AddViewModel
) {
    val currentDeviceDate = remember { getNowLocalDateTime().date }
    var showDatePicker by remember { mutableStateOf(false) }
    val currentDeviceTime = remember { getNowLocalDateTime().time }
    val currentTimeString = remember(currentDeviceTime) {
        val hour = currentDeviceTime.hour
        val minute = currentDeviceTime.minute
        val minuteStr = if (minute < 10) "0$minute" else minute.toString()
        "$hour:$minuteStr"
    }
    val currentTimeStringAmPM = remember(currentDeviceTime) {
        val hour24 = currentDeviceTime.hour
        val minute = currentDeviceTime.minute

        val amPm = if (hour24 >= 12) "PM" else "AM"

        val hour12 = when {
            hour24 == 0 -> 12
            hour24 > 12 -> hour24 - 12
            else -> hour24
        }

        "${hour12}:${minute.toString().padStart(2, '0')} $amPm"
    }

    var selectedDate by remember { mutableStateOf(currentDeviceDate) }
    var selectedTimePeriod by remember { mutableStateOf(TimePeriod.BEFORE_BREAKFAST) }
    var glucoseValue by remember { mutableStateOf("") }
    var time by remember { mutableStateOf(currentTimeStringAmPM) }
    var notes by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()
    // snack bar

    LaunchedEffect(uiState.saveSuccess) {

        if (uiState.saveSuccess) {

            snackbarHostState.showSnackbar(
                message = "Glucose reading saved successfully"
            )

            glucoseValue = ""
            notes = ""

            selectedTimePeriod = TimePeriod.BEFORE_BREAKFAST

            viewModel.resetSuccess()
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }) { padding ->
        Card(
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp).fillMaxHeight(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Scrollable Form Layout Container
                Column(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                        .verticalScroll(rememberScrollState()),
                    // ✅ Spacing Engine: Handles all spacing cleanly between elements automatically
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(Res.string.add_glucose_record_title),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = stringResource(Res.string.track_glucose_reading_desc),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Change calendar card
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
                        border = BorderStroke(
                            1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                        )
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(
                                horizontal = 16.dp, vertical = 14.dp
                            ), verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Outlined.CalendarMonth,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.width(12.dp))

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = stringResource(Res.string.date),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Text(
                                    text = formatDate(selectedDate),
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Text(
                                text = stringResource(Res.string.calendar_emoji) + stringResource(
                                    Res.string.change
                                ),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    if (showDatePicker) {
                        NativeDatePickerDialog(
                            onDateSelected = { newDate ->
                            selectedDate = newDate
                            showDatePicker = false
                        }, onDismiss = { showDatePicker = false }, initialDate = selectedDate
                        )
                    }

                    // Inputs (No manual spacers needed anymore)
                    TimePeriodSelector(
                        selected = selectedTimePeriod, onSelected = {
                            selectedTimePeriod = it
                        })

                    GlucoseInputField(
                        value = glucoseValue,
                        onValueChange = { glucoseValue = it },
                        timePeriod = selectedTimePeriod
                    )

                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text(stringResource(Res.string.time_device_time)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.AccessTime, contentDescription = null
                            )
                        })

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text(stringResource(Res.string.notes_optional)) },
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        maxLines = 3
                    )
                }

                // Fixed Buttons Footer
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            glucoseValue = ""
                            notes = ""

                            selectedDate = currentDeviceDate

                            selectedTimePeriod = TimePeriod.BEFORE_BREAKFAST

                            time = currentTimeStringAmPM

                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(stringResource(Res.string.cancel))
                    }

                    val glucoseInt = glucoseValue.toIntOrNull()
                    val isValid = glucoseInt != null && glucoseInt > 0

                    PrimaryButton(
                        text = stringResource(Res.string.save_reading),
                        saving = uiState.isSaving,
                        enabled = isValid,
                        modifier = Modifier.weight(1f).height(54.dp),
                        onClick = {
                            val record = UserGlucoseRecord(
                                date = formatDate(selectedDate),
                                timePeriod = selectedTimePeriod.name,
                                value = glucoseInt!!,
                                time = time,
                                mealType = selectedTimePeriod.name,
                                notes = notes
                            )
                            viewModel.saveRecord(record)
                        })

                }
            }
        }
    }
}

fun TimePeriod.iconColor(): Color {
    return when (this) {
        TimePeriod.BEFORE_BREAKFAST -> Color(0xFFF59E0B)
        TimePeriod.AFTER_BREAKFAST -> Color(0xFFFB923C)

        TimePeriod.BEFORE_LUNCH -> Color(0xFF22C55E)
        TimePeriod.AFTER_LUNCH -> Color(0xFF14B8A6)

        TimePeriod.BEFORE_DINNER -> Color(0xFF8B5CF6)
        TimePeriod.BEDTIME -> Color(0xFF6366F1)
        else -> {
            Color(0xFF6366F1)
        }
    }
}

fun TimePeriod.icon(): ImageVector {
    return when (this) {

        TimePeriod.BEFORE_BREAKFAST -> Icons.Outlined.WbSunny

        TimePeriod.AFTER_BREAKFAST -> Icons.Outlined.FreeBreakfast

        TimePeriod.BEFORE_LUNCH -> Icons.Outlined.Restaurant

        TimePeriod.AFTER_LUNCH -> Icons.Outlined.LunchDining

        TimePeriod.BEFORE_DINNER -> Icons.Outlined.DinnerDining

        TimePeriod.BEDTIME -> Icons.Outlined.Bedtime

        else -> {
            Icons.Outlined.Bedtime

        }
    }
}

@Composable
fun TimePeriodSelector(
    selected: TimePeriod, onSelected: (TimePeriod) -> Unit
) {

    val periods = listOf(
        TimePeriod.BEFORE_BREAKFAST,
        TimePeriod.AFTER_BREAKFAST,
        TimePeriod.BEFORE_LUNCH,
        TimePeriod.AFTER_LUNCH,
        TimePeriod.BEFORE_DINNER,
        TimePeriod.BEDTIME
    )

    Column {

        Text(
            text = "Time Period",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        periods.chunked(2).forEach { rowItems ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                rowItems.forEach { period ->

                    val selectedItem = selected == period

                    Surface(
                        modifier = Modifier.weight(1f).height(60.dp).clickable {
                            onSelected(period)
                        },
                        shape = RoundedCornerShape(16.dp),
                        color = if (selectedItem) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(
                            width = if (selectedItem) 2.dp else 1.dp,
                            color = if (selectedItem) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline
                        )
                    ) {

                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Icon(
                                imageVector = period.icon(),
                                contentDescription = null,
                                tint = if (selectedItem) period.iconColor()
                                else period.iconColor().copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp),

                                )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                text = period.getDisplayName(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selectedItem) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}

// ✅ Extension function to clean up enum formatting (e.g., BEFORE_BREAKFAST -> Before Breakfast)
fun TimePeriod.getDisplayName(): String {
    return this.name.lowercase().split("_")
        .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
        // Handle custom overrides if enum doesn't map 1:1 to UI text
        .replace("Bedtime", "Before Bed")
}


fun formatDate(date: LocalDate): String {
    val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
    return "${date.dayOfMonth} $month ${date.year}" // Note: changed from .day to .dayOfMonth to match kotlinx.datetime
}
