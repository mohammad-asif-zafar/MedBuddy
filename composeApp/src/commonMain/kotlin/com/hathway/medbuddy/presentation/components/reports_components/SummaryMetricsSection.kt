package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.*
import kotlinx.coroutines.delay
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SummaryMetricsSection(
    avgGlucose: Int,
    hba1c: Double,
    timeInRange: Int,
    totalReadings: Int,
    selectedFilterDays: String,
    modifier: Modifier = Modifier,
    titleColor: Color = OnBackground,
    healthyColor: Color = Success,
    warningColor: Color = Warning,
    dangerColor: Color = Danger,
    neutralColor: Color = Info
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    // Dynamic healthcare compliance logic using your actual theme state variables
    val glucoseColor = if (avgGlucose > 130) dangerColor else healthyColor
    val hba1cColor = when {
        hba1c >= 6.5 -> dangerColor
        hba1c >= 5.7 -> warningColor
        else -> healthyColor
    }
    val tirColor = if (timeInRange >= 70) healthyColor else dangerColor

    Card(
        modifier = modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MiniCardBackground
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = stringResource(Res.string.summary_title_days, selectedFilterDays),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = titleColor,
                modifier = Modifier.padding(start = 2.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            //  Combined grid fade animation wrapper block
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)) + slideInVertically(
                    animationSpec = tween(durationMillis = 500)
                ) { it / 6 }) {
                //  Modern 4-column horizontal configuration grid matching the image layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // 1. Average Glucose
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.avg_glucose_title),
                        value = "$avgGlucose",
                        subValue = stringResource(Res.string.avg_glucose_unit),
                        valueColor = glucoseColor,
                        icon = painterResource(Res.drawable.ic_glucose_pulse)
                    )

                    // 2. HbA1c
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.hba1c_title),
                        value = "${(hba1c * 10).toInt() / 10.0}%",
                        subValue = stringResource(Res.string.hba1c_estimate),
                        valueColor = hba1cColor,
                        icon = painterResource(Res.drawable.ic_blood_drop_outline)
                    )

                    // 3. Time In Range
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.time_in_range_title),
                        value = "$timeInRange%",
                        subValue = "",
                        valueColor = tirColor,
                        icon = painterResource(Res.drawable.ic_time_in_range)
                    )

                    // 4. Total Readings
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.total_readings_title),
                        value = "$totalReadings",
                        subValue = "",
                        valueColor = neutralColor,
                        icon = Icons.AutoMirrored.Filled.Assignment
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
