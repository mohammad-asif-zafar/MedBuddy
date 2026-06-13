package com.hathway.medbuddy.profile.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.medbuddy_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoadingScreen() {

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Surface(
                shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer
            ) {

                Image(
                    painter = painterResource(Res.drawable.medbuddy_logo),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Loading MedBuddy",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Preparing your glucose dashboard",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun LoginScreen(
    errorMessage: String?, onGoogleSignInClick: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Surface(
                shape = RoundedCornerShape(28.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {

                Image(
                    painter = painterResource(Res.drawable.medbuddy_logo),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "MedBuddy",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Smart Glucose Care",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Track glucose levels, monitor trends and stay in control of your health.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            errorMessage?.let {

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {

                    Text(
                        text = it,
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }

            Button(
                onClick = onGoogleSignInClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                Text(
                    text = "Continue with Google", style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Secure sign-in powered by Google",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
