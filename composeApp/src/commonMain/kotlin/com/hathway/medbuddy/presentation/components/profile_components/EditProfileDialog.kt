package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import androidx.compose.foundation.layout.Box

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    name: String,
    age: String,
    weight: String,
    bloodType: String,
    onDismiss: () -> Unit,
    onSave: (name: String, age: String, weight: String, bloodType: String) -> Unit
) {
    var editedName by remember { mutableStateOf(name) }
    var editedAge by remember { mutableStateOf(age) }
    var editedWeight by remember { mutableStateOf(weight) }
    var editedBloodType by remember { mutableStateOf(bloodType) }

    var showDatePicker by remember { mutableStateOf(false) }
    val currentDeviceDate = remember { getNowLocalDateTime().date }

    var selectedDate by remember { mutableStateOf(currentDeviceDate) }

    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= Clock.System.now().toEpochMilliseconds()
        }
    })

    if (showDatePicker) {
        DatePickerDialog(onDismissRequest = { showDatePicker = false }, confirmButton = {
            TextButton(
                onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (selectedMillis != null) {
                        val instant = Instant.fromEpochMilliseconds(selectedMillis)
                        val localDate =
                            instant.toLocalDateTime(TimeZone.currentSystemDefault()).date

                        // ✅ FIXED: Clean string manipulation replacing old capitalization loops
                        val rawMonth = localDate.month.name
                        val formattedMonth =
                            rawMonth.first().uppercase() + rawMonth.substring(1).lowercase()

                        editedAge = "${localDate.dayOfMonth} $formattedMonth ${localDate.year}"
                    }
                    showDatePicker = false
                }) {
                Text("OK")
            }
        }, dismissButton = {
            TextButton(onClick = { showDatePicker = false }) {
                Text("Cancel")
            }
        }) {
            DatePicker(state = datePickerState)
        }
    }

    // 1. Name Input
    EditProfileInputField(
        value = editedName,
        onValueChange = { editedName = it },
        label = "Name",
        icon = Icons.Default.Person
    )

    // ✅ 2. FIXED: Tap gesture now opens the calendar properly
    Box(
        modifier = Modifier.fillMaxWidth().clickable { showDatePicker = true }) {
        EditProfileInputField(
            value = editedAge,
            onValueChange = {},
            label = "Birthdate / Age",
            icon = Icons.Default.CalendarMonth,
            readOnly = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }

    // 3. Weight Input
    EditProfileInputField(
        value = editedWeight,
        onValueChange = { editedWeight = it },
        label = "Weight (kg)",
        icon = Icons.Default.MonitorWeight
    )

    // 4. Blood Type Input
    EditProfileInputField(
        value = editedBloodType,
        onValueChange = { editedBloodType = it },
        label = "Blood Type",
        icon = Icons.Default.WaterDrop
    )
}

@Composable
fun EditProfileInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        interactionSource = remember { MutableInteractionSource() },
        readOnly = readOnly,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.primary
        )
    )
}
