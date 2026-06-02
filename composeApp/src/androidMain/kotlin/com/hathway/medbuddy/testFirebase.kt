package com.hathway.medbuddy

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun testFirestore() {

    val db = FirebaseFirestore.getInstance()

    db.collection("test")
        .add(
            mapOf(
                "name" to "MedBuddy",
                "value" to 145
            )
        )
        .addOnSuccessListener {
            Log.d("Firestore", "SUCCESS")
        }
        .addOnFailureListener {
            Log.e("Firestore", "FAILED", it)
        }
}

fun createUserProfile() {

    val user = FirebaseAuth.getInstance().currentUser ?: return

    FirebaseFirestore.getInstance()
        .collection("Zaf")
        .document(user.uid)
        .set(
            mapOf(
                "name" to (user.displayName ?: ""),
                "email" to (user.email ?: ""),
                "createdAt" to System.currentTimeMillis()
            )
        )
        .addOnSuccessListener {
            Log.d("Firestore", "User profile created")
        }
        .addOnFailureListener {
            Log.e("Firestore", "Failed", it)
        }
}