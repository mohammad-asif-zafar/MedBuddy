package com.hathway.medbuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect
import com.hathway.medbuddy.components.GlucoseList
import com.hathway.medbuddy.components.AddGlucoseRecordDialog
import com.hathway.medbuddy.data.UserGlucoseRecord
import kotlinx.coroutines.launch

@Composable
fun AddScreen(
    repository: com.hathway.medbuddy.repository.IGlucoseRepository? = null
) {
    var showAddDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var refreshTrigger by remember { mutableStateOf(0) } // Trigger for data refresh
    
    // Use database if available, otherwise fall back to demo data
    var records by remember { mutableStateOf(emptyList<com.hathway.medbuddy.data.GlucoseRecord>()) }
    println("AddScreen:Record list:" + records.size + "-" + records.toString())

    // Load initial data
    LaunchedEffect(Unit) {
        if (repository != null) {
            // Database integration will be handled in Android implementation
            coroutineScope.launch {
                try {
                    val dbRecords = repository.getAllRecords()
                    // Sort by date descending (latest first) - database already does this, but ensure UI consistency
                    records = dbRecords
                    println("AddScreen: Loaded ${dbRecords.size} records from database")
                } catch (e: Exception) {
                    println("AddScreen: Error loading database records: ${e.message}")
                    records = emptyList()
                }
            }
        } else {
            println("AddScreen: No repository available - showing empty list")
            records = emptyList()
        }
    }
    
    // Refresh data when trigger changes
    LaunchedEffect(refreshTrigger) {
        if (repository != null) {
            coroutineScope.launch {
                try {
                    val dbRecords = repository.getAllRecords()
                    records = dbRecords
                    println("AddScreen: Refreshed data, ${dbRecords.size} records")
                } catch (e: Exception) {
                    println("AddScreen: Error refreshing data: ${e.message}")
                    records = emptyList()
                }
            }
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GlucoseList(records = records)
        
        // Floating Action Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(56.dp)
                .clickable { showAddDialog = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    
    // Add Glucose Record Dialog
    if (showAddDialog) {
        AddGlucoseRecordDialog(
            onDismiss = { showAddDialog = false },
            onSave = { newRecord: UserGlucoseRecord ->
                if (repository != null) {
                    // Save to database
                    coroutineScope.launch {
                        try {
                            // Check if time period already exists for this date
                            val hasExistingPeriod = repository.hasTimePeriodForDate(newRecord.date, newRecord.timePeriod)
                            
                            if (hasExistingPeriod) {
                                println("AddScreen: Time period '${newRecord.timePeriod}' already exists for date ${newRecord.date}")
                                // Show notification to user - record not saved
                            } else {
                                repository.insertRecord(
                                    newRecord.date,
                                    if (newRecord.timePeriod == "Fasting") newRecord.value else null,
                                    if (newRecord.timePeriod == "Before Breakfast" || newRecord.timePeriod == "After Breakfast") newRecord.value else null,
                                    if (newRecord.timePeriod == "Before Lunch" || newRecord.timePeriod == "After Lunch") newRecord.value else null,
                                    if (newRecord.timePeriod == "Before Dinner" || newRecord.timePeriod == "After Dinner" || newRecord.timePeriod == "Bedtime") newRecord.value else null
                                )
                                println("AddScreen: Record saved successfully, refreshing data...")
                                // Trigger data refresh
                                refreshTrigger++
                            }
                        } catch (e: Exception) {
                            println("AddScreen: Error saving record: ${e.message}")
                            // No fallback - record not saved if database fails
                        }
                    }
                } else {
                    println("AddScreen: No repository available - record not saved")
                }
                showAddDialog = false
            }
        )
    }
}
