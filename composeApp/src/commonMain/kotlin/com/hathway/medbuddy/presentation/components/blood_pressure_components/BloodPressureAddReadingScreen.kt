@file:Suppress("DEPRECATION")

package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import com.hathway.medbuddy.presentation.viewmodel.BloodPressureHomeViewModel
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.title_add_blood_pressure_reading
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BloodPressureAddReadingScreen(
    onBack: () -> Unit, viewReading: () -> Unit
) {
    BackHandler(enabled = true) {
        onBack()
    }
    val bpViewModel: BloodPressureHomeViewModel = viewModel { BloodPressureHomeViewModel() }
    val state by bpViewModel.uiState.collectAsState()
    DetailedReportWrapper(stringResource(Res.string.title_add_blood_pressure_reading), onBack) {
// Wrapped into a Material 3 Card component
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            border = BorderStroke(
                width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Date & Time Component Field
                Text(
                    text = "Date & Time", style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray, fontWeight = FontWeight.Medium
                    ), modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().height(54.dp)
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${state.date}   •   ${state.time}",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Select Date",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Counter Selectors
                CounterSelector(
                    label = "Systolic (mmHg)",
                    value = state.systolic,
                    onValueChange = { /*onEvent(AddBPReadingUiEvent.SystolicChanged(it))*/ })

                Spacer(modifier = Modifier.height(16.dp))

                CounterSelector(
                    label = "Diastolic (mmHg)",
                    value = state.diastolic,
                    onValueChange = {/* onEvent(AddBPReadingUiEvent.DiastolicChanged(it)) */ })

                Spacer(modifier = Modifier.height(16.dp))

                CounterSelector(
                    label = "Pulse (bpm)",
                    value = state.pulse,
                    onValueChange = { /*onEvent(AddBPReadingUiEvent.PulseChanged(it)) */ })

                Spacer(modifier = Modifier.height(20.dp))

                // Mood Selector Row
                Text(
                    text = "How are you feeling?", style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray, fontWeight = FontWeight.Medium
                    ), modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth().height(54.dp)
                        .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(12.dp))
                        .clickable { /* Handle mood drop-down event click */ }
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = state.feeling.ifEmpty { "Normal" },
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown Options",
                        tint = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Notes Section
                Text(
                    text = "Notes (optional)", style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray, fontWeight = FontWeight.Medium
                    ), modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = state.notes,
                    onValueChange = {/* onEvent(AddBPReadingUiEvent.NotesChanged(it)) */ },
                    placeholder = { Text("e.g. Felt a little tired", color = Color.LightGray) },
                    modifier = Modifier.fillMaxWidth().height(72.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = Color(0xFFF9FAFB),
                        unfocusedContainerColor = Color(0xFFF9FAFB)
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Action Execution Button
                Button(
                    onClick = { viewReading() },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F66F6))
                ) {
                    Text(
                        text = "Save Reading", style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold, color = Color.White
                        )
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}