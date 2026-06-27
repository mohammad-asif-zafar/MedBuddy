package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.presentation.ui_state.AddBPReadingUiEvent
import com.hathway.medbuddy.presentation.ui_state.AddBPReadingUiState
import com.hathway.medbuddy.presentation.ui_state.BloodPressureHomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BloodPressureHomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddBPReadingUiState())
    val uiState: StateFlow<AddBPReadingUiState> = _uiState.asStateFlow()

    private val _uiStateBp = MutableStateFlow(BloodPressureHomeUiState())
    val uiStateBp: StateFlow<BloodPressureHomeUiState> = _uiStateBp.asStateFlow()

    fun onEvent(event: AddBPReadingUiEvent) {
        when (event) {
            is AddBPReadingUiEvent.SystolicChanged -> {
                _uiState.update { it.copy(systolic = event.value.coerceAtLeast(0)) }
            }

            is AddBPReadingUiEvent.DiastolicChanged -> {
                _uiState.update { it.copy(diastolic = event.value.coerceAtLeast(0)) }
            }

            is AddBPReadingUiEvent.PulseChanged -> {
                _uiState.update { it.copy(pulse = event.value.coerceAtLeast(0)) }
            }

            is AddBPReadingUiEvent.NotesChanged -> {
                _uiState.update { it.copy(notes = event.value) }
            }

            AddBPReadingUiEvent.SaveClicked -> saveReading()
        }
    }

    private fun saveReading() {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true) }
            // Invoke domain use-case execution logic here
            _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
        }
    }
}
