package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.navigation.NavigationDestination
import com.hathway.medbuddy.presentation.ui.ReportsScreen
import com.hathway.medbuddy.presentation.viewmodel.ReportsViewModel

@Composable
fun ReportsContent(
    repository: IGlucoseRepository? = null,
    onFeatureClick: (NavigationDestination) -> Unit = {},
    onMenuClick: () -> Unit = {}
) {
    if (repository == null) return
    
    val viewModel: ReportsViewModel = viewModel {
        ReportsViewModel(repository)
    }

    ReportsScreen(
        viewModel = viewModel,
        onMenuClick = onMenuClick,
        onMoreOptionClick = { /* Handle more option */ },
        onFeatureClick = onFeatureClick,
        onExportPdf = {},
        onShareReport = {}
    )
}
