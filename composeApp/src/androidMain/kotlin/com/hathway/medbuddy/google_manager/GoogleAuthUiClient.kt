package com.hathway.medbuddy.google_manager

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.hathway.medbuddy.R
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context
) {

    private val auth = FirebaseAuth.getInstance()

    fun getSignInIntent(): Intent {

        val gso = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        )
            .requestIdToken(
                context.getString(R.string.default_web_client_id)
            )
            .requestEmail()
            .build()

        return GoogleSignIn.getClient(
            context,
            gso
        ).signInIntent
    }

    suspend fun signInWithGoogle(
        account: GoogleSignInAccount
    ): FirebaseUser? {

        val credential = GoogleAuthProvider.getCredential(
            account.idToken,
            null
        )

        return auth.signInWithCredential(
            credential
        ).await().user
    }

    fun signOut() {
        auth.signOut()
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.DEFAULT_SIGN_IN
        ).signOut()
    }
}