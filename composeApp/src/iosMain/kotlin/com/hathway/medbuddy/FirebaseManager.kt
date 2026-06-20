package com.hathway.medbuddy

import com.hathway.medbuddy.domain.model.DoctorInfo

actual object FirebaseManager {
    actual val currentUser: CurrentUser? = null
    actual fun signOut() {}
    actual suspend fun getDoctorInfo(userId: String): DoctorInfo = DoctorInfo()
    actual suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo) {}
    actual suspend fun getUserProfile(userId: String): CurrentUser? = null
    actual suspend fun saveUserProfile(userId: String, name: String, age: String, weight: String, bloodType: String) {}
    actual suspend fun updateProfilePicture(userId: String, imageBytes: ByteArray): String? = null
    actual suspend fun updateFcmToken(userId: String, token: String) {}
}
