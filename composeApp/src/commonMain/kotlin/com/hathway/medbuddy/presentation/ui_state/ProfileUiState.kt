package com.hathway.medbuddy.presentation.ui_state

import com.hathway.medbuddy.profile.DoctorInfo

data class ProfileUiState(

    // User Information
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",

    // Doctor Information
    val doctorInfo: DoctorInfo = DoctorInfo(),

    // UI State
    val isLoading: Boolean = false,
    val showDoctorDialog: Boolean = false,

    // Error Handling
    val errorMessage: String? = null
)