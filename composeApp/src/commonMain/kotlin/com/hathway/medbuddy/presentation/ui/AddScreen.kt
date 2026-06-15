package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.UserGlucoseRecord
import com.hathway.medbuddy.presentation.components.glucose_components.GlucoseInputField
import com.hathway.medbuddy.presentation.components.glucose_components.NativeDatePickerDialog
import com.hathway.medbuddy.presentation.components.glucose_components.PrimaryButton
import com.hathway.medbuddy.presentation.components.glucose_components.TimePeriodDropdown
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.datetime.LocalDate
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.add_glucose_record_title
import medbuddy.composeapp.generated.resources.calendar_emoji
import medbuddy.composeapp.generated.resources.cancel
import medbuddy.composeapp.generated.resources.change
import medbuddy.composeapp.generated.resources.notes_optional
import medbuddy.composeapp.generated.resources.save
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

    var selectedDate by remember { mutableStateOf(currentDeviceDate) }
    var selectedTimePeriod by remember { mutableStateOf(TimePeriod.BEFORE_BREAKFAST) }
    var glucoseValue by remember { mutableStateOf("") }
    var time by remember { mutableStateOf(currentTimeString) }
    var notes by remember { mutableStateOf("") }

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
                modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState()),
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
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            Icon(Icons.Outlined.EditCalendar, contentDescription = null)
                            Text(
                                modifier = Modifier.padding(start = 10.dp),
                                text = formatDate(selectedDate),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(Res.string.change),
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Text(
                                text = stringResource(Res.string.calendar_emoji),
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
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
                TimePeriodDropdown(
                    selected = selectedTimePeriod, onSelected = { selectedTimePeriod = it })

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
                    readOnly = true
                )

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text(stringResource(Res.string.notes_optional)) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            // Fixed Buttons Footer
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { /* Add dismiss action here */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(Res.string.cancel))
                }

                val glucoseInt = glucoseValue.toIntOrNull()
                val isValid = glucoseInt != null && glucoseInt > 0

                PrimaryButton(
                    text = stringResource(Res.string.save), onClick = {
                        val record = UserGlucoseRecord(
                            date = formatDate(selectedDate),
                            timePeriod = selectedTimePeriod.name,
                            value = glucoseInt!!,
                            time = time,
                            mealType = selectedTimePeriod.name,
                            notes = notes
                        )
                        viewModel.saveRecord(record)
                    }, modifier = Modifier.weight(1f), enabled = isValid
                )
            }
        }
    }
}


fun formatDate(date: LocalDate): String {
    val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
    return "${date.dayOfMonth} $month ${date.year}" // Note: changed from .day to .dayOfMonth to match kotlinx.datetime
}
