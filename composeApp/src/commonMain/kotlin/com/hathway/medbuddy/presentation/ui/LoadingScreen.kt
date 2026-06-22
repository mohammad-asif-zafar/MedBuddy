package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme

@Composable
fun LoadingScreen(onLoadingFinished: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        delay(3000)
        onLoadingFinished()
    }

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
                text = stringResource(Res.string.loading_medbuddy),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = stringResource(Res.string.preparing_dashboard),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


// Light Theme Preview for Loading Screen
@Preview(name = "Loading Screen - Light", showBackground = true)
@Composable
fun LoadingScreenLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        LoadingScreen()
    }
}

// Dark Theme Preview for Loading Screen
@Preview(name = "Loading Screen - Dark", showBackground = true)
@Composable
fun LoadingScreenDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        LoadingScreen()
    }
}
