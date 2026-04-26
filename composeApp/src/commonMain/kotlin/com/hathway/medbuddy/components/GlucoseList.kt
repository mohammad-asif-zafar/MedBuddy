package com.hathway.medbuddy.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.data.GlucoseRecord
import com.hathway.medbuddy.data.UserGlucoseRecord

@Composable
fun GlucoseList(records: List<UserGlucoseRecord>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Your Glucose Records",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        if (records.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No glucose records yet.\nTap the + button to add your first reading!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Group records by date
            val groupedRecords = records.groupBy { it.date }
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(groupedRecords.keys.toList()) { date ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Date Row
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Text(
                                text = date,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        // Labels and Values Row
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                groupedRecords[date]?.forEach { record ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Time Period Label
                                        Text(
                                            text = record.timePeriod,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.weight(1f)
                                        )
                                        
                                        // Glucose Value
                                        Text(
                                            text = "${record.value} mg/dL",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = getGlucoseValueColor(record.value),
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.weight(1f),
                                            textAlign = TextAlign.End
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun getGlucoseValueColor(value: Int) = when {
    value < 70 -> MaterialTheme.colorScheme.error
    value > 180 -> MaterialTheme.colorScheme.error
    else -> MaterialTheme.colorScheme.onSurface
}
