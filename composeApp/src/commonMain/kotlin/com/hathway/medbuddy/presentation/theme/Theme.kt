package com.hathway.medbuddy.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hathway.medbuddy.ThemeMode

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    background = LightBackground,
    onBackground = OnBackground,
    surface = Color.White,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Border,
    errorContainer = DangerContainer
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF5CC17D),
    onPrimary = Color(0xFF06210F),
    primaryContainer = Color(0xFF183A28),
    onPrimaryContainer = Color(0xFFD8F4E1),
    secondary = Color(0xFF8AA694),
    onSecondary = Color(0xFF0E1611),
    secondaryContainer = Color(0xFF1F2B24),
    onSecondaryContainer = Color(0xFFD7E6DB),
    background = Color(0xFF0F1720),
    onBackground = Color(0xFFF5F5F3),
    surface = Color(0xFF18212B),
    onSurface = Color(0xFFF5F5F3),
    surfaceVariant = Color(0xFF24303A),
    onSurfaceVariant = Color(0xFFAAB4BE),
    outline = Color(0xFF33404B),
    error = Color(0xFFF87171),
    onError = Color(0xFF2B0A0A),
    errorContainer = Color(0xFF4C1C1C)
)

@Composable
fun MedBuddyTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}
