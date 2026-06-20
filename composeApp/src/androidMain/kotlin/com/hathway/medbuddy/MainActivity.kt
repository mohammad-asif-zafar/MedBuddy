package com.hathway.medbuddy

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.hathway.medbuddy.google_manager.GoogleAuthUiClient
import com.hathway.medbuddy.presentation.ui.LoadingScreen
import com.hathway.medbuddy.presentation.ui.LoginScreen
import com.hathway.medbuddy.data.remote.FirebaseSyncService
import com.hathway.medbuddy.data.repository.DoctorRepository
import com.hathway.medbuddy.data.repository.GlucoseRepository
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private lateinit var googleAuthUiClient: GoogleAuthUiClient

    private val authState = mutableStateOf<AuthState>(AuthState.Login)

    private val errorMessage = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askNotificationPermission()

        googleAuthUiClient = GoogleAuthUiClient(this)

        // Listen for Auth State Changes (Handles Logout)
        FirebaseManager.auth.addAuthStateListener { auth ->
            if (auth.currentUser == null) {
                authState.value = AuthState.Login
            }
        }

        // Auto Login
        if (FirebaseManager.auth.currentUser != null) {

            authState.value = AuthState.Loading

            lifecycleScope.launch {

                try {

                    FirebaseSyncService(this@MainActivity).createUserIfNotExists()
                    syncFcmToken()

                    authState.value = AuthState.Home

                } catch (e: Exception) {

                    authState.value = AuthState.Login

                    Log.e(
                        TAG, "Auto Login Failed", e
                    )
                }
            }
        }

        setContent {

            when (authState.value) {

                AuthState.Login -> {
                    LoginScreen(
                        errorMessage = errorMessage.value, onGoogleSignInClick = {
                            errorMessage.value = null
                            launcher.launch(
                                googleAuthUiClient.getSignInIntent()
                            )
                        })
                }

                AuthState.Loading -> {
                    LoadingScreen()
                }

                AuthState.Home -> {

                    val repository = GlucoseRepository(this)
                    val doctorRepository = DoctorRepository()

                    App(repository, doctorRepository)
                }
            }
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(TAG, "Notification permission granted")
        } else {
            Log.w(TAG, "Notification permission denied")
        }
    }

    private suspend fun syncFcmToken() {
        try {
            val userId = FirebaseManager.auth.currentUser?.uid ?: return
            val token = FirebaseMessaging.getInstance().token.await()
            FirebaseManager.updateFcmToken(userId, token)
            Log.d(TAG, "FCM Token synced to Firestore")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to sync FCM Token", e)
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        authState.value = AuthState.Loading
        errorMessage.value = null

        val task = GoogleSignIn.getSignedInAccountFromIntent(
            result.data
        )

        lifecycleScope.launch {

            try {

                val account = task.getResult(
                    ApiException::class.java
                )

                val user = googleAuthUiClient.signInWithGoogle(account)

                if (user != null) {

                    FirebaseSyncService(
                        this@MainActivity
                    ).createUserIfNotExists()
                    syncFcmToken()

                    authState.value = AuthState.Home

                    Log.d(
                        TAG, "Google Sign-In Success"
                    )

                } else {

                    errorMessage.value = "Login failed"

                    authState.value = AuthState.Login
                }

            } catch (e: Exception) {

                errorMessage.value = e.localizedMessage ?: "Google Sign-In Failed"

                authState.value = AuthState.Login

                Log.e(
                    TAG, "Google Sign-In Failed", e
                )
            }
        }
    }
}