package com.hathway.medbuddy.presentation.components.all_add_reading_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.outlined.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.viewmodel.HistorySummary
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.glucose_unit
import org.jetbrains.compose.resources.stringResource

@Composable
fun HistorySummaryCard(
    summary: HistorySummary, modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    // Adaptive Surface Theming System (Safe contrast ratios across themes)
    val containerBgColor = colors.primaryContainer.copy(alpha = 0.25f)
    val iconContainerColor = colors.primary
    val iconTintColor = colors.onPrimary

    val secondaryTextColor = colors.onSurfaceVariant
    val accentHighlightColor = colors.primary
    val dividerColor = colors.outlineVariant

    // FIX: Map state colors to dynamic, high-contrast M3 theme parameters
    val normalStatusColor = colors.secondary      // Balanced secondary green/teal tone
    val highStatusColor = colors.error          // Adaptive system alert red
    val lowStatusColor = colors.tertiary        // Complementary caution gold/tertiary tone

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerBgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            // Trend Icon Ring Frame Block
            Surface(
                modifier = Modifier.size(48.dp), shape = CircleShape, color = iconContainerColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.TrendingUp,
                        contentDescription = null,
                        tint = iconTintColor
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Average Value Text Information Node
            Column(modifier = Modifier.weight(1.2f)) {
                Text(
                    text = "Average (30 Days)",
                    style = MaterialTheme.typography.labelSmall,
                    color = secondaryTextColor
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${summary.averageValue}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = accentHighlightColor
                    )
                    Text(
                        text = " " + stringResource(Res.string.glucose_unit),
                        style = MaterialTheme.typography.labelSmall,
                        color = secondaryTextColor,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            // Central Boundary Separator Line
            VerticalDivider(
                modifier = Modifier.height(40.dp).padding(horizontal = 4.dp), color = dividerColor
            )

            // Proportional Health Aggregates Distribution Row
            Row(
                modifier = Modifier.weight(2f), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SummaryStatItem(
                    count = summary.normalCount, label = "Normal", color = normalStatusColor
                )
                SummaryStatItem(
                    count = summary.highCount, label = "High", color = highStatusColor
                )
                SummaryStatItem(
                    count = summary.lowCount, label = "Low", color = lowStatusColor
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = secondaryTextColor
            )
        }
    }
}

// ========================
// Previews
// ========================

private val previewMockSummary = HistorySummary(
    averageValue = 104, normalCount = 18, highCount = 4, lowCount = 2
)

@Preview
@Composable
fun HistorySummaryCardLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HistorySummaryCard(summary = previewMockSummary)
        }
    }
}

@Preview
@Composable
fun HistorySummaryCardDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            HistorySummaryCard(summary = previewMockSummary)
        }
    }
}
