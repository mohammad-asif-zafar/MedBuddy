package com.hathway.medbuddy

import com.hathway.medbuddy.domain.model.DoctorInfo

expect object FirebaseManager {
    val currentUser: CurrentUser?
    fun signOut()
    suspend fun getDoctorInfo(userId: String): DoctorInfo
    suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo)
}

data class CurrentUser(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?
)
