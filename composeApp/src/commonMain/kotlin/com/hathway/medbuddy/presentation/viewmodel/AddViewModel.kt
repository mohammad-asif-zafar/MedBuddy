package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.domain.model.UserGlucoseRecord
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.usecase.GetGlucoseUseCase
import com.hathway.medbuddy.domain.usecase.SaveGlucoseUseCase
import com.hathway.medbuddy.presentation.ui_state.AddUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddViewModel(
    private val repository: IGlucoseRepository? = null
) : ViewModel() {

    private val saveGlucoseUseCase = repository?.let { SaveGlucoseUseCase(it) }
    private val getGlucoseUseCase = repository?.let { GetGlucoseUseCase(it) }

    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    init {
        loadRecords()
    }

    fun resetSuccess() {
        _uiState.update {
            it.copy(saveSuccess = false)
        }
    }

    fun loadRecords() {
        if (repository == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val records = getGlucoseUseCase?.invoke() ?: emptyList()
                _uiState.update { it.copy(records = records, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun saveRecord(newRecord: UserGlucoseRecord) {
        if (repository == null) return

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, saveSuccess = false) }
            try {
                saveGlucoseUseCase?.invoke(
                    date = newRecord.date,
                    beforeBreakfast = if (newRecord.timePeriod == "BEFORE_BREAKFAST") newRecord.value else null,
                    afterBreakfast = if (newRecord.timePeriod == "AFTER_BREAKFAST") newRecord.value else null,
                    beforeLunch = if (newRecord.timePeriod == "BEFORE_LUNCH") newRecord.value else null,
                    afterLunch = if (newRecord.timePeriod == "AFTER_LUNCH") newRecord.value else null,
                    beforeDinner = if (newRecord.timePeriod == "BEFORE_DINNER") newRecord.value else null,
                    afterDinner = if (newRecord.timePeriod == "AFTER_DINNER") newRecord.value else null,
                    bedtime = if (newRecord.timePeriod == "BEDTIME") newRecord.value else null,
                    time = newRecord.time,
                    mealType = newRecord.timePeriod,
                    notes = newRecord.notes
                )
                
                // Fetch updated records
                val records = getGlucoseUseCase?.invoke() ?: emptyList()
                
                _uiState.update {
                    it.copy(
                        records = records,
                        isSaving = false,
                        saveSuccess = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}
