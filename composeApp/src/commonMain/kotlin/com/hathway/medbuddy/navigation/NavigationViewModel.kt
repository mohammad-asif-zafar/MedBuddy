package com.hathway.medbuddy.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel : ViewModel() {
    
    private val _currentDestination = MutableStateFlow(NavigationDestination.HOME)
    val currentDestination: StateFlow<NavigationDestination> = _currentDestination.asStateFlow()
    
    fun navigateTo(destination: NavigationDestination) {
        viewModelScope.launch {
            _currentDestination.value = destination
        }
    }
}
