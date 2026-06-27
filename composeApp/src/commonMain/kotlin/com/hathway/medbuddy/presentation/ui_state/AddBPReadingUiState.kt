package com.hathway.medbuddy.presentation.ui_state

// UI State representing the data on screen
data class AddBPReadingUiState(
    val date: String = "May 20, 2024",
    val time: String = "8:30 AM",
    val systolic: Int = 120,
    val diastolic: Int = 80,
    val pulse: Int = 72,
    val notes: String = "",
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val feeling: String = "happy"
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
