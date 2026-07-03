package com.hathway.medbuddy

import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.model.Language
import platform.Foundation.NSUserDefaults

actual object FirebaseManager {
    actual val currentUser: CurrentUser? = null
    actual fun signOut() {}
    actual suspend fun getDoctorInfo(userId: String): DoctorInfo = DoctorInfo()
    actual suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo) {}
    actual suspend fun getUserProfile(userId: String): CurrentUser? = null
    actual suspend fun saveUserProfile(userId: String, name: String, age: String, weight: String, bloodType: String) {}
    actual suspend fun updateProfilePicture(userId: String, imageBytes: ByteArray): String? = null
    actual suspend fun updateFcmToken(userId: String, token: String) {}
    actual suspend fun deleteAccount() {}

    actual fun getThemeMode(): ThemeMode {
        val mode = NSUserDefaults.standardUserDefaults.stringForKey("theme_mode") ?: ThemeMode.SYSTEM.name
        return try {
            ThemeMode.valueOf(mode)
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }

    actual fun setThemeMode(mode: ThemeMode) {
        NSUserDefaults.standardUserDefaults.setObject(mode.name, "theme_mode")
    }

    actual fun getLanguage(): Language {
        val code = NSUserDefaults.standardUserDefaults.stringForKey("language_code") ?: Language.ENGLISH.code
        return Language.entries.find { it.code == code } ?: Language.ENGLISH
    }

    actual fun setLanguage(language: Language) {
        NSUserDefaults.standardUserDefaults.setObject(language.code, "language_code")
    }
}
