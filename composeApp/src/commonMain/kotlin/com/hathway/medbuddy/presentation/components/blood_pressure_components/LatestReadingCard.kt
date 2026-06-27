package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.SuccessContainer
import androidx.compose.foundation.BorderStroke

@Composable
fun LatestReadingCard(
    date: String,
    systolic: Int,
    diastolic: Int,
    pulse: Int,
    statusLabel: String,
    statusColor: Color,
    statusContainerColor: Color,
    modifier: Modifier = Modifier
) {
    // Wrapped into a Material 3 Card component
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        ) {
            Text(
                text = "Latest Reading", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
                )
            )

            Text(
                text = date, style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ), modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            Row(
                verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$systolic/$diastolic",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 42.sp
                    )
                )

                Text(
                    text = " mmHg", style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    ), modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Dynamic Status Badge using constructor colors
                Box(
                    modifier = Modifier.padding(bottom = 8.dp)
                        .background(statusContainerColor, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = statusLabel, style = MaterialTheme.typography.bodyMedium.copy(
                            color = statusColor, fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Pulse: $pulse bpm", style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}


@Preview(name = "Latest Reading Light Mode", showBackground = true)
@Composable
fun LatestReadingCardLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Surface(modifier = Modifier.padding(16.dp)) {
            LatestReadingCard(
                date = "23 Jun 2026, 08:30 AM",
                systolic = 118,
                diastolic = 78,
                pulse = 72,
                statusLabel = "Normal",
                statusColor = StatusInRange,
                statusContainerColor = SuccessContainer
            )
        }
    }
}

@Preview(name = "Latest Reading Dark Mode", showBackground = true)
@Composable
fun LatestReadingCardDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Surface(modifier = Modifier.padding(16.dp)) {
            LatestReadingCard(
                date = "23 Jun 2026, 08:30 AM",
                systolic = 118,
                diastolic = 78,
                pulse = 72,
                statusLabel = "Normal",
                statusColor = StatusInRange,
                statusContainerColor = SuccessContainer
            )
        }
    }
}
