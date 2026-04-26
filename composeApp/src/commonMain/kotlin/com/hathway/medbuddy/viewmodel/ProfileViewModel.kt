package com.hathway.medbuddy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserProfile(
    val name: String,
    val email: String,
    val phone: String,
    val dateOfBirth: String
)

class ProfileViewModel : ViewModel() {
    
    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing.asStateFlow()
    
    private val _editName = MutableStateFlow("")
    val editName: StateFlow<String> = _editName.asStateFlow()
    
    private val _editEmail = MutableStateFlow("")
    val editEmail: StateFlow<String> = _editEmail.asStateFlow()
    
    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()
    
    init {
        loadUserProfile()
    }
    
    private fun loadUserProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            // Simulate loading user profile
            delay(500)
            _userProfile.value = UserProfile(
                name = "John Doe",
                email = "john.doe@example.com",
                phone = "+1234567890",
                dateOfBirth = "1990-01-01"
            )
            _isLoading.value = false
        }
    }
    
    fun startEditing() {
        _userProfile.value?.let { profile ->
            _editName.value = profile.name
            _editEmail.value = profile.email
            _isEditing.value = true
        }
    }
    
    fun cancelEditing() {
        _isEditing.value = false
        _editName.value = ""
        _editEmail.value = ""
        _saveSuccess.value = false
    }
    
    fun onNameChanged(name: String) {
        _editName.value = name
    }
    
    fun onEmailChanged(email: String) {
        _editEmail.value = email
    }
    
    fun saveProfile() {
        viewModelScope.launch {
            _isLoading.value = true
            // Simulate saving profile
            delay(800)
            _userProfile.value?.let { currentProfile ->
                _userProfile.value = currentProfile.copy(
                    name = _editName.value,
                    email = _editEmail.value
                )
            }
            _saveSuccess.value = true
            _isEditing.value = false
            _isLoading.value = false
            
            // Reset success message after delay
            delay(2000)
            _saveSuccess.value = false
        }
    }
    
    fun refreshProfile() {
        loadUserProfile()
    }
}
