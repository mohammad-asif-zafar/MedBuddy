package com.hathway.medbuddy.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hathway.medbuddy.data.GlucoseRecord
import com.hathway.medbuddy.data.UserGlucoseRecord
import com.hathway.medbuddy.data.userGlucoseRecords
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGlucoseRecordDialog(
    onDismiss: () -> Unit,
    onSave: (UserGlucoseRecord) -> Unit
) {
    var selectedDate by remember { mutableStateOf(Date()) }
    var selectedTimePeriod by remember { mutableStateOf(TimePeriod.FASTING) }
    var glucoseValue by remember { mutableStateOf("") }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    text = "Add Glucose Record",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                // Date Picker
                var showDatePicker by remember { mutableStateOf(false) }
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
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
                        Text(
                            text = "📅",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }
                
                // Date Picker Dialog
                if (showDatePicker) {
                    DatePickerDialog(
                        onDateSelected = { newDate ->
                            selectedDate = newDate
                            showDatePicker = false
                        },
                        onDismiss = { showDatePicker = false },
                        initialDate = selectedDate
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Time Period Dropdown
                var expanded by remember { mutableStateOf(false) }
                
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedTimePeriod.displayName,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Select Time Period") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        TimePeriod.values().forEach { period ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = period.displayName,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                },
                                onClick = {
                                    selectedTimePeriod = period
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Glucose Value Input
                OutlinedTextField(
                    value = glucoseValue,
                    onValueChange = { glucoseValue = it.filter { it.isDigit() } },
                    label = { Text("Glucose Level (mg/dL)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    ),
                    supportingText = {
                        Text(
                            text = "Enter your glucose reading for ${selectedTimePeriod.displayName.lowercase()}",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
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
                        ),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text("Cancel")
                    }
                    
                    // Save Button
                    Button(
                        onClick = {
                            val newRecord = UserGlucoseRecord(
                                date = formatDate(selectedDate),
                                timePeriod = selectedTimePeriod.displayName,
                                value = glucoseValue.toIntOrNull() ?: 0
                            )
                            userGlucoseRecords.add(newRecord)
                            onSave(newRecord)
                            onDismiss() // Dismiss dialog immediately after save
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        enabled = glucoseValue.isNotBlank()
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

enum class TimePeriod(val displayName: String) {
    FASTING("Fasting (before breakfast)"),
    BEFORE_BREAKFAST("Before Breakfast"),
    AFTER_BREAKFAST("After Breakfast"),
    BEFORE_LUNCH("Before Lunch"),
    AFTER_LUNCH("After Lunch"),
    BEFORE_DINNER("Before Dinner"),
    AFTER_DINNER("After Dinner"),
    BEDTIME("Bedtime")
}

private fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    return formatter.format(date)
}

private fun createGlucoseRecord(
    date: String,
    timePeriod: TimePeriod,
    value: Int?
): GlucoseRecord {
    return when (timePeriod) {
        TimePeriod.FASTING -> GlucoseRecord(date, value, null, null, null)
        TimePeriod.BEFORE_BREAKFAST -> GlucoseRecord(date, null, value, null, null)
        TimePeriod.AFTER_BREAKFAST -> GlucoseRecord(date, null, value, null, null)
        TimePeriod.BEFORE_LUNCH -> GlucoseRecord(date, null, null, value, null)
        TimePeriod.AFTER_LUNCH -> GlucoseRecord(date, null, null, value, null)
        TimePeriod.BEFORE_DINNER -> GlucoseRecord(date, null, null, null, value)
        TimePeriod.AFTER_DINNER -> GlucoseRecord(date, null, null, null, value)
        TimePeriod.BEDTIME -> GlucoseRecord(date, null, null, null, value)
    }
}
