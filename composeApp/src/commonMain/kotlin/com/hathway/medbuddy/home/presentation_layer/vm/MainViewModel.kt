package com.hathway.medbuddy.home.presentation_layer.vm

import androidx.lifecycle.ViewModel
import com.hathway.medbuddy.home.data_layer.UserRepositoryImpl
import com.hathway.medbuddy.home.presentation_layer.ui_state.MainUiState
import com.hathway.medbuddy.screens.GetUserTextUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val repository = UserRepositoryImpl()
    private val useCase = GetUserTextUseCase(repository)

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    fun onInputChange(value: String) {
        _uiState.update {
            it.copy(input = value)
        }
    }

    fun onButtonClick() {

        val result = useCase(
            _uiState.value.input
        )

        _uiState.update {
            it.copy(result = result)
        }
    }
}