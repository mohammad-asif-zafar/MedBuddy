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

@Composable
fun DoctorDialog(
    doctorInfo: DoctorInfo, onDismiss: () -> Unit, onSave: (DoctorInfo) -> Unit
) {
    var doctorName by remember { mutableStateOf(doctorInfo.doctorName) }
    var doctorType by remember { mutableStateOf(doctorInfo.doctorType) }
    var speciality by remember { mutableStateOf(doctorInfo.speciality) }
    var hospital by remember { mutableStateOf(doctorInfo.hospital) }
    var nextAppointment by remember { mutableStateOf(doctorInfo.nextAppointment) }

    // Color definitions matching the previous UI card palette
    val brandCream = Color(0xFFFEF9F0)
    val brandGreen = Color(0xFF1B5E20)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            color = brandCream, // Matches card background
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
                                text = "Profile Details",
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.Gray
                            )
                            Text(
                                text = "Doctor Information",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        IconButton(
                            onClick = onDismiss, modifier = Modifier.background(
                                Color.Black.copy(alpha = 0.05f), CircleShape
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Black
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
                        text = "General Information",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = brandGreen,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    DoctorInputField(
                        value = doctorName,
                        onValueChange = { doctorName = it },
                        label = "Doctor Name",
                        icon = Icons.Default.Person,
                        placeholder = "e.g., Dr. Sumit Gulla"
                    )

                    DoctorInputField(
                        value = doctorType,
                        onValueChange = { doctorType = it },
                        label = "Role Title",
                        icon = Icons.Default.Badge,
                        placeholder = "e.g., MBBS, MD"
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Group 2: Medical Details
                    Text(
                        text = "Practice Details",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = brandGreen,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )

                    DoctorInputField(
                        value = speciality,
                        onValueChange = { speciality = it },
                        label = "Speciality",
                        icon = Icons.Default.Work,
                        placeholder = "e.g., Diabetologist"
                    )

                    DoctorInputField(
                        value = hospital,
                        onValueChange = { hospital = it },
                        label = "Medical Center",
                        icon = Icons.Default.Business,
                        placeholder = "e.g., Miracles Health"
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Group 3: Schedule
                    Text(
                        text = "Follow Up",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = brandGreen,
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
                                contentColor = Color.Gray
                            )
                        ) {
                            Text(
                                text = "Discard", fontWeight = FontWeight.SemiBold, fontSize = 16.sp
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
                                containerColor = brandGreen, contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = "Save Info", fontWeight = FontWeight.Bold, fontSize = 16.sp
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
    // Exact colors from our previous card/dialog theme
    val brandGreen = Color(0xFF1B5E20)
    val brandCreamDarker =
        Color(0xFFF4EFE6) // Slightly darker tint for the unfocused background fill

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        placeholder = {
            Text(
                text = placeholder, color = Color.Gray.copy(alpha = 0.5f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = brandGreen // Custom deep green brand icon accent
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            // Border Colors
            focusedBorderColor = brandGreen, unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),

            // Text & Label Colors
            focusedLabelColor = brandGreen, unfocusedLabelColor = Color.Gray,

            // Container Fill Colors (Blends with the cream background)
            focusedContainerColor = Color.Transparent, unfocusedContainerColor = brandCreamDarker
        )
    )
}

