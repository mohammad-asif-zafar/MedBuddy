package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.DeepGreen
import com.hathway.medbuddy.presentation.theme.MiniCardBackground
import com.hathway.medbuddy.presentation.theme.TextPrimary
import com.hathway.medbuddy.presentation.theme.TextSecondary
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoctorInformationCard(
    doctorName: String,
    doctorType: String,
    speciality: String,
    hospital: String,
    nextAppointment: String,
    onDetailsClick: () -> Unit // Updated to match the "View Doctor Details" intent
) {
    val hasDoctorInfo = doctorName.isNotBlank()

    Card(
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MiniCardBackground
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (hasDoctorInfo) {
                // Horizontal arrangement for Profile, Text Details, and Next Visit Date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Profile Image Container Placeholder
                        Surface(
                            modifier = Modifier.size(64.dp).clip(CircleShape), color = Color.White
                        ) {
                            // Replace with an AsyncImage or Image loader pointing to your doctor drawable asset
                        }

                        // Text Descriptions
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = doctorName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary,
                                    fontSize = 18.sp
                                )
                            )
                            if (doctorType.isNotBlank() || speciality.isNotBlank()) {
                                val credentials =
                                    listOf(doctorType, speciality).filter { it.isNotBlank() }
                                        .joinToString(", ")

                                Text(
                                    text = credentials,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                            if (hospital.isNotBlank()) {
                                Text(
                                    text = hospital,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                    }

                    // Next Visit block aligned completely to the right edge
                    if (nextAppointment.isNotBlank()) {
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = stringResource(Res.string.next_visit),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Medium,
                                    color = DeepGreen // Deep green brand accent
                                )
                            )
                            Text(
                                text = nextAppointment,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold, color = TextPrimary
                                )
                            )
                        }
                    }
                }
            } else {
                // Fallback Empty State Display layout
                Column {
                    Text(
                        text = stringResource(Res.string.no_doctor_info_available),
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(Res.string.add_doctor_info_prompt),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Button matching the border contour line seen in the mock design
            OutlinedButton(
                onClick = onDetailsClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.dp
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = DeepGreen // Deep green matching text assets
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (hasDoctorInfo) stringResource(Res.string.view_doctor_details) else stringResource(Res.string.add_doctor_info_btn),
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }
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
            onDetailsClick = {})
    }
}