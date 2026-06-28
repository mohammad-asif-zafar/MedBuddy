package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.components.app_theme.GoogleSignInButton
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.medbuddy
import medbuddy.composeapp.generated.resources.medbuddy_logo
import medbuddy.composeapp.generated.resources.smart_glucose_care
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginScreen(
    errorMessage: String?, onGoogleSignInClick: () -> Unit
) {
    val backgroundColor = Color(0xFFF9FDFD)
    val primaryColor = Color(0xFF00897B)
    val secondaryColor = Color(0xFFE0F2F1)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Background decorative elements (waves/blobs)
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Large bottom wavy blob
            drawCircle(
                color = secondaryColor.copy(alpha = 0.6f),
                radius = 400.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(size.width * 0.5f, size.height * 1.1f)
            )
            // Extra soft accents
            drawCircle(
                color = secondaryColor.copy(alpha = 0.4f),
                radius = 200.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(0f, size.height * 0.8f)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.medbuddy_logo),
                contentDescription = null,
                modifier = Modifier.size(180.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.medbuddy),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                fontSize = 42.sp
            )

            // Accent underline
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(3.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(primaryColor.copy(alpha = 0.6f))
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.smart_glucose_care),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(64.dp))

            errorMessage?.let {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = it,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Using the shared GoogleSignInButton component
            GoogleSignInButton(
                onClick = onGoogleSignInClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        LoginScreen(errorMessage = null, onGoogleSignInClick = {})
    }
}
