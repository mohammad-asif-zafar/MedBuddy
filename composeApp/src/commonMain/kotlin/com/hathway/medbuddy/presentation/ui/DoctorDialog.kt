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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.presentation.components.glucose_components.PrimaryButton
import com.hathway.medbuddy.presentation.components.profile_components.AppointmentDateCard
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.close
import medbuddy.composeapp.generated.resources.discard
import medbuddy.composeapp.generated.resources.doctor_information
import medbuddy.composeapp.generated.resources.doctor_name
import medbuddy.composeapp.generated.resources.doctor_name_placeholder
import medbuddy.composeapp.generated.resources.follow_up
import medbuddy.composeapp.generated.resources.general_information
import medbuddy.composeapp.generated.resources.medical_center
import medbuddy.composeapp.generated.resources.medical_center_placeholder
import medbuddy.composeapp.generated.resources.practice_details
import medbuddy.composeapp.generated.resources.profile_details
import medbuddy.composeapp.generated.resources.role_title
import medbuddy.composeapp.generated.resources.role_title_placeholder
import medbuddy.composeapp.generated.resources.save_info
import medbuddy.composeapp.generated.resources.speciality
import medbuddy.composeapp.generated.resources.speciality_placeholder
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoctorDialog(
    doctorInfo: DoctorInfo, onDismiss: () -> Unit, onSave: (DoctorInfo) -> Unit
) {
    var doctorName by remember { mutableStateOf(doctorInfo.doctorName) }
    var doctorType by remember { mutableStateOf(doctorInfo.doctorType) }
    var speciality by remember { mutableStateOf(doctorInfo.speciality) }
    var hospital by remember { mutableStateOf(doctorInfo.hospital) }
    var nextAppointment by remember { mutableStateOf(doctorInfo.nextAppointment) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            ) {
                // Modern Header with Primary Container color
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer).padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = stringResource(Res.string.profile_details),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                            )
                            Text(
                                text = stringResource(Res.string.doctor_information),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        IconButton(
                            onClick = onDismiss, modifier = Modifier.background(
                                MaterialTheme.colorScheme.surface.copy(
                                    alpha = 0.5f
                                ), CircleShape
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(Res.string.close),
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
                        text = stringResource(Res.string.general_information),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    DoctorInputField(
                        value = doctorName,
                        onValueChange = { doctorName = it },
                        label = stringResource(Res.string.doctor_name),
                        icon = Icons.Default.Person,
                        placeholder = stringResource(Res.string.doctor_name_placeholder)
                    )

                    DoctorInputField(
                        value = doctorType,
                        onValueChange = { doctorType = it },
                        label = stringResource(Res.string.role_title),
                        icon = Icons.Default.Badge,
                        placeholder = stringResource(Res.string.role_title_placeholder)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Group 2: Medical Details
                    Text(
                        text = stringResource(Res.string.practice_details),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    DoctorInputField(
                        value = speciality,
                        onValueChange = { speciality = it },
                        label = stringResource(Res.string.speciality),
                        icon = Icons.Default.Work,
                        placeholder = stringResource(Res.string.speciality_placeholder)
                    )

                    DoctorInputField(
                        value = hospital,
                        onValueChange = { hospital = it },
                        label = stringResource(Res.string.medical_center),
                        icon = Icons.Default.Business,
                        placeholder = stringResource(Res.string.medical_center_placeholder)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Group 3: Schedule
                    Text(
                        text = stringResource(Res.string.follow_up),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    var showDatePicker by remember {
                        mutableStateOf(false)
                    }

                    AppointmentDateCard(
                        appointmentDate = nextAppointment, onClick = {
                            showDatePicker = true
                        })

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
                            Text(stringResource(Res.string.discard), fontWeight = FontWeight.Bold)
                        }
                        PrimaryButton(
                            text = stringResource(Res.string.save_info), onClick = {
                                onSave(
                                    DoctorInfo(
                                        doctorName = doctorName,
                                        doctorType = doctorType,
                                        speciality = speciality,
                                        hospital = hospital,
                                        nextAppointment = nextAppointment
                                    )
                                )
                            }, modifier = Modifier.weight(1f), enabled = true
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
        placeholder = {
            Text(
                placeholder, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
        },
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
