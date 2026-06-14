package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hathway.medbuddy.presentation.components.profile_components.AppointmentDateCard
import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.model.UserGlucoseRecord
import com.hathway.medbuddy.presentation.components.glucose_components.PrimaryButton
import com.hathway.medbuddy.presentation.components.glucose_components.formatDate

@Composable
fun DoctorDialog(
    doctorInfo: DoctorInfo,
    onDismiss: () -> Unit,
    onSave: (DoctorInfo) -> Unit
) {
    var doctorName by remember { mutableStateOf(doctorInfo.doctorName) }
    var doctorType by remember { mutableStateOf(doctorInfo.doctorType) }
    var speciality by remember { mutableStateOf(doctorInfo.speciality) }
    var hospital by remember { mutableStateOf(doctorInfo.hospital) }
    var nextAppointment by remember { mutableStateOf(doctorInfo.nextAppointment) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                // Modern Header with Primary Container color
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Profile Details",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                            Text(
                                text = "Doctor Information",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Group 1: Identity
                    Text(
                        text = "General Information",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    DoctorInputField(
                        value = doctorName,
                        onValueChange = { doctorName = it },
                        label = "Doctor Name",
                        icon = Icons.Default.Person,
                        placeholder = "e.g. Dr. Sumit Gulla"
                    )

                    DoctorInputField(
                        value = doctorType,
                        onValueChange = { doctorType = it },
                        label = "Role / Title",
                        icon = Icons.Default.Badge,
                        placeholder = "e.g. Primary Physician"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Group 2: Medical Details
                    Text(
                        text = "Practice Details",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    DoctorInputField(
                        value = speciality,
                        onValueChange = { speciality = it },
                        label = "Speciality",
                        icon = Icons.Default.Work,
                        placeholder = "e.g. Endocrinologist"
                    )

                    DoctorInputField(
                        value = hospital,
                        onValueChange = { hospital = it },
                        label = "Medical Center",
                        icon = Icons.Default.Business,
                        placeholder = "e.g. City Care Hospital"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Group 3: Schedule
                    Text(
                        text = "Follow-up",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    var showDatePicker by remember {
                        mutableStateOf(false)
                    }

                    AppointmentDateCard(
                        appointmentDate = nextAppointment,
                        onClick = {
                            showDatePicker = true
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons Area
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f).height(54.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Discard", fontWeight = FontWeight.Bold)
                        }
                        PrimaryButton(
                            text = "Save Info",
                            onClick = {
                                onSave(
                                    DoctorInfo(
                                        doctorName = doctorName,
                                        doctorType = doctorType,
                                        speciality = speciality,
                                        hospital = hospital,
                                        nextAppointment = nextAppointment
                                    )
                                )
                            },
                            modifier = Modifier.weight(1f),
                            enabled = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = { Text(placeholder, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
            focusedContainerColor = Color.Transparent
        )
    )
}
