package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.usecase.GetNotificationsUseCase
import com.hathway.medbuddy.domain.usecase.MedBuddyNotification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NotificationUiState(
    val isLoading: Boolean = true,
    val notifications: List<MedBuddyNotification> = emptyList(),
    val error: String? = null
)

class NotificationViewModel(
    private val glucoseRepository: IGlucoseRepository,
    private val doctorRepository: IDoctorRepository
) : ViewModel() {

    private val getNotificationsUseCase = GetNotificationsUseCase(glucoseRepository, doctorRepository)

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    fun loadNotifications() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val notifications = getNotificationsUseCase()
                _uiState.update { it.copy(isLoading = false, notifications = notifications) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }
}
