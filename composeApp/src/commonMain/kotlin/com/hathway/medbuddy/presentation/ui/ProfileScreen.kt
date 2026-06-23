package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.components.app_theme.ThemeSelectionDialog
import com.hathway.medbuddy.presentation.components.home_components.MedBuddyTopBar
import com.hathway.medbuddy.presentation.components.preferences_and_help_components.PreferencesAndHelpScreen
import com.hathway.medbuddy.presentation.components.profile_components.DoctorInformationCard
import com.hathway.medbuddy.presentation.components.profile_components.EditProfileDialog
import com.hathway.medbuddy.presentation.components.profile_components.ProfileHeaderCard
import com.hathway.medbuddy.presentation.components.profile_components.SectionHeader
import com.hathway.medbuddy.presentation.components.profile_components.SettingsSection
import com.hathway.medbuddy.presentation.viewmodel.ProfileViewModel
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.nav_profile
import medbuddy.composeapp.generated.resources.personal_health_details
import org.jetbrains.compose.resources.stringResource

@Composable
expect fun ProfileImagePicker(onImagePicked: (ByteArray) -> Unit): () -> Unit

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel, onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    // Controlled flag state to manage sub-screen switching safely
    var showPreferencesAndHelp by remember { mutableStateOf(false) }
    BackHandler(enabled = true) {
        onBack() // Executes your navigation lambda to route back home
    }
    // Intercept layout rendering if help screen option toggle state is enabled
    if (showPreferencesAndHelp) {
        PreferencesAndHelpScreen(
            onBackClick = onBack
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                MedBuddyTopBar(
                    title = stringResource(Res.string.nav_profile),
                    rightIcon = Icons.Outlined.Settings,
                    onRightClick = { },
                    titleColor = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Profile Header Card & Section Title (Grouped to manage overlapping offset)
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy((-42).dp) // Tighter grouping to eliminate excessive gap
                        ) {
                            ProfileHeaderCard(
                                name = uiState.name,
                                email = uiState.email,
                                photoUrl = uiState.photoUrl,
                                age = uiState.age,
                                weight = uiState.weight,
                                bloodType = uiState.bloodType,
                                isLoading = uiState.isLoading,
                                onEditPhotoClick = {
                                    viewModel.showProfileDialog()
                                },
                                onEditProfileClick = {
                                    viewModel.showProfileDialog()
                                })

                            SectionHeader(
                                title = stringResource(Res.string.personal_health_details),
                                icon = Icons.Default.MedicalServices
                            )
                        }
                    }

                    // Doctor Information Card
                    item {
                        DoctorInformationCard(
                            doctorName = uiState.doctorInfo.doctorName,
                            doctorType = uiState.doctorInfo.doctorType,
                            speciality = uiState.doctorInfo.speciality,
                            hospital = uiState.doctorInfo.hospital,
                            nextAppointment = uiState.doctorInfo.nextAppointment,
                            onDetailsClick = {
                                viewModel.showDoctorDialog()
                            })
                    }

                    // Settings & Logout Section
                    item {
                        SettingsSection(
                            onPreferencesClick = { viewModel.showThemeDialog() },
                            onHelpClick = {
                                // Safely toggle visibility state wrapper flag on UI threads
                                showPreferencesAndHelp = true
                            },
                            onLogoutClick = { viewModel.logout() })
                    }

                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                // Dialog Conditional Visibility States
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
                        onDismiss = { viewModel.hideProfileDialog() },
                        onSave = { name, age, weight, bloodType ->
                            viewModel.saveProfile(name, age, weight, bloodType)
                        })
                }

                if (uiState.showThemeDialog) {
                    ThemeSelectionDialog(
                        currentMode = uiState.themeMode,
                        onDismiss = { viewModel.hideThemeDialog() },
                        onSelect = {
                            viewModel.setThemeMode(it)
                            viewModel.hideThemeDialog()
                        })
                }
            }
        }
    }
}
