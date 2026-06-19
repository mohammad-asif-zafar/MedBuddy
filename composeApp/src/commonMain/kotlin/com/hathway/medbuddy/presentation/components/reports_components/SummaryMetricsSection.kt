package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.avg_glucose_title
import medbuddy.composeapp.generated.resources.avg_glucose_unit
import medbuddy.composeapp.generated.resources.hba1c_estimate
import medbuddy.composeapp.generated.resources.hba1c_title
import medbuddy.composeapp.generated.resources.summary_title_days
import medbuddy.composeapp.generated.resources.time_in_range_title
import medbuddy.composeapp.generated.resources.total_readings_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun SummaryMetricsSection(
    avgGlucose: Int,
    hba1c: Double,
    timeInRange: Int,
    totalReadings: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    titleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    healthyColor: Color = MaterialTheme.colorScheme.primary,
    warningColor: Color = MaterialTheme.colorScheme.error,
    neutralColor: Color = MaterialTheme.colorScheme.onSurface,
    selectedFilterDays: String // 7 Days 90 Days 30 Days
) {

    // Animation state triggers
    var isVisibleRow1 by remember { mutableStateOf(false) }
    var isVisibleRow2 by remember { mutableStateOf(false) }

    // Sequential load engine
    LaunchedEffect(Unit) {
        delay(100) // Small initial delay to let the screen mount
        isVisibleRow1 = true
        delay(150) // Staggered transition effect for Row 2
        isVisibleRow2 = true
    }

    val hba1cColor = if (hba1c >= 5.7) warningColor else healthyColor
    val tirColor = if (timeInRange >= 70) healthyColor else warningColor
    val glucoseColor = if (avgGlucose > 130) warningColor else healthyColor

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = stringResource(Res.string.summary_title_days, selectedFilterDays),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor,
                modifier = Modifier.padding(start = 2.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // 🎬 Row 1 Fade & Slide Animation Wrapper
            AnimatedVisibility(
                visible = isVisibleRow1,
                enter = fadeIn(animationSpec = tween(durationMillis = 400)) + slideInVertically(
                    animationSpec = tween(durationMillis = 400)
                ) { it / 4 }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.avg_glucose_title),
                        value = "$avgGlucose",
                        subValue = stringResource(Res.string.avg_glucose_unit),
                        valueColor = glucoseColor,
                        icon = Icons.Default.WaterDrop
                    )
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.hba1c_title),
                        value = "${(hba1c * 10).toInt() / 10.0}%", // Clean format
                        subValue = stringResource(Res.string.hba1c_estimate),
                        valueColor = hba1cColor,
                        icon = Icons.Default.Bloodtype
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 🎬 Row 2 Fade & Slide Animation Wrapper (Staggered)
            AnimatedVisibility(
                visible = isVisibleRow2,
                enter = fadeIn(animationSpec = tween(durationMillis = 400)) + slideInVertically(
                    animationSpec = tween(durationMillis = 400)
                ) { it / 4 }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.time_in_range_title),
                        value = "${timeInRange}%",
                        subValue = "-",
                        valueColor = tirColor,
                        icon = Icons.Default.Adjust
                    )
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.total_readings_title),
                        value = "$totalReadings",
                        subValue = "-",
                        valueColor = neutralColor,
                        icon = Icons.Default.Assignment
                    )
                }
            }
        }
    }
}


// ==================== PREVIEW ENGINE (TESTING THRESHOLDS) ====================

@Preview(name = "Healthy Metrics Profile")
@Composable
fun SummaryMetricsHealthyPreview() {
    MedBuddyTheme(darkTheme = true) {
        SummaryMetricsSection(
            avgGlucose = 112, hba1c = 5.4,       // Under 5.7 -> Will render Green
            timeInRange = 92,  // Over 70% -> Will render Green
            totalReadings = 145,
            selectedFilterDays = "7 Days",
        )
    }
}

@Composable
private fun MetricsGridPreviewTheme(
    isDark: Boolean = false, isCream: Boolean = false, content: @Composable () -> Unit
) {
    val colors = when {
        isCream -> lightColorScheme(
            surfaceVariant = Color(0xFFF7F7EE),      // Main light cream grid background
            surface = Color(0xFFFFFFFF),             // Elevation Card base fields
            onSurfaceVariant = Color(0xFF5A5950),    // Muted dark grey text
            onSurface = Color(0xFF2C3E50),           // Dark blue label text color
            primary = Color(0xFF2E7D32),             // Forest green health color
            error = Color(0xFFC62828)                // Clear warning crimson
        )

        isDark -> darkColorScheme(
            surfaceVariant = Color(0xFF1E1E1C),
            surface = Color(0xFF2B2B28),
            onSurfaceVariant = Color(0xFFB0B0AA),
            onSurface = Color(0xFF90CAF9),
            primary = Color(0xFF81C784),
            error = Color(0xFFE57373)
        )

        else -> lightColorScheme(
            surfaceVariant = Color(0xFFF5F5F5),
            surface = Color(0xFFFFFFFF),
            onSurfaceVariant = Color(0xFF757575),
            onSurface = Color(0xFF1976D2),
            primary = Color(0xFF388E3C),
            error = Color(0xFFD32F2F)
        )
    }
    MaterialTheme(colorScheme = colors, content = content)
}

@Preview
@Composable
fun SummaryGridMetricsCreamPreview() {
    MetricsGridPreviewTheme(isCream = true) {
        SummaryMetricsSection(
            avgGlucose = 124,
            hba1c = 5.9,
            timeInRange = 78,
            totalReadings = 142,
            selectedFilterDays = "30 Days", // ✅ Fixed parameter name matching signature
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun SummaryGridMetricsDarkPreview() {
    MetricsGridPreviewTheme(isDark = true) {
        SummaryMetricsSection(
            avgGlucose = 124,
            hba1c = 5.9,
            timeInRange = 78,
            totalReadings = 142,
            selectedFilterDays = "90 Days", // ✅ Fixed parameter name matching signature
            modifier = Modifier.padding(16.dp)
        )
    }
}
