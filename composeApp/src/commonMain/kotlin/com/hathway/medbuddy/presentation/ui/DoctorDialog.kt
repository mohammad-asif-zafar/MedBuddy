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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.presentation.components.profile_components.AppointmentDateCard
import com.hathway.medbuddy.presentation.theme.Cream
import com.hathway.medbuddy.presentation.theme.DeepGreen
import com.hathway.medbuddy.presentation.theme.CreamDarker
import com.hathway.medbuddy.presentation.theme.TextPrimary
import com.hathway.medbuddy.presentation.theme.TextSecondary
import medbuddy.composeapp.generated.resources.*
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
            shape = RoundedCornerShape(24.dp),
            color = Cream, // Matches card background
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())
            ) {
                // Header Panel matching the warm layout theme
                Box(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 20.dp)
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
                                color = TextSecondary
                            )
                            Text(
                                text = stringResource(Res.string.doctor_information),
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                        IconButton(
                            onClick = onDismiss, modifier = Modifier.background(
                                Color.Black.copy(alpha = 0.05f), CircleShape
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(Res.string.close),
                                tint = TextPrimary
                            )
                        }
                    }
                }

                // Input Section Form
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Group 1: Identity
                    Text(
                        text = stringResource(Res.string.general_information),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepGreen,
                        modifier = Modifier.padding(bottom = 2.dp)
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

                    Spacer(modifier = Modifier.height(4.dp))

                    // Group 2: Medical Details
                    Text(
                        text = stringResource(Res.string.practice_details),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepGreen,
                        modifier = Modifier.padding(bottom = 2.dp)
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

                    Spacer(modifier = Modifier.height(4.dp))

                    // Group 3: Schedule
                    Text(
                        text = stringResource(Res.string.follow_up),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = DeepGreen,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    var showDatePicker by remember { mutableStateOf(false) }

                    AppointmentDateCard(
                        appointmentDate = nextAppointment, onClick = { showDatePicker = true })

                    Spacer(modifier = Modifier.height(12.dp))

                    // Action Buttons Area
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Cancel/Discard Button
                        TextButton(
                            onClick = onDismiss,
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = TextSecondary
                            )
                        ) {
                            Text(
                                text = stringResource(Res.string.discard), fontWeight = FontWeight.SemiBold, fontSize = 16.sp
                            )
                        }

                        // Save Button utilizing our brand theme color accent
                        Button(
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
                            modifier = Modifier.weight(1f).height(50.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepGreen, contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = stringResource(Res.string.save_info), fontWeight = FontWeight.Bold, fontSize = 16.sp
                            )
                        }
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
                text = placeholder, color = TextSecondary.copy(alpha = 0.5f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = DeepGreen // Custom deep green brand icon accent
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            // Border Colors
            focusedBorderColor = DeepGreen, unfocusedBorderColor = TextSecondary.copy(alpha = 0.3f),

            // Text & Label Colors
            focusedLabelColor = DeepGreen, unfocusedLabelColor = TextSecondary,

            // Container Fill Colors (Blends with the cream background)
            focusedContainerColor = Color.Transparent, unfocusedContainerColor = CreamDarker
        )
    )
}
