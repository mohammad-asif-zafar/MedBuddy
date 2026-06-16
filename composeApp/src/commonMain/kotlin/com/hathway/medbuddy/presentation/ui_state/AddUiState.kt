package com.hathway.medbuddy.presentation.ui_state

import com.hathway.medbuddy.domain.model.GlucoseRecord

data class AddUiState(
    val records: List<GlucoseRecord> = emptyList(),
    val isLoading: Boolean = false,
    var showAddDialog: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val error: String? = null
)