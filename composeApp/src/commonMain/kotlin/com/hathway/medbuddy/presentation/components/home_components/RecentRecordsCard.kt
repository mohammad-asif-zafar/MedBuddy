package com.hathway.medbuddy.presentation.components.home_components

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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.viewmodel.RecentRecord

@Composable
fun RecentRecordsCard(
    recentRecords: List<RecentRecord>,
    onViewAllClick: () -> Unit = {}
) {

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

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Recent Readings",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                TextButton(
                    onClick = onViewAllClick
                ) {
                    Text("View All")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (recentRecords.isEmpty()) {

                Text(
                    text = "No glucose readings available",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            } else {

                recentRecords.take(3).forEachIndexed { index, record ->

                    RecentRecordItem(record)

                    if (index != recentRecords.lastIndex) {

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.outline.copy(
                                alpha = 0.3f
                            )
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )
                    }
                }
            }
        }
    }
}

