package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.usecase.RecentReading
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.no_glucose_readings
import medbuddy.composeapp.generated.resources.recent_readings
import medbuddy.composeapp.generated.resources.view_all

@Composable
fun RecentRecordsCard(
    recentRecords: List<RecentReading>,
    onViewAllClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.recent_readings),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                TextButton(onClick = onViewAllClick) {
                    Text(stringResource(Res.string.view_all))
                }
            }

            if (recentRecords.isEmpty()) {
                Text(
                    text = stringResource(Res.string.no_glucose_readings),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                // Take only the top 3 items to show clean dashboard history constraints
                recentRecords.take(3).forEachIndexed { index, record ->
                    RecentRecordItem(record)

                    if (index != minOf(recentRecords.lastIndex, 2)) {
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RecentRecordsCardPreview() {
    val mockRecords = listOf(
        RecentReading(date = "12 May", timePeriod = "Before Breakfast", value = 115, time = "08:30 AM", abbreviatedPeriod = "B.Bf"),
        RecentReading(date = "12 May", timePeriod = "After Lunch", value = 145, time = "01:30 PM", abbreviatedPeriod = "A.Ln"),
        RecentReading(date = "11 May", timePeriod = "Before Dinner", value = 110, time = "08:30 PM", abbreviatedPeriod = "B.Dn")
    )
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.padding(16.dp)) {
            RecentRecordsCard(recentRecords = mockRecords)
        }
    }
}
