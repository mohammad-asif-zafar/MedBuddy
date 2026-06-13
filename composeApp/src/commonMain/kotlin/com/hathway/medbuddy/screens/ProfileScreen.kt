/*
package com.hathway.medbuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {


    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Card {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Personal Information",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(12.dp))

                    Text("Name: ${uiState.name}")
                    Text("Email: ${uiState.email}")
                }
            }
        }

        item {

            Card {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Doctor Information",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(12.dp))

                    Text("Doctor Name: ${uiState.doctorInfo.doctorName}")
                    Text("Doctor Type: ${uiState.doctorInfo.doctorType}")
                    Text("Speciality: ${uiState.doctorInfo.speciality}")
                    Text("Hospital: ${uiState.doctorInfo.hospital}")
                    Text("Next Appointment: ${uiState.doctorInfo.nextAppointment}")

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.showDoctorDialog()
                        }
                    ) {

                        Text(
                            if (uiState.doctorInfo.doctorName.isBlank())
                                "Add Doctor"
                            else
                                "Edit Doctor"
                        )
                    }
                }
            }
        }

        item {

            OutlinedButton(
                onClick = {
                    viewModel.logout()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logout")
            }
        }
    }

    if (uiState.showDoctorDialog) {

        DoctorDialog(
            doctorInfo = uiState.doctorInfo,
            onDismiss = {
                viewModel.hideDoctorDialog()
            },
            onSave = {
                viewModel.saveDoctor(it)
            }
        )
    }

}
*/

