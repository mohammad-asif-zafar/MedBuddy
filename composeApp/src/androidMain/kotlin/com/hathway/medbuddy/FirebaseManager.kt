package com.hathway.medbuddy

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hathway.medbuddy.domain.model.DoctorInfo
import kotlinx.coroutines.tasks.await

import com.google.firebase.firestore.SetOptions

actual object FirebaseManager {

    val auth = FirebaseAuth.getInstance()

    val firestore = FirebaseFirestore.getInstance()

    val storage = FirebaseStorage.getInstance()

    actual val currentUser: CurrentUser?
        get() = auth.currentUser?.let {
            CurrentUser(
                uid = it.uid,
                displayName = it.displayName,
                email = it.email,
                photoUrl = it.photoUrl?.toString()
            )
        }

    actual fun signOut() {
        auth.signOut()
    }

    actual suspend fun getDoctorInfo(userId: String): DoctorInfo {
        val snapshot = firestore
            .collection("MedBuddy_users")
            .document(userId)
            .get()
            .await()

        return DoctorInfo(
            doctorName = snapshot.getString("doctorName") ?: "",
            doctorType = snapshot.getString("doctorType") ?: "",
            speciality = snapshot.getString("speciality") ?: "",
            hospital = snapshot.getString("hospital") ?: "",
            nextAppointment = snapshot.getString("nextAppointment") ?: ""
        )
    }

    actual suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo) {
        firestore
            .collection("MedBuddy_users")
            .document(userId)
            .set(
                mapOf(
                    "doctorName" to doctorInfo.doctorName,
                    "doctorType" to doctorInfo.doctorType,
                    "speciality" to doctorInfo.speciality,
                    "hospital" to doctorInfo.hospital,
                    "nextAppointment" to doctorInfo.nextAppointment
                ),
                SetOptions.merge()
            )
            .await()
    }
}
