package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.Danger
import com.hathway.medbuddy.presentation.theme.Secondary
import com.hathway.medbuddy.presentation.theme.Success
import com.hathway.medbuddy.presentation.theme.Warning
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.current_glucose
import medbuddy.composeapp.generated.resources.glucose_unit
import medbuddy.composeapp.generated.resources.last_reading
import medbuddy.composeapp.generated.resources.reading_details
import medbuddy.composeapp.generated.resources.reading_value
import medbuddy.composeapp.generated.resources.status_high
import medbuddy.composeapp.generated.resources.status_in_range
import medbuddy.composeapp.generated.resources.status_low
import medbuddy.composeapp.generated.resources.trend_up
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs

@Composable
fun BloodGlucoseCard(
    average: Double,
    trend: Double,
    status: String,
    lastReading: Double,
    lastReadingTime: String,
    lastMealType: String,
    onClick: () -> Unit = {}
) {

    val statusColor = when (status.lowercase()) {
        stringResource(Res.string.status_high) -> Warning
        stringResource(Res.string.status_low) -> Danger
        stringResource(Res.string.status_in_range) -> Success
        else -> Success
    }

    val trendArrow = if (trend >= 0) "↑" else "↓"

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Outlined.MonitorHeart,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(Res.string.current_glucose),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Value
            Text(
                text = average.toInt().toString(),
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = stringResource(Res.string.glucose_unit),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Status Chip
            Surface(
                color = statusColor.copy(alpha = 0.15f), shape = RoundedCornerShape(50)
            ) {

                Text(
                    text = status, modifier = Modifier.padding(
                        horizontal = 12.dp, vertical = 6.dp
                    ), color = statusColor, fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Trend
            Text(
                text = stringResource(
                    Res.string.trend_up, trendArrow, abs(trend)
                ),
                color = statusColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Last Reading
            Text(
                text = stringResource(Res.string.last_reading),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(
                    Res.string.reading_value, lastReading
                ), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(
                    Res.string.reading_details, lastReadingTime, lastMealType
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BloodGlucoseCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            BloodGlucoseCard(
                average = 7.4,
                trend = 0.3,
                status = "In Range",
                lastReading = 8.2,
                lastReadingTime = "4:59 PM",
                lastMealType = "BFF"
            )
        }
    }
}