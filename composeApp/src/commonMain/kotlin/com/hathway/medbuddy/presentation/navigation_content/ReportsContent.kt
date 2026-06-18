package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.ui.ReportsScreen
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel

@Composable
fun ReportsContent(repository: IGlucoseRepository? = null) {
    val viewModel: AddViewModel = viewModel {
        AddViewModel(repository)
    }

    ReportsScreen(onMenuClick = {}, onCalendarClick = {}, onExportPdf = {}, onShareReport = {})
}
