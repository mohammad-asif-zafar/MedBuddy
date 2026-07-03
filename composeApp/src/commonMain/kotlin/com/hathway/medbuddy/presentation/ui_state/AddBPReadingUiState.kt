package com.hathway.medbuddy.presentation.ui_state

// UI State representing the data on screen
data class AddBPReadingUiState(
    val date: String = "",
    val time: String = "",
    val systolic: Int = 0,
    val diastolic: Int = 0,
    val pulse: Int = 0,
    val notes: String = "",
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val feeling: String = ""
)

// UI Events from User Interactions
sealed interface AddBPReadingUiEvent {
    data class SystolicChanged(val value: Int) : AddBPReadingUiEvent
    data class DiastolicChanged(val value: Int) : AddBPReadingUiEvent
    data class PulseChanged(val value: Int) : AddBPReadingUiEvent
    data class NotesChanged(val value: String) : AddBPReadingUiEvent
    object SaveClicked : AddBPReadingUiEvent
}

data class BloodPressureHomeUiState(
    val isLoading: Boolean = false
)
