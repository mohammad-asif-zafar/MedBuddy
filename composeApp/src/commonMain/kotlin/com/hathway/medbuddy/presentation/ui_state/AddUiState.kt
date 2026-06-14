package com.hathway.medbuddy.presentation.ui_state

import com.hathway.medbuddy.domain.model.GlucoseRecord

data class AddUiState(
    val records: List<GlucoseRecord> = emptyList(),
    val isLoading: Boolean = false,
    var showAddDialog: Boolean = false
)