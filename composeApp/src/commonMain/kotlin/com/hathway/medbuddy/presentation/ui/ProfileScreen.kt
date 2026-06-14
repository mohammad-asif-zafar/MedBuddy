package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.components.profile_components.DoctorInformationCard
import com.hathway.medbuddy.presentation.components.profile_components.EditProfileDialog
import com.hathway.medbuddy.presentation.components.profile_components.ProfileHeaderCard
import com.hathway.medbuddy.presentation.components.profile_components.SectionHeader
import com.hathway.medbuddy.presentation.components.profile_components.SettingsSection
import com.hathway.medbuddy.presentation.viewmodel.ProfileViewModel
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.personal_health_details
import org.jetbrains.compose.resources.stringResource

@Composable
expect fun ProfileImagePicker(onImagePicked: (ByteArray) -> Unit): () -> Unit

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val pickImage = ProfileImagePicker { bytes ->
        viewModel.updateProfilePicture(bytes)
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Profile Header Card
        item {
            ProfileHeaderCard(
                name = uiState.name,
                email = uiState.email,
                photoUrl = uiState.photoUrl,
                age = uiState.age,
                weight = uiState.weight,
                bloodType = uiState.bloodType,
                isLoading = uiState.isLoading,
                onEditPhotoClick = {
                    pickImage()
                },
                onEditProfileClick = {
                    viewModel.showProfileDialog()
                })

        }

        // Section Title
        item {
            SectionHeader(
                title = stringResource(
                    Res.string.personal_health_details
                ), icon = Icons.Default.MedicalServices
            )
        }

        // Doctor Information Card
        item {
            DoctorInformationCard(
                doctorName = uiState.doctorInfo.doctorName,
                doctorType = uiState.doctorInfo.doctorType,
                speciality = uiState.doctorInfo.speciality,
                hospital = uiState.doctorInfo.hospital,
                nextAppointment = uiState.doctorInfo.nextAppointment,
                onEditClick = {
                    viewModel.showDoctorDialog()
                })
        }

        // Settings  Logout Section
        item {
            SettingsSection(onPreferencesClick = {}, onLogoutClick = { viewModel.logout() })
        }
        item {
            Spacer(
                modifier = Modifier.height(6.dp)
            )
        }
    }


    if (uiState.showDoctorDialog) {
        DoctorDialog(
            doctorInfo = uiState.doctorInfo,
            onDismiss = { viewModel.hideDoctorDialog() },
            onSave = { viewModel.saveDoctor(it) })
    }

    if (uiState.showProfileDialog) {
        EditProfileDialog(
            name = uiState.name,
            age = uiState.age,
            weight = uiState.weight,
            bloodType = uiState.bloodType,
            onDismiss = {
                viewModel.hideProfileDialog()
            },
            onSave = { name, age, weight, bloodType ->
                viewModel.saveProfile(
                    name, age, weight, bloodType
                )
            })
    }
}
