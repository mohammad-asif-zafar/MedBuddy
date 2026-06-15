package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.UserGlucoseRecord
import com.hathway.medbuddy.presentation.components.glucose_components.GlucoseInputField
import com.hathway.medbuddy.presentation.components.glucose_components.NativeDatePickerDialog
import com.hathway.medbuddy.presentation.components.glucose_components.TimePeriodDropdown
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel
import com.hathway.medbuddy.util.getNowLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(
    viewModel: AddViewModel, onBack: () -> Unit
) {

    val currentDeviceDate = remember {
        getNowLocalDateTime().date
    }

    val currentDeviceTime = remember {
        getNowLocalDateTime().time
    }

    val currentTimeString = remember(currentDeviceTime) {
        val minuteStr = currentDeviceTime.minute.toString().padStart(2, '0')

        "${currentDeviceTime.hour}:$minuteStr"
    }

    var selectedDate by remember {
        mutableStateOf(currentDeviceDate)
    }

    var selectedTimePeriod by remember {
        mutableStateOf(TimePeriod.BEFORE_BREAKFAST)
    }

    var glucoseValue by remember {
        mutableStateOf("")
    }

    var notes by remember {
        mutableStateOf("")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    val glucoseInt = glucoseValue.toIntOrNull()
    val isValid = glucoseInt != null && glucoseInt > 0

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Add Glucose Reading")
            }, navigationIcon = {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null
                    )
                }
            })
        },

        bottomBar = {

            Surface(
                shadowElevation = 8.dp, color = MaterialTheme.colorScheme.surface
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedButton(
                        modifier = Modifier.weight(1f), onClick = onBack
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        modifier = Modifier.weight(1f), enabled = isValid, onClick = {

                            val record = UserGlucoseRecord(
                                date = formatDate(selectedDate),
                                timePeriod = selectedTimePeriod.name,
                                value = glucoseInt!!,
                                time = currentTimeString,
                                mealType = selectedTimePeriod.name,
                                notes = notes
                            )

                            viewModel.saveRecord(record)

                            onBack()
                        }) {
                        Text("Save Reading")
                    }
                }
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Date", style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(8.dp))

            OutlinedCard(
                modifier = Modifier.fillMaxWidth().clickable {
                    showDatePicker = true
                }) {

                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        formatDate(selectedDate)
                    )

                    Text(
                        "Change", color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Glucose Reading (mg/dL)", style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(8.dp))

            GlucoseInputField(
                value = glucoseValue, onValueChange = {
                    glucoseValue = it
                }, timePeriod = selectedTimePeriod
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = selectedTimePeriod.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Time Period", style = MaterialTheme.typography.labelLarge
            )

            Spacer(Modifier.height(8.dp))

            TimePeriodDropdown(
                selected = selectedTimePeriod, onSelected = {
                    selectedTimePeriod = it
                })

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = currentTimeString,
                onValueChange = {},
                readOnly = true,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Time (device time)")
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.AccessTime, contentDescription = null
                    )
                })

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(value = notes, onValueChange = {
                notes = it
            }, modifier = Modifier.fillMaxWidth().height(120.dp), label = {
                Text("Notes (optional)")
            })

            Spacer(Modifier.height(100.dp))
        }

        if (showDatePicker) {

            NativeDatePickerDialog(initialDate = selectedDate, onDateSelected = {
                selectedDate = it
                showDatePicker = false
            }, onDismiss = {
                showDatePicker = false
            })
        }
    }
}

