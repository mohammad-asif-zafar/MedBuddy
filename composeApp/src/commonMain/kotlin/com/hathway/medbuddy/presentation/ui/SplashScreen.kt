package com.hathway.medbuddy.presentation.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.FirebaseManager
import com.hathway.medbuddy.ThemeManager
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import kotlinx.coroutines.delay
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.medbuddy
import medbuddy.composeapp.generated.resources.smart_glucose_care
import medbuddy.composeapp.generated.resources.splash_logo_dark
import medbuddy.composeapp.generated.resources.splash_logo_light
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    var progress by remember { mutableStateOf(0f) }
    val animatedProgress by animateFloatAsState(
        targetValue = progress, animationSpec = tween(durationMillis = 2000)
    )

    LaunchedEffect(Unit) {
        progress = 1f
        delay(1500)
        onSplashFinished()
    }

    // ADAPTIVE THEMING PALETTE: Pulls from your defined light/dark Material 3 Color Schemes
    val backgroundColor = MaterialTheme.colorScheme.background
    val brandTealColor = MaterialTheme.colorScheme.primary
    val subtextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val progressTrackColor = MaterialTheme.colorScheme.surfaceVariant
    val containerWaveColor = MaterialTheme.colorScheme.primaryContainer
    val themeMode by ThemeManager.themeMode.collectAsState()

    val splashLogoPainter = if (rememberIsDarkTheme(themeMode)) {
        painterResource(Res.drawable.splash_logo_dark)
    } else {
        painterResource(Res.drawable.splash_logo_light)
    }
    Box(
        modifier = Modifier.fillMaxSize().background(backgroundColor)
    ) {
        // Dynamic Ambient Wave Canvas: Adapts style based on active theme system
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // 1. Bottom Fill Wave Gradient
            val wavePathFilled = Path().apply {
                moveTo(0f, height * 0.62f)
                cubicTo(
                    width * 0.25f,
                    height * 0.58f,
                    width * 0.65f,
                    height * 0.82f,
                    width,
                    height * 0.72f
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }

            drawPath(
                path = wavePathFilled, brush = Brush.verticalGradient(
                    colors = listOf(
                        containerWaveColor.copy(alpha = 0.35f), backgroundColor.copy(alpha = 0.0f)
                    ), startY = height * 0.60f, endY = height
                )
            )

            // 2. Translucent Ambient Ridge Highlight Accent Line
            val waveOutlinePath = Path().apply {
                moveTo(0f, height * 0.62f)
                cubicTo(
                    width * 0.25f,
                    height * 0.58f,
                    width * 0.65f,
                    height * 0.82f,
                    width,
                    height * 0.72f
                )
            }

            drawPath(
                path = waveOutlinePath,
                color = brandTealColor.copy(alpha = 0.25f),
                style = Stroke(width = 2.5.dp.toPx())
            )
        }

        // Center Content Branding Block
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = splashLogoPainter,
                contentDescription = null,
                modifier = Modifier.size(190.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(Res.string.medbuddy),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = brandTealColor,
                fontSize = 44.sp,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(Res.string.smart_glucose_care), // "Track. Understand. Take Control."
                style = MaterialTheme.typography.bodyLarge,
                color = subtextColor,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                letterSpacing = 0.25.sp
            )
        }

        // Bottom Loading Section Indicator Area
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 70.dp)
                .padding(horizontal = 54.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Loading...",
                color = brandTealColor.copy(alpha = 0.85f),
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier.fillMaxWidth().height(5.dp),
                color = brandTealColor,
                trackColor = progressTrackColor,
                strokeCap = StrokeCap.Round
            )
        }
    }
}


@Composable
fun rememberIsDarkTheme(themeMode: ThemeMode): Boolean {
    return when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme() // Resolves Android/iOS system dark mode configurations safely
    }
}


// ─── LIGHT MODE SPLASH PREVIEW ───
@Preview
@Composable
fun SplashScreenLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        SplashScreen(onSplashFinished = {})
    }
}

// ─── DARK MODE SPLASH PREVIEW ───
@Preview
@Composable
fun SplashScreenDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        SplashScreen(onSplashFinished = {})
    }
}


