package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.hathway.medbuddy.presentation.ui_state.BpDashboardUiState
import com.hathway.medbuddy.presentation.ui_state.BpHistoryUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BpDashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BpDashboardUiState())
    val uiState: StateFlow<BpDashboardUiState> = _uiState.asStateFlow()


    private val _uiBpState = MutableStateFlow(BpHistoryUiState())
    val uiBpState: StateFlow<BpHistoryUiState> = _uiBpState.asStateFlow()


}
