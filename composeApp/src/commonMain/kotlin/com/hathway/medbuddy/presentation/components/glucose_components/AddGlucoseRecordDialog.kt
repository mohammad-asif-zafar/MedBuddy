package com.hathway.medbuddy.presentation.components.glucose_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.window.Dialog
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.UserGlucoseRecord
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGlucoseRecordDialog(
    onDismiss: () -> Unit,
    onSave: (UserGlucoseRecord) -> Unit
) {
    // ✅ Dynamically fetches the current local device date on initialization
    val currentDeviceDate = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    // ✅ Get current device time
    val currentDeviceTime = remember {
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
    }
    val currentTimeString = remember(currentDeviceTime) {
        "${currentDeviceTime.hour}:${String.format("%02d", currentDeviceTime.minute)}"
    }

    // ✅ Initial state is now bound directly to the live device date
    var selectedDate by remember {
        mutableStateOf(currentDeviceDate)
    }
    var selectedTimePeriod by remember { mutableStateOf(TimePeriod.BEFORE_BREAKFAST) }
    var glucoseValue by remember { mutableStateOf("") }
    var time by remember { mutableStateOf(currentTimeString) }
    var notes by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with Gradient background
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Add Glucose Record",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Track your glucose reading",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = onDismiss
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close, contentDescription = "Close dialog"
                        )
                    }
                }

                // Date Picker
                var showDatePicker by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Date",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                            Text(
                                text = formatDate(selectedDate),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "change",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = "📅", style = MaterialTheme.typography.headlineMedium
                            )
                        }
                    }
                }

                // Native Date Picker Dialog
                if (showDatePicker) {
                    NativeDatePickerDialog(
                        onDateSelected = { newDate ->
                        selectedDate = newDate
                        showDatePicker = false
                    }, onDismiss = { showDatePicker = false }, initialDate = selectedDate
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Time Period Dropdown
                TimePeriodDropdown(
                    selected = selectedTimePeriod, onSelected = { selectedTimePeriod = it })

                Spacer(modifier = Modifier.height(24.dp))

                // Glucose Value Input
                GlucoseInputField(
                    value = glucoseValue,
                    onValueChange = { glucoseValue = it },
                    timePeriod = selectedTimePeriod
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Time Input (auto-populated with device time)
                OutlinedTextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (device time)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Notes Input
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Cancel Button
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Cancel")
                    }

                    // Save Button
                    val glucoseInt = glucoseValue.toIntOrNull()
                    val isValid = glucoseInt != null && glucoseInt > 0

                    PrimaryButton(
                        text = "Save", onClick = {
                            val record = UserGlucoseRecord(
                                date = formatDate(selectedDate),
                                timePeriod = selectedTimePeriod.name,
                                value = glucoseInt!!,
                                time = time,
                                mealType = selectedTimePeriod.name,
                                notes = notes
                            )

                            onSave(record)   // single source of truth
                            onDismiss()
                        }, modifier = Modifier.weight(1f), enabled = isValid
                    )
                }
            }
        }
    }
}

fun formatDate(date: LocalDate): String {
    val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
    return "${date.dayOfMonth} $month ${date.year}" // Note: changed from .day to .dayOfMonth to match kotlinx.datetime
}
