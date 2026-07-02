package com.hathway.medbuddy.presentation.components.all_add_reading_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.usecase.RecentReading
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme


@Composable
fun HistoryReadingItem(
    reading: RecentReading,
    modifier: Modifier = Modifier
) {
    // Dynamic theme colors to respect contrast ratios across dark/light schemes
    val colors = MaterialTheme.colorScheme

    // Dynamic categorization styling mapping to semantic tokens
    val (icon, iconColor, bgColor) = remember(reading.category, colors) {
        when (reading.category) {
            "Morning" -> Triple(Icons.Outlined.LightMode, colors.primary, colors.primaryContainer.copy(alpha = 0.35f))
            "Afternoon" -> Triple(Icons.Outlined.WbSunny, colors.secondary, colors.secondaryContainer.copy(alpha = 0.35f))
            "Evening" -> Triple(Icons.Outlined.WbSunny, colors.tertiary, colors.tertiaryContainer.copy(alpha = 0.35f))
            else -> Triple(Icons.Outlined.Bedtime, colors.outline, colors.surfaceVariant)
        }
    }

    // Dynamic semantic alert colors maps directly to standard design specification
    val (statusColor, statusBgColor) = remember(reading.status, colors) {
        when (reading.status) {
            "High" -> Pair(colors.error, colors.errorContainer.copy(alpha = 0.4f))
            "Low" -> Pair(colors.primary, colors.primaryContainer.copy(alpha = 0.4f))
            else -> Pair(colors.secondary, colors.secondaryContainer.copy(alpha = 0.4f))
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = bgColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = reading.abbreviatedPeriod,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = colors.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = bgColor,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = reading.category,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = iconColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Text(
                    text = "${reading.time} • ${reading.mealTimingLabel}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.onSurfaceVariant
                )

                if (reading.notes.isNotEmpty()) {
                    Text(
                        text = reading.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurfaceVariant,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${reading.value}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface
                )
                Text(
                    text = "mg/dL",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = statusBgColor,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = reading.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = colors.outline
            )
        }
    }
}

// ========================
// Previews
// ========================
// ========================
// Previews
// ========================

private val previewMockReading = RecentReading(
    date = "24 Oct 2023",
    timePeriod = "Morning Routine",
    category = "Morning",
    abbreviatedPeriod = "AM",
    time = "08:30 AM",
    mealTimingLabel = "Before Breakfast",
    notes = "Fasting calculation",
    value = 115,
    status = "High"
)

@Preview
@Composable
fun HistoryReadingItemLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HistoryReadingItem(reading = previewMockReading)
        }
    }
}

@Preview
@Composable
fun HistoryReadingItemDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HistoryReadingItem(reading = previewMockReading)
        }
    }
}

