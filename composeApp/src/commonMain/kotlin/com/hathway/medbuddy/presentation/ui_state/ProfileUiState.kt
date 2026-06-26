package com.hathway.medbuddy.presentation.ui_state

import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.model.Language

data class ProfileUiState(

    // User Information
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "",
    val age: String = "",
    val weight: String = "",
    val bloodType: String = "",

    // Doctor Information
    val doctorInfo: DoctorInfo = DoctorInfo(),

    // UI State
    val isLoading: Boolean = false,
    val showDoctorDialog: Boolean = false,

    // Error Handling
    val errorMessage: String? = null,

    val showProfileDialog: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val showThemeDialog: Boolean = false,
    val language: Language = Language.ENGLISH,
    val showLanguageDialog: Boolean = false
)