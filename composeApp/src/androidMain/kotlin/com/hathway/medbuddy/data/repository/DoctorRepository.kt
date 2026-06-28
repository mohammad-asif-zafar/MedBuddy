package com.hathway.medbuddy.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import kotlinx.coroutines.tasks.await

class DoctorRepository : IDoctorRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val USERS_COLLECTION = "MedBuddy_users"
    private var cachedDoctorInfo: DoctorInfo? = null

    override suspend fun getDoctorInfo(userId: String): DoctorInfo {
        if (cachedDoctorInfo != null) return cachedDoctorInfo!!

        try {
            val snapshot = firestore
                .collection(USERS_COLLECTION)
                .document(userId)
                .get()
                .await()

            val info = DoctorInfo(
                doctorName = snapshot.getString("doctorName") ?: "",
                doctorType = snapshot.getString("doctorType") ?: "",
                speciality = snapshot.getString("speciality") ?: "",
                hospital = snapshot.getString("hospital") ?: "",
                nextAppointment = snapshot.getString("nextAppointment") ?: ""
            )
            cachedDoctorInfo = info
            return info
        } catch (e: Exception) {
            return DoctorInfo("", "", "", "", "")
        }
    }

    override suspend fun saveDoctorInfo(userId: String, doctorInfo: DoctorInfo) {
        cachedDoctorInfo = doctorInfo
        firestore
            .collection(USERS_COLLECTION)
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
