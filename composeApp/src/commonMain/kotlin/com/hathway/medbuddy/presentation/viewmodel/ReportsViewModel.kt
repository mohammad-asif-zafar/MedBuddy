package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.usecase.GetReportsUseCase
import com.hathway.medbuddy.domain.usecase.ReportsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReportsUiState(
    val isLoading: Boolean = true,
    val selectedFilter: String = "7 Days",
    val reportsData: ReportsData? = null,
    val error: String? = null
)

class ReportsViewModel(
    private val repository: IGlucoseRepository
) : ViewModel() {

    private val getReportsUseCase = GetReportsUseCase(repository)

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadReports()
    }

    fun onFilterSelected(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
        loadReports()
    }

    private fun loadReports() {
        val days = when (_uiState.value.selectedFilter) {
            "7 Days" -> 7
            "30 Days" -> 30
            "90 Days" -> 90
            else -> 30
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val data = getReportsUseCase(days)
                _uiState.update { it.copy(isLoading = false, reportsData = data) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
