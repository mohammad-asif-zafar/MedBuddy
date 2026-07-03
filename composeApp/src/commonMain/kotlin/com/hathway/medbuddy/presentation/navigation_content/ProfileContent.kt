package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.ProfileScreen
import com.hathway.medbuddy.presentation.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.repository.IGlucoseRepository

@Composable
fun ProfileContent(
    repository: IGlucoseRepository? = null,
    doctorRepository: IDoctorRepository? = null,
    onBack: () -> Unit,
    logout: () -> Unit
) {
    val profileViewModel: ProfileViewModel = viewModel {
        ProfileViewModel(doctorRepository, repository)
    }

    ProfileScreen(profileViewModel, onBack, logout)
}
