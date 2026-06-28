package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.data.local.FakeGlucoseRepository
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.UserGlucoseRecord
import com.hathway.medbuddy.presentation.components.glucose_components.GlucoseInputField
import com.hathway.medbuddy.presentation.components.glucose_components.NativeDatePickerDialog
import com.hathway.medbuddy.presentation.components.glucose_components.PrimaryButton
import com.hathway.medbuddy.presentation.components.glucose_components.TimePeriodSelector
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel
import com.hathway.medbuddy.util.clearFocusOnTapOutside
import com.hathway.medbuddy.util.formatDate
import com.hathway.medbuddy.util.getCurrentTime12Hour
import com.hathway.medbuddy.util.getNowLocalDateTime
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.add_glucose_record_title
import medbuddy.composeapp.generated.resources.calendar_emoji
import medbuddy.composeapp.generated.resources.cancel
import medbuddy.composeapp.generated.resources.change
import medbuddy.composeapp.generated.resources.date
import medbuddy.composeapp.generated.resources.glucose_saved_success
import medbuddy.composeapp.generated.resources.notes_optional
import medbuddy.composeapp.generated.resources.ok
import medbuddy.composeapp.generated.resources.save_reading
import medbuddy.composeapp.generated.resources.time_device_time
import medbuddy.composeapp.generated.resources.track_glucose_reading_desc
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddScreen(
    viewModel: AddViewModel,
    onSaveSuccess: () -> Unit = {},
    onCancel: () -> Unit = {}
) {
    val currentDeviceDate = remember { getNowLocalDateTime().date }
    var showDatePicker by remember { mutableStateOf(false) }

    var selectedDate by remember { mutableStateOf(currentDeviceDate) }
    var selectedTimePeriod by remember { mutableStateOf(TimePeriod.BEFORE_BREAKFAST) }
    var glucoseValue by remember { mutableStateOf("") }
    var time by remember { mutableStateOf(getCurrentTime12Hour()) }
    var notes by remember { mutableStateOf("") }
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            glucoseValue = ""
            notes = ""
            selectedDate = getNowLocalDateTime().date
            selectedTimePeriod = TimePeriod.BEFORE_BREAKFAST
            time = getCurrentTime12Hour()
            showSuccessDialog = true
            viewModel.resetSuccess()
        }
    }
    // 1. Loading Dialog (Displays when uiState.isSaving is true)
    if (uiState.isSaving) {
        Dialog(
            onDismissRequest = { /* Prevent dismissal while saving */ },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                modifier = Modifier.size(100.dp).background(
                    MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(16.dp)
                ), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

// 2. Success Dialog (Displays after data is saved successfully)
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = {
                Text(
                    text = stringResource(Res.string.save_reading),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = stringResource(Res.string.glucose_saved_success), // "Your glucose reading has been successfully recorded."
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    onSaveSuccess()
                }) {
                    Text(text = stringResource(Res.string.ok)) // "OK"
                }
            },
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }) { padding ->
        Card(
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp).fillMaxHeight()
                .clearFocusOnTapOutside(),
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

                // 1. Get the keyboard controller handle at the top of your layout code scope
                val keyboardController = LocalSoftwareKeyboardController.current

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Cancel / Reset Form Actions View
                    OutlinedButton(
                        onClick = {
                            keyboardController?.hide() // ⌨️ Gracefully dismiss keyboard on cancel click
                            glucoseValue = ""
                            notes = ""
                            selectedDate = getNowLocalDateTime().date
                            selectedTimePeriod = TimePeriod.BEFORE_BREAKFAST
                            time = getCurrentTime12Hour()
                           // onCancel()
                        },
                        enabled = !uiState.isSaving,
                        modifier = Modifier.weight(1f).height(54.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (!uiState.isSaving) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.outline.copy(
                                alpha = 0.12f
                            )
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        )
                    ) {
                        Text(
                            text = stringResource(Res.string.cancel),
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    val glucoseInt = glucoseValue.toIntOrNull()
                    val isValid = glucoseInt != null && glucoseInt > 0 && !uiState.isSaving

                    // Confirm Database Record Write Action
                    PrimaryButton(
                        text = stringResource(Res.string.save_reading),
                        saving = uiState.isSaving,
                        enabled = isValid,
                        modifier = Modifier.weight(1f).height(54.dp),
                        onClick = {
                            // ⌨️ Instantly dismisses soft-input panel layout before running back-end processing jobs
                            keyboardController?.hide()

                            glucoseInt?.let { validGlucose ->
                                val record = UserGlucoseRecord(
                                    date = selectedDate.toString(),
                                    timePeriod = selectedTimePeriod.name,
                                    value = validGlucose,
                                    time = time,
                                    mealType = selectedTimePeriod.name,
                                    notes = notes
                                )
                                viewModel.saveRecord(record)
                            }
                        })
                }
            }
        }
    }
}


// 1. Standard Single Preview
@Preview
@Composable
fun AddScreenPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        AddScreen(
            viewModel = AddViewModel(FakeGlucoseRepository()),
            onSaveSuccess = {},
            onCancel = {}
        )
    }
}

// 2. Dual Light/Dark Mode Multiplatform Preview
@Preview
@Composable
fun AddScreenInteractivePreview() {
    // Renders the UI layout for light and dark viewports concurrently
    Column {
        Text("Light Mode View:", style = MaterialTheme.typography.labelLarge)
        MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
            AddScreen(viewModel = AddViewModel(FakeGlucoseRepository()))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Dark Mode View:", style = MaterialTheme.typography.labelLarge)
        MedBuddyTheme(themeMode = ThemeMode.DARK) {
            AddScreen(viewModel = AddViewModel(FakeGlucoseRepository()))
        }
    }
}
