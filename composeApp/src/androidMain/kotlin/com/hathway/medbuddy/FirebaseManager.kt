package com.hathway.medbuddy

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.hathway.medbuddy.domain.model.DoctorInfo
import com.hathway.medbuddy.domain.model.Language
import com.hathway.medbuddy.data.local.GlucoseDatabaseHelper
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.SetOptions

actual object FirebaseManager {

    val auth by lazy { FirebaseAuth.getInstance() }
    val firestore by lazy { FirebaseFirestore.getInstance() }
    val storage by lazy { FirebaseStorage.getInstance("gs://medbuddy-4873d.firebasestorage.app") }

    private val context: Context?
        get() = try {
            FirebaseApp.getInstance().applicationContext
        } catch (e: Exception) {
            null
        }

    private val prefs by lazy {
        context?.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    }

    actual val currentUser: CurrentUser?
        get() = try {
            auth.currentUser?.let {
                CurrentUser(
                    uid = it.uid,
                    displayName = prefs?.getString("name", it.displayName),
                    email = it.email,
                    photoUrl = prefs?.getString("photoUrl", it.photoUrl?.toString()),
                    age = prefs?.getString("age", null),
                    weight = prefs?.getString("weight", null),
                    bloodType = prefs?.getString("bloodType", null)
                )
            }
        } catch (e: Exception) {
            null
        }

    actual fun signOut() {
        try {
            auth.signOut()
            prefs?.edit()?.clear()?.apply()
        } catch (e: Exception) {
        }
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
            prefs?.edit()?.apply {
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
        prefs?.edit()?.apply {
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
            prefs?.edit()?.putString("photoUrl", downloadUrl)?.apply()

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

    actual suspend fun deleteAccount() {
        val user = auth.currentUser ?: return
        val userId = user.uid

        try {
            // 1. Delete Remote Firestore data (Sub-collections first)
            try {
                val glucoseRecords = firestore.collection("MedBuddy_users").document(userId)
                    .collection("glucose_records").get().await()
                for (doc in glucoseRecords.documents) {
                    doc.reference.delete().await()
                }
                firestore.collection("MedBuddy_users").document(userId).delete().await()
            } catch (e: Exception) {
                Log.e("FirebaseManager", "Error deleting Firestore data", e)
            }

            // 2. Delete Remote Storage data (profile picture if exists)
            try {
                storage.reference.child("profile_pictures/$userId.jpg").delete().await()
            } catch (e: Exception) {
                // Ignore 404 or other storage errors
                Log.d("FirebaseManager", "Storage deletion skipped or failed: ${e.message}")
            }

            // 3. IMPORTANT: Terminate Firestore to release local DB locks
            try {
                firestore.terminate().await()
                firestore.clearPersistence().await()
            } catch (e: Exception) {
                Log.e("FirebaseManager", "Error terminating Firestore", e)
            }

            // 4. Delete user from Firebase Auth (Do this before local wipe in case it fails)
            try {
                user.delete().await()
            } catch (e: Exception) {
                Log.e("FirebaseManager", "Error deleting Auth user", e)
                // If this fails (e.g. requires recent login), we might want to throw to notify user
                throw e
            }

            // 5. Clear all local data safely
            context?.let { ctx ->
                // Clear all SharedPreferences
                try {
                    val root = ctx.filesDir.parentFile
                    val sharedPrefsDir = java.io.File(root, "shared_prefs")
                    if (sharedPrefsDir.exists() && sharedPrefsDir.isDirectory) {
                        sharedPrefsDir.list()?.forEach { fileName ->
                            val prefName = fileName.replace(".xml", "")
                            ctx.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit().clear().apply()
                        }
                    }
                } catch (e: Exception) {
                    prefs?.edit()?.clear()?.apply()
                }

                // Clear all local databases
                try {
                    // Close our app's specific helper first
                    GlucoseDatabaseHelper(ctx).close()
                    
                    val root = ctx.filesDir.parentFile
                    val databasesDir = java.io.File(root, "databases")
                    if (databasesDir.exists() && databasesDir.isDirectory) {
                        val files = databasesDir.listFiles()
                        files?.forEach { file ->
                            // Use deleteDatabase for safety
                            ctx.deleteDatabase(file.name)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseManager", "Error clearing databases", e)
                }

                // Clear Cache
                try {
                    ctx.cacheDir.deleteRecursively()
                } catch (e: Exception) {
                }
                
                // Clear Internal Files
                try {
                    ctx.filesDir.deleteRecursively()
                } catch (e: Exception) {
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }

    actual fun getThemeMode(): ThemeMode {
        val mode = prefs?.getString("theme_mode", ThemeMode.SYSTEM.name) ?: ThemeMode.SYSTEM.name
        return try {
            ThemeMode.valueOf(mode)
        } catch (e: Exception) {
            ThemeMode.SYSTEM
        }
    }

    actual fun setThemeMode(mode: ThemeMode) {
        prefs?.edit()?.putString("theme_mode", mode.name)?.apply()
    }

    actual fun getLanguage(): Language {
        val code = prefs?.getString("language_code", Language.ENGLISH.code) ?: Language.ENGLISH.code
        return Language.entries.find { it.code == code } ?: Language.ENGLISH
    }

    actual fun setLanguage(language: Language) {
        prefs?.edit()?.putString("language_code", language.code)?.apply()
    }
}
