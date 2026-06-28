package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.usecase.GetFullHistoryUseCase
import com.hathway.medbuddy.domain.usecase.RecentReading
import com.hathway.medbuddy.domain.model.GlucoseRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class HistorySummary(
    val averageValue: Int = 0,
    val normalCount: Int = 0,
    val highCount: Int = 0,
    val lowCount: Int = 0
)

class FullHistoryViewModel(
    private val repository: IGlucoseRepository
) : ViewModel() {
    private val getFullHistoryUseCase = GetFullHistoryUseCase(repository)

    private val _readings = MutableStateFlow<List<RecentReading>>(emptyList())
    val readings: StateFlow<List<RecentReading>> = _readings.asStateFlow()

    private val _summary = MutableStateFlow(HistorySummary())
    val summary: StateFlow<HistorySummary> = _summary.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        observeRecords()
    }

    private fun observeRecords() {
        viewModelScope.launch {
            repository.recordsFlow.collectLatest { records ->
                loadHistory(records)
            }
        }
    }

    private fun loadHistory(records: List<GlucoseRecord>) {
        viewModelScope.launch {
            val allReadings = getFullHistoryUseCase(records)
            _readings.value = allReadings
            
            // Calculate Summary
            if (allReadings.isNotEmpty()) {
                _summary.value = HistorySummary(
                    averageValue = allReadings.map { it.value }.average().toInt(),
                    normalCount = allReadings.count { it.status == "Normal" },
                    highCount = allReadings.count { it.status == "High" },
                    lowCount = allReadings.count { it.status == "Low" }
                )
            }
            _isLoading.value = false
        }
    }
}
