package com.hathway.medbuddy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    
    private val _welcomeMessage = MutableStateFlow("Welcome to Home!")
    val welcomeMessage: StateFlow<String> = _welcomeMessage.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            // Simulate data loading
            delay(1000)
            _welcomeMessage.value = "Home Data Updated!"
            _isLoading.value = false
        }
    }
}
