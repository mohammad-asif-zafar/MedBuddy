package com.hathway.medbuddy.profile.setup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color

@Composable
fun LoadingScreen() {

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator()

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Loading MedBuddy..."
            )
        }
    }
}


@Composable
fun LoginScreen(
    errorMessage: String?, onGoogleSignInClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🩺 MedBuddy", style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Manage your diabetes with confidence",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        errorMessage?.let {

            Text(
                text = it, color = Color.Red
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }

        Button(
            onClick = onGoogleSignInClick, modifier = Modifier.fillMaxWidth()
        ) {

            Text("Sign in with Google")
        }
    }
}

