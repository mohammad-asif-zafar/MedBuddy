package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.ui.FullHistoryScreen
import com.hathway.medbuddy.presentation.viewmodel.FullHistoryViewModel

@Composable
fun FullHistoryContent(
    repository: IGlucoseRepository,
    onBack: () -> Unit
) {
    val fullHistoryViewModel: FullHistoryViewModel = viewModel {
        FullHistoryViewModel(repository)
    }

    FullHistoryScreen(
        viewModel = fullHistoryViewModel,
        onBack = onBack
    )
}
