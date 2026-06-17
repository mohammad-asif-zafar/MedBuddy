package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.ui.GlucoseRecordHistory
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel

@Composable
fun HistoryContent(
    repository: IGlucoseRepository? = null, onNavigateToAdd: () -> Unit = {}
) {
    val viewModel: AddViewModel = viewModel {
        AddViewModel(repository)
    }

    val uiState by viewModel.uiState.collectAsState()

    GlucoseRecordHistory(
        records = uiState.records, viewModel = viewModel, onNavigateToAdd = onNavigateToAdd
    )
}
