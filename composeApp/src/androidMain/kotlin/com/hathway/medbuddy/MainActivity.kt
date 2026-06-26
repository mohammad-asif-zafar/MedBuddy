package com.hathway.medbuddy

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.hathway.medbuddy.google_manager.GoogleAuthUiClient
import com.hathway.medbuddy.data.remote.FirebaseSyncService
import com.hathway.medbuddy.data.repository.DoctorRepository
import com.hathway.medbuddy.data.repository.GlucoseRepository
import com.google.firebase.messaging.FirebaseMessaging
import com.hathway.medbuddy.presentation.navigation.NavigationDestination
import androidx.compose.runtime.key
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import com.hathway.medbuddy.LanguageManager
import java.util.Locale
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

    private lateinit var googleAuthUiClient: GoogleAuthUiClient

    private val deepLinkDestination = mutableStateOf(NavigationDestination.SPLASH)
    private val repository = mutableStateOf<GlucoseRepository?>(null)
    private val doctorRepository = mutableStateOf<DoctorRepository?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        handleIntent(intent)
        askNotificationPermission()

        googleAuthUiClient = GoogleAuthUiClient(this)

        // Auto Login and Repository Initialization
        if (FirebaseManager.auth.currentUser != null) {
            initRepositories()
        }

        setContent {
            val language by LanguageManager.language.collectAsState()

            LaunchedEffect(language) {
                val locale = Locale.forLanguageTag(language.code)
                Locale.setDefault(locale)
                val resources = this@MainActivity.resources
                val configuration = resources.configuration
                configuration.setLocale(locale)
                this@MainActivity.createConfigurationContext(configuration)
                resources.updateConfiguration(configuration, resources.displayMetrics)
            }

            key(language) {
                App(
                    repository = repository.value,
                    doctorRepository = doctorRepository.value,
                    initialDestination = deepLinkDestination.value,
                    onGoogleSignInClick = {
                        launcher.launch(googleAuthUiClient.getSignInIntent())
                    }
                )
            }
        }
    }

    private fun initRepositories() {
        repository.value = GlucoseRepository(this)
        doctorRepository.value = DoctorRepository()

        lifecycleScope.launch {
            try {
                FirebaseSyncService(this@MainActivity).createUserIfNotExists()
                syncFcmToken()
            } catch (e: Exception) {
                Log.e(TAG, "Initialization Failed", e)
            }
        }
    }

    override fun onNewIntent(intent: android.content.Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: android.content.Intent) {
        val target = intent.getStringExtra("navigate_to")
        if (target == "notifications") {
            deepLinkDestination.value = NavigationDestination.NOTIFICATIONS
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
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        lifecycleScope.launch {
            try {
                val account = task.getResult(ApiException::class.java)
                val user = googleAuthUiClient.signInWithGoogle(account)
                if (user != null) {
                    initRepositories()
                    Log.d(TAG, "Google Sign-In Success")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Google Sign-In Failed", e)
            }
        }
    }
}
