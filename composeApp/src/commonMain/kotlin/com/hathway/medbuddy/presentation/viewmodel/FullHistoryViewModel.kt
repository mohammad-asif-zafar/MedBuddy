package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.usecase.GetFullHistoryUseCase
import com.hathway.medbuddy.domain.usecase.RecentReading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FullHistoryViewModel(
    private val repository: IGlucoseRepository
) : ViewModel() {
    private val getFullHistoryUseCase = GetFullHistoryUseCase(repository)

    private val _readings = MutableStateFlow<List<RecentReading>>(emptyList())
    val readings: StateFlow<List<RecentReading>> = _readings.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _readings.value = getFullHistoryUseCase()
        }
    }
}
