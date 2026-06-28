package com.hathway.medbuddy.presentation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ThemeManager
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun LoadingScreen() {
    // 1. Fetch current theme state directly via app-level ThemeManager state flow stream emissions
    val activeThemeMode by ThemeManager.themeMode.collectAsState()
    val isDark = rememberIsDarkTheme(activeThemeMode)

    // 2. Adaptive theme system architecture bindings
    val backgroundColor = MaterialTheme.colorScheme.background
    val primaryColor = MaterialTheme.colorScheme.primary
    val descriptiveTextColor = MaterialTheme.colorScheme.onSurfaceVariant

    // 3. Dynamic layout asset handling checks user preference overrides cleanly
    val loadingLogoPainter = if (isDark) {
        painterResource(Res.drawable.splash_logo_dark) // Reuses your clean dark asset
    } else {
        painterResource(Res.drawable.splash_logo_light)
    }

    Scaffold(
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            androidx.compose.foundation.Image(
                painter = loadingLogoPainter,
                contentDescription = null,
                modifier = Modifier.size(160.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.loading_medbuddy),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                fontSize = 40.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.preparing_health_data),
                style = MaterialTheme.typography.bodyLarge,
                color = descriptiveTextColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(60.dp))

            DottedCircularLoader(color = primaryColor)
        }
    }
}

@Composable
fun DottedCircularLoader(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    dotCount: Int = 12
) {
    val infiniteTransition = rememberInfiniteTransition(label = "LoaderLoop")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing), repeatMode = RepeatMode.Restart
        ), label = "RotationAngle"
    )

    Box(
        modifier = modifier.size(80.dp).graphicsLayer { rotationZ = rotation },
        contentAlignment = Alignment.Center
    ) {
        val radius = 30.dp

        val radiansFactor = ((2f * PI) / dotCount).toFloat()

        for (i in 0 until dotCount) {
            val angle = i * radiansFactor
            val alpha = (i + 1).toFloat() / dotCount

            Box(
                modifier = Modifier.offset(
                    x = (radius.value * cos(angle)).dp, y = (radius.value * sin(angle)).dp
                ).size(8.dp).clip(CircleShape).background(color.copy(alpha = alpha))
            )
        }
    }
}


@Preview
@Composable
fun LoadingScreenLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        LoadingScreen()
    }
}

@Preview
@Composable
fun LoadingScreenDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        LoadingScreen()
    }
}
