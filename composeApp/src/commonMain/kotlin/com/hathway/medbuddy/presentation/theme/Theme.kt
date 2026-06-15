package com.hathway.medbuddy.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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

    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,

    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,

    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,

    outline = Border
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5CC17D),
    onPrimary = Color(0xFF06210F),

    secondary = Color(0xFF8AA694),
    onSecondary = Color(0xFF0E1611),

    background = Color(0xFF0F1720),
    onBackground = Color(0xFFF5F5F3),

    surface = Color(0xFF18212B),
    onSurface = Color(0xFFF5F5F3),

    primaryContainer = Color(0xFF183A28),
    onPrimaryContainer = Color(0xFFD8F4E1),

    secondaryContainer = Color(0xFF1F2B24),
    onSecondaryContainer = Color(0xFFD7E6DB),

    surfaceVariant = Color(0xFF24303A),
    onSurfaceVariant = Color(0xFFAAB4BE),

    outline = Color(0xFF33404B),

    error = Color(0xFFF87171),
    onError = Color(0xFF2B0A0A)
)

@Composable
fun MedBuddyTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}