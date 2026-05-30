package com.hathway.medbuddy.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.data.GlucoseRecord
import com.hathway.medbuddy.data.UserGlucoseRecord

@Composable
fun GlucoseList(records: List<GlucoseRecord>) {
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
            // Create expandable state for each date (all collapsed by default)
            val expandedStates = remember { mutableStateMapOf<String, Boolean>() }
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(
                    items = records,
                    key = { index, _ -> "${records[index].date}-$index" }
                ) { _, record ->
                    ExpandableDateSection(
                        date = record.date,
                        record = record,
                        isExpanded = expandedStates[record.date] ?: false,
                        onToggleExpanded = {
                            expandedStates[record.date] = !(expandedStates[record.date] ?: false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableDateSection(
    date: String,
    record: GlucoseRecord,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Date Row (always visible, clickable to expand/collapse)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleExpanded() },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                // Expand/Collapse Icon
                Text(
                    text = if (isExpanded) "▼" else "▶",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Expandable Content (labels and values)
        AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
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
                    // Before Breakfast (Fasting)
                    record.beforeBreakfast?.let { value ->
                        GlucoseReadingRow(
                            timePeriod = "Before Breakfast",
                            value = value,
                            showDivider = record.afterBreakfast != null || record.beforeLunch != null || record.afterLunch != null || record.beforeDinner != null || record.afterDinner != null || record.bedtime != null
                        )
                    }

                    // After Breakfast
                    record.afterBreakfast?.let { value ->
                        GlucoseReadingRow(
                            timePeriod = "After Breakfast",
                            value = value,
                            showDivider = record.beforeLunch != null || record.afterLunch != null || record.beforeDinner != null || record.afterDinner != null || record.bedtime != null
                        )
                    }

                    // Before Lunch
                    record.beforeLunch?.let { value ->
                        GlucoseReadingRow(
                            timePeriod = "Before Lunch",
                            value = value,
                            showDivider = record.afterLunch != null || record.beforeDinner != null || record.afterDinner != null || record.bedtime != null
                        )
                    }

                    // After Lunch
                    record.afterLunch?.let { value ->
                        GlucoseReadingRow(
                            timePeriod = "After Lunch",
                            value = value,
                            showDivider = record.beforeDinner != null || record.afterDinner != null || record.bedtime != null
                        )
                    }

                    // Before Dinner
                    record.beforeDinner?.let { value ->
                        GlucoseReadingRow(
                            timePeriod = "Before Dinner",
                            value = value,
                            showDivider = record.afterDinner != null || record.bedtime != null
                        )
                    }

                    // After Dinner
                    record.afterDinner?.let { value ->
                        GlucoseReadingRow(
                            timePeriod = "After Dinner",
                            value = value,
                            showDivider = record.bedtime != null
                        )
                    }

                    // Bedtime
                    record.bedtime?.let { value ->
                        GlucoseReadingRow(
                            timePeriod = "Bedtime",
                            value = value,
                            showDivider = false
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GlucoseReadingRow(
    timePeriod: String,
    value: Int,
    showDivider: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time Period Label
            Text(
                text = timePeriod,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            
            // Glucose Value
            Text(
                text = "${value} mg/dL",
                style = MaterialTheme.typography.bodyLarge,
                color = getGlucoseValueColor(value),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
        
        // Add separator if needed
        if (showDivider) {
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                thickness = 1.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun getGlucoseValueColor(value: Int) = when {
    value < 70 -> MaterialTheme.colorScheme.error
    value > 180 -> MaterialTheme.colorScheme.error
    else -> MaterialTheme.colorScheme.onSurface
}
