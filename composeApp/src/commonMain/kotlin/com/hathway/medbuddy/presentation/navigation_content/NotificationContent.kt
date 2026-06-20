package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.presentation.ui.MedBuddyNotification
import com.hathway.medbuddy.presentation.viewmodel.NotificationViewModel

@Composable
fun NotificationContent(
    repository: IGlucoseRepository? = null,
    doctorRepository: IDoctorRepository? = null,
    onBack: () -> Unit
) {
    if (repository == null || doctorRepository == null) return
    
    val viewModel: NotificationViewModel = viewModel {
        NotificationViewModel(repository, doctorRepository)
    }

    MedBuddyNotification(
        viewModel = viewModel,
        onBackClick = onBack
    )
}
