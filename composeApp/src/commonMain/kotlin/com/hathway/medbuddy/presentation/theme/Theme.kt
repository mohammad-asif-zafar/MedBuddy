package com.hathway.medbuddy.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Theme.kt
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,

    secondary = Secondary,
    onSecondary = OnSecondary,

    background = Background,
    onBackground = OnBackground,

    surface = Surface,
    onSurface = OnSurface,

    error = Error,
    onError = OnError,

    secondaryContainer = Color(0xFFCCFBF1),
    outline = Border,
    primaryContainer = Color(0xFFEFF6FF),
    onPrimaryContainer = Color(0xFF1E3A8A),

    surfaceVariant = Color(0xFFF1F5F9),
    onSurfaceVariant = Color(0xFF64748B),

    )

private val DarkColorScheme = darkColorScheme(
    primary = Primary, onPrimary = OnPrimary,

    secondary = Secondary, onSecondary = OnSecondary,

    background = Color(0xFF0F172A), onBackground = Color(0xFFF8FAFC),

    surface = Color(0xFF1E293B), onSurface = Color(0xFFF8FAFC),

    error = Error, onError = OnError
)

@Composable
fun MedBuddyTheme(
    darkTheme: Boolean = false, content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, content = content
    )
}