package com.hathway.medbuddy

import android.annotation.SuppressLint
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hathway.medbuddy.domain.model.DoctorInfo
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.SetOptions

actual object FirebaseManager {

    val auth = FirebaseAuth.getInstance()
    @SuppressLint("StaticFieldLeak")
    val firestore = FirebaseFirestore.getInstance()
    val storage = FirebaseStorage.getInstance("gs://medbuddy-4873d.firebasestorage.app")

    private val context: Context
        get() = FirebaseApp.getInstance().applicationContext

    private val prefs by lazy {
        context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    }

    actual val currentUser: CurrentUser?
        get() = auth.currentUser?.let {
            CurrentUser(
                uid = it.uid,
                displayName = prefs.getString("name", it.displayName),
                email = it.email,
                photoUrl = prefs.getString("photoUrl", it.photoUrl?.toString()),
                age = prefs.getString("age", null),
                weight = prefs.getString("weight", null),
                bloodType = prefs.getString("bloodType", null)
            )
        }

    actual fun signOut() {
        auth.signOut()
        prefs.edit().clear().apply()
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

    actual suspend fun getUserProfile(userId: String): CurrentUser? {
        return try {
            val snapshot = firestore
                .collection("MedBuddy_users")
                .document(userId)
                .get()
                .await()

            if (!snapshot.exists()) return currentUser

            val name = snapshot.getString("name")
            val age = snapshot.getString("age")
            val weight = snapshot.getString("weight")
            val bloodType = snapshot.getString("bloodType")
            val photoUrl = snapshot.getString("photoUrl")

            // Save locally
            prefs.edit().apply {
                putString("name", name)
                putString("age", age)
                putString("weight", weight)
                putString("bloodType", bloodType)
                putString("photoUrl", photoUrl)
                apply()
            }

            auth.currentUser?.let {
                CurrentUser(
                    uid = it.uid,
                    displayName = name ?: it.displayName,
                    email = it.email,
                    photoUrl = photoUrl ?: it.photoUrl?.toString(),
                    age = age,
                    weight = weight,
                    bloodType = bloodType
                )
            }
        } catch (e: Exception) {
            currentUser
        }
    }

    actual suspend fun saveUserProfile(
        userId: String,
        name: String,
        age: String,
        weight: String,
        bloodType: String
    ) {
        // Save to Firebase
        firestore
            .collection("MedBuddy_users")
            .document(userId)
            .set(
                mapOf(
                    "name" to name,
                    "age" to age,
                    "weight" to weight,
                    "bloodType" to bloodType
                ),
                SetOptions.merge()
            )
            .await()

        // Save locally
        prefs.edit().apply {
            putString("name", name)
            putString("age", age)
            putString("weight", weight)
            putString("bloodType", bloodType)
            apply()
        }
    }

    actual suspend fun updateProfilePicture(userId: String, imageBytes: ByteArray): String? {
        return try {
            val ref = storage.reference.child("profile_pictures/$userId.jpg")
            ref.putBytes(imageBytes).await()
            val downloadUrl = ref.downloadUrl.await().toString()

            // Update Firestore
            firestore.collection("MedBuddy_users").document(userId)
                .update("photoUrl", downloadUrl).await()

            // Update Local
            prefs.edit().putString("photoUrl", downloadUrl).apply()

            downloadUrl
        } catch (e: Exception) {
            null
        }
    }

    actual suspend fun updateFcmToken(userId: String, token: String) {
        try {
            firestore.collection("MedBuddy_users")
                .document(userId)
                .update("fcmToken", token)
                .await()
        } catch (e: Exception) {
            // Document might not exist yet, or other error
        }
    }

    actual fun getThemeMode(): ThemeMode {
        val mode = prefs.getString("theme_mode", ThemeMode.SYSTEM.name)
        return try {
            ThemeMode.valueOf(mode!!)
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }

    actual fun setThemeMode(mode: ThemeMode) {
        prefs.edit().putString("theme_mode", mode.name).apply()
    }
}
