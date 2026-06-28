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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
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
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    val glucoseColor = if (avgGlucose > 130) StatusLow else StatusInRange
    val hba1cColor = when {
        hba1c >= 6.5 -> StatusLow
        hba1c >= 5.7 -> StatusHigh
        else -> StatusInRange
    }
    val tirColor = if (timeInRange >= 70) StatusInRange else StatusLow

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
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
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(start = 2.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(animationSpec = tween(durationMillis = 500)) + slideInVertically(
                    animationSpec = tween(durationMillis = 500)
                ) { it / 6 }) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.avg_glucose_title),
                        value = "$avgGlucose",
                        subValue = stringResource(Res.string.avg_glucose_unit),
                        valueColor = glucoseColor,
                        icon = painterResource(Res.drawable.ic_glucose_pulse)
                    )

                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.hba1c_title),
                        value = "${(hba1c * 10).toInt() / 10.0}%",
                        subValue = stringResource(Res.string.hba1c_estimate),
                        valueColor = hba1cColor,
                        icon = painterResource(Res.drawable.ic_blood_drop_outline)
                    )

                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.time_in_range_title),
                        value = "$timeInRange%",
                        subValue = "",
                        valueColor = tirColor,
                        icon = painterResource(Res.drawable.ic_time_in_range)
                    )

                    SummaryMiniCard(
                        modifier = Modifier.weight(1f),
                        title = stringResource(Res.string.total_readings_title),
                        value = "$totalReadings",
                        subValue = "",
                        valueColor = MaterialTheme.colorScheme.primary,
                        icon = Icons.AutoMirrored.Filled.Assignment
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SummaryMetricsSectionPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        SummaryMetricsSection(
            avgGlucose = 115,
            hba1c = 5.7,
            timeInRange = 78,
            totalReadings = 120,
            selectedFilterDays = "7 Days"
        )
    }
}
