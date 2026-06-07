package com.hathway.medbuddy

expect object FirebaseManager {
    val currentUser: CurrentUser?
}

data class CurrentUser(
    val displayName: String?,
    val email: String?,
    val photoUrl: String?
)
