package com.hathway.medbuddy

import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.model.Language

expect object FirebaseManager {
    val currentUser: CurrentUser?
    fun signOut()
    suspend fun getDoctorInfo(userId: String): DoctorInfo
    suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo)
    suspend fun getUserProfile(userId: String): CurrentUser?
    suspend fun saveUserProfile(userId: String, name: String, age: String, weight: String, bloodType: String)
    suspend fun updateProfilePicture(userId: String, imageBytes: ByteArray): String?
    suspend fun updateFcmToken(userId: String, token: String)
    suspend fun deleteAccount()
    fun getThemeMode(): ThemeMode
    fun setThemeMode(mode: ThemeMode)
    fun getLanguage(): Language
    fun setLanguage(language: Language)
}

enum class ThemeMode {
    LIGHT, DARK, SYSTEM
}

data class CurrentUser(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?,
    val age: String?,
    val weight: String?,
    val bloodType: String?
)
