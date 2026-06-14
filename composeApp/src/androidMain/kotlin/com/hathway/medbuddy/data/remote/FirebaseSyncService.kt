package com.hathway.medbuddy.data.remote

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hathway.medbuddy.FirebaseManager
import com.hathway.medbuddy.domain.model.GlucoseRecord
import kotlinx.coroutines.tasks.await

class FirebaseSyncService(private val context: Context) {

    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = "MedBuddy_users"
    suspend fun syncRecordToFirebase(record: GlucoseRecord): Boolean {
        return try {
            val userId = getCurrentUserId()
            if (userId.isEmpty()) {
                Log.e("FirebaseSyncService", "User not authenticated")
                return false
            }

            val recordData = hashMapOf(
                "value" to getGlucoseValue(record),
                "date" to record.date,
                "time" to record.time,
                "mealType" to record.mealType,
                "notes" to record.notes,
                "createdAt" to record.createdAt,
                "beforeBreakfast" to record.beforeBreakfast,
                "afterBreakfast" to record.afterBreakfast,
                "beforeLunch" to record.beforeLunch,
                "afterLunch" to record.afterLunch,
                "beforeDinner" to record.beforeDinner,
                "afterDinner" to record.afterDinner,
                "bedtime" to record.bedtime,
            )

            firestore.collection(usersCollection)
                .document(userId)
                .collection("glucose_records")
                .add(recordData)
                .await()

            Log.d("FirebaseSyncService", "Record synced to Firebase successfully")
            true
        } catch (e: Exception) {
            Log.e("FirebaseSyncService", "Failed to sync record to Firebase", e)
            false
        }
    }

    suspend fun syncAllRecordsToFirebase(records: List<GlucoseRecord>): Int {
        var successCount = 0
        records.forEach { record ->
            if (syncRecordToFirebase(record)) {
                successCount++
            }
        }
        return successCount
    }

    suspend fun fetchRecordsFromFirebase(): List<GlucoseRecord> {
        return try {

            val userId = getCurrentUserId()

            if (userId.isEmpty()) {
                Log.e("FirebaseSyncService", "User not authenticated")
                return emptyList()
            }

            // Create user profile if needed
            createUserIfNotExists()

            val snapshot = firestore
                .collection(usersCollection)
                .document(userId)
                .collection("glucose_records")
                .get()
                .await()

            val records = snapshot.documents.mapNotNull { doc ->
                try {

                    GlucoseRecord(
                        id = doc.id,
                        date = doc.getString("date") ?: "",
                        beforeBreakfast = doc.getLong("beforeBreakfast")?.toInt(),
                        afterBreakfast = doc.getLong("afterBreakfast")?.toInt(),
                        beforeLunch = doc.getLong("beforeLunch")?.toInt(),
                        afterLunch = doc.getLong("afterLunch")?.toInt(),
                        beforeDinner = doc.getLong("beforeDinner")?.toInt(),
                        afterDinner = doc.getLong("afterDinner")?.toInt(),
                        bedtime = doc.getLong("bedtime")?.toInt(),
                        time = doc.getString("time") ?: "",
                        mealType = doc.getString("mealType") ?: "",
                        notes = doc.getString("notes") ?: "",
                        createdAt = doc.getLong("createdAt")
                            ?: System.currentTimeMillis()
                    )

                } catch (e: Exception) {
                    Log.e(
                        "FirebaseSyncService",
                        "Failed to parse record",
                        e
                    )
                    null
                }
            }

            Log.d(
                "FirebaseSyncService",
                "Fetched ${records.size} records from Firebase"
            )

            records

        } catch (e: Exception) {

            Log.e(
                "FirebaseSyncService",
                "Failed to fetch records from Firebase",
                e
            )

            emptyList()
        }
    }

    suspend fun createUserIfNotExists() {
        val userId = getCurrentUserId()

        if (userId.isEmpty()) return

        val userRef = firestore.collection(usersCollection)
            .document(userId)

        val snapshot = userRef.get().await()
        val prefs = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

        if (!snapshot.exists()) {

            val user = FirebaseManager.auth.currentUser

            val userData = mapOf(
                "name" to (user?.displayName ?: ""),
                "email" to (user?.email ?: ""),
                "photoUrl" to (user?.photoUrl?.toString() ?: ""),
                "createdAt" to System.currentTimeMillis()
            )

            userRef.set(userData).await()

            // Save to local prefs
            prefs.edit().apply {
                putString("name", user?.displayName ?: "")
                putString("photoUrl", user?.photoUrl?.toString() ?: "")
                apply()
            }
        } else {
            // User exists, sync Firestore data to local prefs
            prefs.edit().apply {
                putString("name", snapshot.getString("name"))
                putString("age", snapshot.getString("age"))
                putString("weight", snapshot.getString("weight"))
                putString("bloodType", snapshot.getString("bloodType"))
                putString("photoUrl", snapshot.getString("photoUrl"))
                apply()
            }
        }
    }
    private fun getCurrentUserId(): String {
        return FirebaseManager.auth.currentUser?.uid ?: ""
    }

    private fun getGlucoseValue(record: GlucoseRecord): Int? {
        return record.beforeBreakfast
            ?: record.afterBreakfast
            ?: record.beforeLunch
            ?: record.afterLunch
            ?: record.beforeDinner
            ?: record.afterDinner
            ?: record.bedtime
    }
}
