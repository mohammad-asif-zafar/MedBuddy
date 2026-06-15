package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.ui.ProfileScreen
import com.hathway.medbuddy.presentation.ui.ReportScreen
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel
import com.hathway.medbuddy.presentation.viewmodel.ProfileViewModel

@Composable
fun ReportsContent(repository: IGlucoseRepository? = null) {
    val viewModel: AddViewModel = viewModel {
        AddViewModel(repository)
    }

    ReportScreen(onBack = {}, viewModel = viewModel)
}
