package com.hathway.medbuddy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<String>>(emptyList())
    val searchResults: StateFlow<List<String>> = _searchResults.asStateFlow()
    
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()
    
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
    
    fun performSearch() {
        viewModelScope.launch {
            _isSearching.value = true
            // Simulate search operation
            delay(500)
            _searchResults.value = if (_searchQuery.value.isNotBlank()) {
                listOf("Result 1 for ${_searchQuery.value}", "Result 2 for ${_searchQuery.value}")
            } else {
                emptyList()
            }
            _isSearching.value = false
        }
    }
    
    fun clearSearch() {
        _searchQuery.value = ""
        _searchResults.value = emptyList()
    }
}
