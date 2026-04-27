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
import java.text.SimpleDateFormat
import java.util.*

// Helper function to parse date strings for sorting
private fun parseDate(dateString: String): Date {
    val format = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
    return try {
        format.parse(dateString) ?: Date()
    } catch (e: Exception) {
        Date()
    }
}

@Composable
fun AddScreen(
    repository: Any? = null // Using Any for now to avoid import issues
) {
    var showAddDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var refreshTrigger by remember { mutableStateOf(0) } // Trigger for data refresh
    
    // Use database if available, otherwise fall back to demo data
    var records by remember { mutableStateOf(emptyList<com.hathway.medbuddy.data.GlucoseRecord>()) }
    
    // Load initial data
    LaunchedEffect(Unit) {
        if (repository != null) {
            // Database integration will be handled in Android implementation
            coroutineScope.launch {
                try {
                    // This will be handled by Android-specific implementation
                    @Suppress("UNCHECKED_CAST")
                    val dbRecords = (repository as suspend () -> List<com.hathway.medbuddy.data.GlucoseRecord>).invoke()
                    // Sort by date descending (latest first) - database already does this, but ensure UI consistency
                    records = dbRecords
                    println("AddScreen: Loaded ${dbRecords.size} records from database")
                } catch (e: Exception) {
                    // Fall back to demo data if database fails
                    val demoRecords = com.hathway.medbuddy.data.glucoseDemoList.sortedByDescending { record ->
                        parseDate(record.date)
                    }
                    records = demoRecords
                    println("AddScreen: Using demo data, ${demoRecords.size} records")
                }
            }
        } else {
            // Fall back to demo data - sort by date descending (latest first)
            val demoRecords = com.hathway.medbuddy.data.glucoseDemoList.sortedByDescending { record ->
                parseDate(record.date)
            }
            records = demoRecords
            println("AddScreen: Using demo data (no repository), ${demoRecords.size} records")
        }
    }
    
    // Refresh data when trigger changes
    LaunchedEffect(refreshTrigger) {
        if (repository != null) {
            coroutineScope.launch {
                try {
                    @Suppress("UNCHECKED_CAST")
                    val dbRecords = (repository as suspend () -> List<com.hathway.medbuddy.data.GlucoseRecord>).invoke()
                    records = dbRecords
                    println("AddScreen: Refreshed data, ${dbRecords.size} records")
                } catch (e: Exception) {
                    println("AddScreen: Error refreshing data: ${e.message}")
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
                            // This will be handled by Android-specific implementation
                            @Suppress("UNCHECKED_CAST")
                            val insertFunction = repository as suspend (String, Int?, Int?, Int?, Int?) -> Unit
                            insertFunction(
                                newRecord.date,
                                if (newRecord.timePeriod == "Fasting") newRecord.value else null,
                                if (newRecord.timePeriod == "After Breakfast") newRecord.value else null,
                                if (newRecord.timePeriod == "After Lunch") newRecord.value else null,
                                if (newRecord.timePeriod == "After Dinner") newRecord.value else null
                            )
                            println("AddScreen: Record saved successfully, refreshing data...")
                            // Trigger data refresh
                            refreshTrigger++
                        } catch (e: Exception) {
                            println("AddScreen: Error saving record: ${e.message}")
                            // Fall back to in-memory if database fails
                            com.hathway.medbuddy.data.userGlucoseRecords.add(newRecord)
                        }
                    }
                } else {
                    // Save to in-memory list
                    com.hathway.medbuddy.data.userGlucoseRecords.add(newRecord)
                    println("AddScreen: Record saved to in-memory list")
                }
                showAddDialog = false
            }
        )
    }
}
