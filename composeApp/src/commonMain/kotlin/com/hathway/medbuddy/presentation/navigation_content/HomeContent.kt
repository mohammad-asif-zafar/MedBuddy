package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.HomeScreen
import com.hathway.medbuddy.presentation.viewmodel.HomeViewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.repository.IDoctorRepository

@Composable
fun HomeContent(
    repository: IGlucoseRepository,
    doctorRepository: IDoctorRepository,
    onOpenNotifications: () -> Unit,
    onMenuClick: () -> Unit,
    onViewAllHistory: () -> Unit
) {
    HomeScreen(
        viewModel = HomeViewModel(repository, doctorRepository),
        onOpenNotifications = onOpenNotifications,
        onMenuClick = onMenuClick,
        onViewAllHistory = onViewAllHistory
    )
}
