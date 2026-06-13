package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.FirebaseManager
import com.hathway.medbuddy.presentation.ui_state.ProfileUiState
import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.usecase.GetDoctorUseCase
import com.hathway.medbuddy.domain.usecase.SaveDoctorUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val doctorRepository: IDoctorRepository? = null
) : ViewModel() {

    private val getDoctorUseCase = doctorRepository?.let { GetDoctorUseCase(it) }
    private val saveDoctorUseCase = doctorRepository?.let { SaveDoctorUseCase(it) }

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> =
        _uiState.asStateFlow()

    init {
        loadUser()
        loadDoctorInfo()
    }

    private fun getCurrentUserId(): String {
        return FirebaseManager.currentUser?.uid ?: ""
    }

    private fun loadUser() {

        val user = FirebaseManager.currentUser

        _uiState.update {

            it.copy(
                name = user?.displayName ?: "",
                email = user?.email ?: "",
                photoUrl = user?.photoUrl ?: ""
            )
        }
    }

    private fun loadDoctorInfo() {

        viewModelScope.launch {

            try {

                val userId = getCurrentUserId()

                if (userId.isEmpty()) return@launch

                val doctorInfo = getDoctorUseCase?.invoke(userId) ?: DoctorInfo()

                _uiState.update {
                    it.copy(
                        doctorInfo = doctorInfo
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun saveDoctor(
        doctorInfo: DoctorInfo
    ) {

        viewModelScope.launch {

            try {

                val userId = getCurrentUserId()

                if (userId.isEmpty()) return@launch

                saveDoctorUseCase?.invoke(userId, doctorInfo)

                _uiState.update {

                    it.copy(
                        doctorInfo = doctorInfo,
                        showDoctorDialog = false
                    )
                }

            } catch (e: Exception) {

                _uiState.update {
                    it.copy(
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun showDoctorDialog() {

        _uiState.update {
            it.copy(
                showDoctorDialog = true
            )
        }
    }

    fun hideDoctorDialog() {

        _uiState.update {
            it.copy(
                showDoctorDialog = false
            )
        }
    }

    fun logout() {

        FirebaseManager.signOut()
    }
}
