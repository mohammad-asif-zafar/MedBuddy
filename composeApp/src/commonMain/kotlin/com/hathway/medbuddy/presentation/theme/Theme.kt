package com.hathway.medbuddy.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.hathway.medbuddy.ThemeMode

// ========================
// Color Schemes
// ========================
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnPrimaryContainer,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = OnSecondaryContainer,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Border,
    errorContainer = DangerContainer
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF42E3D0),
    onPrimary = Color(0xFF003731),
    primaryContainer = Color(0xFF005047),
    onPrimaryContainer = Color(0xFF6FFFEF),
    secondary = Color(0xFFB2CBD0),
    onSecondary = Color(0xFF1E3538),
    secondaryContainer = Color(0xFF354B4F),
    onSecondaryContainer = Color(0xFFCDE7EC),
    background = Color(0xFF0B141A),
    onBackground = Color(0xFFE1E3E5),
    surface = Color(0xFF111C24),
    onSurface = Color(0xFFE1E3E5),
    surfaceVariant = Color(0xFF1F2A31),
    onSurfaceVariant = Color(0xFFBEC8CC),
    outline = Color(0xFF4C565A),
    error = Color(0xFFFA8072),
    onError = Color(0xFF540003),
    errorContainer = Color(0xFF740007)
)

@Composable
fun MedBuddyTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM, content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme, content = content
    )
}
