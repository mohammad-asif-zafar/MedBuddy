package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.domain.model.TimePeriod
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
    open val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

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
                // 1. Look for an existing day's record to prevent overwriting other meals with null
                val existingRecords = getGlucoseUseCase?.invoke() ?: emptyList()
                val existingDayRecord = existingRecords.find { it.date == newRecord.date }

                // 2. Safely merge the new reading value with existing daily fields
                saveGlucoseUseCase?.invoke(
                    date = newRecord.date,
                    beforeBreakfast = if (newRecord.timePeriod == TimePeriod.BEFORE_BREAKFAST.name) newRecord.value else existingDayRecord?.beforeBreakfast,
                    afterBreakfast = if (newRecord.timePeriod == TimePeriod.AFTER_BREAKFAST.name) newRecord.value else existingDayRecord?.afterBreakfast,
                    beforeLunch = if (newRecord.timePeriod == TimePeriod.BEFORE_LUNCH.name) newRecord.value else existingDayRecord?.beforeLunch,
                    afterLunch = if (newRecord.timePeriod == TimePeriod.AFTER_LUNCH.name) newRecord.value else existingDayRecord?.afterLunch,
                    beforeDinner = if (newRecord.timePeriod == TimePeriod.BEFORE_DINNER.name) newRecord.value else existingDayRecord?.beforeDinner,
                    afterDinner = if (newRecord.timePeriod == TimePeriod.AFTER_DINNER.name) newRecord.value else existingDayRecord?.afterDinner,
                    bedtime = if (newRecord.timePeriod == TimePeriod.BEDTIME.name) newRecord.value else existingDayRecord?.bedtime,
                    time = newRecord.time,
                    mealType = newRecord.timePeriod,
                    notes = newRecord.notes
                // Keep old notes if new ones are blank
                )

                // 3. Re-load the updated list state to refresh UI
                val updatedRecords = getGlucoseUseCase?.invoke() ?: emptyList()

                _uiState.update {
                    it.copy(
                        records = updatedRecords,
                        isSaving = false,
                        saveSuccess = true // This will trigger your Composable's LaunchedEffect
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }

}
