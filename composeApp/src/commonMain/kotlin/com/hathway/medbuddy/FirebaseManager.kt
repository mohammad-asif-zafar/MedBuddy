package com.hathway.medbuddy

import com.hathway.medbuddy.domain.model.DoctorInfo

expect object FirebaseManager {
    val currentUser: CurrentUser?
    fun signOut()
    suspend fun getDoctorInfo(userId: String): DoctorInfo
    suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo)
    suspend fun getUserProfile(userId: String): CurrentUser?
    suspend fun saveUserProfile(userId: String, name: String, age: String, weight: String, bloodType: String)
    suspend fun updateProfilePicture(userId: String, imageBytes: ByteArray): String?
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
