package com.hathway.medbuddy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddViewModel : ViewModel() {
    
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()
    
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()
    
    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()
    
    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()
    
    fun onTitleChanged(newTitle: String) {
        _title.value = newTitle
    }
    
    fun onDescriptionChanged(newDescription: String) {
        _description.value = newDescription
    }
    
    fun saveItem() {
        viewModelScope.launch {
            _isSaving.value = true
            // Simulate save operation
            delay(800)
            _saveSuccess.value = true
            _isSaving.value = false
            
            // Reset form after successful save
            delay(1000)
            _title.value = ""
            _description.value = ""
            _saveSuccess.value = false
        }
    }
    
    fun clearForm() {
        _title.value = ""
        _description.value = ""
        _saveSuccess.value = false
    }
}
