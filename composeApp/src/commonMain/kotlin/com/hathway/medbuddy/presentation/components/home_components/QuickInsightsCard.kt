package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode

enum class InsightType {
    POSITIVE, WARNING, ALERT
}

@Composable
fun QuickInsightsCard(
    insight: String, insightEmoji: String, insightTitle: String, insightType: InsightType
) {

    val chipColor = when (insightType) {
        InsightType.POSITIVE -> MaterialTheme.colorScheme.primary
        InsightType.WARNING -> MaterialTheme.colorScheme.secondary
        InsightType.ALERT -> MaterialTheme.colorScheme.error
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.Top
        ) {

            Surface(
                modifier = Modifier.size(52.dp),
                shape = CircleShape,
                color = chipColor.copy(alpha = 0.12f)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = insightEmoji
                    )
                }
            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = insightTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = insight,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuickInsightsCardPositivePreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {

            QuickInsightsCard(
                insightTitle = "Glucose Stable",
                insight = "Your average glucose decreased by 12% compared to last week.",
                insightEmoji = "📈",
                insightType = InsightType.POSITIVE
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuickInsightsCardWarningPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {

            QuickInsightsCard(
                insightTitle = "High Readings",
                insight = "3 readings exceeded your target range this week.",
                insightEmoji = "⚠️",
                insightType = InsightType.WARNING
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuickInsightsCardAlertPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {

            QuickInsightsCard(
                insightTitle = "Low Glucose Alert",
                insight = "You recorded 2 low glucose events in the last 24 hours.",
                insightEmoji = "🚨",
                insightType = InsightType.ALERT
            )
        }
    }
}
