package com.hathway.medbuddy

import android.util.Log
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