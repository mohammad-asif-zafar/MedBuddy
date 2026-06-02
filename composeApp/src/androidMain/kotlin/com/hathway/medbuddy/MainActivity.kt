package com.hathway.medbuddy

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import com.hathway.medbuddy.repository.GlucoseRepository

class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        FirebaseManager.auth.signInAnonymously()
            .addOnSuccessListener {
                Log.e(TAG, "Firebase Login Success")

                testFirestore()
            }
        super.onCreate(savedInstanceState)

        setContent {
            val repository = GlucoseRepository(this)
            App(repository = repository)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}