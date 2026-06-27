package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.usecase.RecentReading
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.glucose_unit
import org.jetbrains.compose.resources.stringResource

@Composable
fun HistoryReadingItem(reading: RecentReading) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = reading.timePeriod,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${reading.date} • ${reading.time}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${reading.value}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(Res.string.glucose_unit),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (reading.notes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = reading.notes,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}



@Preview
@Composable
fun HistoryReadingItemPreview() {
    // Standard mock entry with structural data fields filled
    val sampleReadingWithNotes = RecentReading(
        timePeriod = "Before Breakfast",
        date = "24 Oct 2024",
        time = "08:30 AM",
        value = 5,
        notes = "Felt slightly dizzy after waking up, took reading immediately."
    )

    val sampleReadingNoNotes = RecentReading(
        timePeriod = "After Lunch",
        date = "24 Oct 2024",
        time = "02:15 PM",
        value = 7,
        notes = "" // Validates that the conditional divider and notes text vanish properly
    )

    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Item Variant: With User Notes",
                style = MaterialTheme.typography.labelMedium
            )
            HistoryReadingItem(reading = sampleReadingWithNotes)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Item Variant: Empty Notes",
                style = MaterialTheme.typography.labelMedium
            )
            HistoryReadingItem(reading = sampleReadingNoNotes)
        }
    }
}
