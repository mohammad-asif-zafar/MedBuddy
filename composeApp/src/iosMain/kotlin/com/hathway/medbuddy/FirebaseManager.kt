package com.hathway.medbuddy

import com.hathway.medbuddy.domain.model.DoctorInfo

actual object FirebaseManager {
    actual val currentUser: CurrentUser? = null
    actual fun signOut() {}
    actual suspend fun getDoctorInfo(userId: String): DoctorInfo = DoctorInfo()
    actual suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo) {}
}
