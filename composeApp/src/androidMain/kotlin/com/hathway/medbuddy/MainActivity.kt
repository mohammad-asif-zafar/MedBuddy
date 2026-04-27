package com.hathway.medbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.repository.GlucoseRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
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