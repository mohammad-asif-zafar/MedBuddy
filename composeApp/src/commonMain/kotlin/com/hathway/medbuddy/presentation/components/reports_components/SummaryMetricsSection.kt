package com.hathway.medbuddy.presentation.components.reports_components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.Error
import com.hathway.medbuddy.presentation.theme.Primary
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.*

@Composable
fun SummaryMetricsSection(
    modifier: Modifier = Modifier,
    avgGlucose: Int,
    hba1c: Double,
    timeInRange: Int,
    totalReadings: Int
) {

    // ✅ Completely Dynamic Color Engines linked straight to the active Theme Palette tokens
    val healthyColor = Primary
    val warningColor = Error

    // ✅ Dynamic Color Engines based on health thresholds
    val hba1cColor = if (hba1c >= 5.7) warningColor else healthyColor
    val tirColor = if (timeInRange >= 70) healthyColor else warningColor
    val glucoseColor = if (avgGlucose > 130) warningColor else healthyColor


    Card(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.summary_title_days),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))

            // First Row: Glucose and HbA1c
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryMiniCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.avg_glucose_title),
                    value = "$avgGlucose",
                    subValue = stringResource(Res.string.avg_glucose_unit),
                    valueColor = glucoseColor, // Dynamic
                    icon = Icons.Default.WaterDrop
                )
                SummaryMiniCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.hba1c_title),
                    value = "$hba1c%",
                    subValue = stringResource(Res.string.hba1c_estimate),
                    valueColor = hba1cColor, // Dynamic threshold color
                    icon = Icons.Default.Bloodtype
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Second Row: Time in Range and Total Records
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryMiniCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.time_in_range_title),
                    value = "$timeInRange%",
                    subValue = "",
                    valueColor = tirColor, // Dynamic threshold color
                    icon = Icons.Default.Adjust
                )
                SummaryMiniCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(Res.string.total_readings_title),
                    value = "$totalReadings",
                    subValue = "",
                    valueColor = MaterialTheme.colorScheme.onSurface,
                    icon = Icons.Default.Assignment
                )
            }
        }
    }
}

// ==================== PREVIEW ENGINE (TESTING THRESHOLDS) ====================

@Preview(name = "Healthy Metrics Profile")
@Composable
fun SummaryMetricsHealthyPreview() {
    MedBuddyMockTheme(darkTheme = true) {
        SummaryMetricsSection(
            avgGlucose = 112, hba1c = 5.4,       // Under 5.7 -> Will render Green
            timeInRange = 92,  // Over 70% -> Will render Green
            totalReadings = 145
        )
    }
}

@Preview(name = "Warning Metrics Profile")
@Composable
fun SummaryMetricsWarningPreview() {
    MedBuddyMockTheme(darkTheme = false) {
        SummaryMetricsSection(
            avgGlucose = 154, hba1c = 7.1,       // Over 5.7 -> Will flip to Red
            timeInRange = 58,  // Under 70% -> Will flip to Red
            totalReadings = 94
        )
    }
}

@Composable
private fun MedBuddyMockTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val systemColorScheme = if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            surfaceVariant = Color(0xFF2B2B28),
            onSurface = Color(0xFFF7F7EE),
            onSurfaceVariant = Color(0xFFE5E5DC)
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            surfaceVariant = Color(0xFFF7F7EE), // Your warm cream color
            onSurface = Color(0xFF1A1A17), onSurfaceVariant = Color(0xFF42423E)
        )
    }
    MaterialTheme(colorScheme = systemColorScheme, content = content)
}
