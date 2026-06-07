package com.hathway.medbuddy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.hathway.medbuddy.google_manager.GoogleAuthUiClient
import com.hathway.medbuddy.profile.setup.LoadingScreen
import com.hathway.medbuddy.profile.setup.LoginScreen
import com.hathway.medbuddy.repository.GlucoseRepository
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private lateinit var googleAuthUiClient: GoogleAuthUiClient

    private val authState = mutableStateOf<AuthState>(AuthState.Login)

    private val errorMessage = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleAuthUiClient = GoogleAuthUiClient(this)

        // Auto Login
        if (FirebaseManager.auth.currentUser != null) {

            authState.value = AuthState.Loading

            lifecycleScope.launch {

                try {

                    FirebaseSyncService(this@MainActivity).createUserIfNotExists()

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

                    App(repository)
                }
            }
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