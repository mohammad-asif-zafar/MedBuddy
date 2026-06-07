package com.hathway.medbuddy

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

actual object FirebaseManager {

    val auth = FirebaseAuth.getInstance()

    val firestore = FirebaseFirestore.getInstance()

    val storage = FirebaseStorage.getInstance()

    actual val currentUser: CurrentUser?
        get() = auth.currentUser?.let {
            CurrentUser(
                displayName = it.displayName,
                email = it.email,
                photoUrl = it.photoUrl?.toString()
            )
        }
}

