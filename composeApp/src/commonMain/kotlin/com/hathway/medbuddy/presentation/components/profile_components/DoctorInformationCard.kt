package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoctorInformationCard(
    doctorName: String,
    doctorType: String,
    speciality: String,
    hospital: String,
    nextAppointment: String,
    onEditClick: () -> Unit
) {

    val hasDoctorInfo = doctorName.isNotBlank()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            if (hasDoctorInfo) {

                Text(
                    text = doctorName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    if (doctorType.isNotBlank()) {
                        AssistChip(onClick = {}, label = {
                            Text(doctorType)
                        })
                    }

                    if (speciality.isNotBlank()) {
                        AssistChip(onClick = {}, label = {
                            Text(speciality)
                        })
                    }
                }

                if (hospital.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Text(
                        text = hospital,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (nextAppointment.isNotBlank()) {

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {

                        Row(
                            modifier = Modifier.padding(
                                horizontal = 12.dp, vertical = 8.dp
                            ), verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Spacer(
                                modifier = Modifier.width(8.dp)
                            )

                            Text(
                                text = nextAppointment,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

            } else {

                Text(
                    text = stringResource(Res.string.no_doctor_info),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = stringResource(Res.string.add_doctor_info_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            OutlinedButton(
                onClick = onEditClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {

                Icon(
                    imageVector = if (hasDoctorInfo) Icons.Default.Edit
                    else Icons.Default.Edit, contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    text = if (hasDoctorInfo) stringResource(Res.string.edit_doctor_info)
                    else stringResource(Res.string.add_doctor_info)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorInformationCardPreview() {

    MaterialTheme {

        DoctorInformationCard(
            doctorName = "Dr. Sarah Lim",
            doctorType = "Primary Care",
            speciality = "Endocrinologist",
            hospital = "Sunway Medical Centre",
            nextAppointment = "15 Jun 2026",
            onEditClick = {})
    }
}