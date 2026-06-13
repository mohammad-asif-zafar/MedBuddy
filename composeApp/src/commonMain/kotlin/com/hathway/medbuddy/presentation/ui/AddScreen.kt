package com.hathway.medbuddy.presentation.ui

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
import com.hathway.medbuddy.presentation.components.glucose_components.AddGlucoseRecordDialog
import com.hathway.medbuddy.data.GlucoseRecord
import com.hathway.medbuddy.data.UserGlucoseRecord
import com.hathway.medbuddy.data.TimePeriod
import com.hathway.medbuddy.glucose_screen.GlucoseRecordHistory
import com.hathway.medbuddy.repository.IGlucoseRepository
import kotlinx.coroutines.launch

@Composable
fun AddScreen(
    repository: IGlucoseRepository? = null
) {
    var showAddDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var refreshTrigger by remember { mutableStateOf(0) } // Trigger for data refresh

    // Use database if available, otherwise fall back to demo data
    var records by remember { mutableStateOf(emptyList<GlucoseRecord>()) }
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
       // GlucoseList(records = records)
        GlucoseRecordHistory(records = records)

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
                            // Get existing record for this date
                            val existingRecords = repository.getAllRecords()
                            val existingRecord = existingRecords.find { it.date == newRecord.date }

                            // Map time period to database field
                            val timePeriodEnum = TimePeriod.values().find { it.name == newRecord.timePeriod }

                            // Preserve existing values and update only the specific time period
                            val beforeBreakfast: Int? = existingRecord?.beforeBreakfast
                            val afterBreakfast: Int? = existingRecord?.afterBreakfast
                            val beforeLunch: Int? = existingRecord?.beforeLunch
                            val afterLunch: Int? = existingRecord?.afterLunch
                            val beforeDinner: Int? = existingRecord?.beforeDinner
                            val afterDinner: Int? = existingRecord?.afterDinner
                            val bedtime: Int? = existingRecord?.bedtime

                            val updatedBeforeBreakfast: Int? = when (timePeriodEnum) {
                                TimePeriod.BEFORE_BREAKFAST -> newRecord.value
                                else -> beforeBreakfast
                            }
                            val updatedAfterBreakfast: Int? = when (timePeriodEnum) {
                                TimePeriod.AFTER_BREAKFAST -> newRecord.value
                                else -> afterBreakfast
                            }
                            val updatedBeforeLunch: Int? = when (timePeriodEnum) {
                                TimePeriod.BEFORE_LUNCH -> newRecord.value
                                else -> beforeLunch
                            }
                            val updatedAfterLunch: Int? = when (timePeriodEnum) {
                                TimePeriod.AFTER_LUNCH -> newRecord.value
                                else -> afterLunch
                            }
                            val updatedBeforeDinner: Int? = when (timePeriodEnum) {
                                TimePeriod.BEFORE_DINNER -> newRecord.value
                                else -> beforeDinner
                            }
                            val updatedAfterDinner: Int? = when (timePeriodEnum) {
                                TimePeriod.AFTER_DINNER -> newRecord.value
                                else -> afterDinner
                            }
                            val updatedBedtime: Int? = when (timePeriodEnum) {
                                TimePeriod.BEDTIME -> newRecord.value
                                else -> bedtime
                            }

                            if (existingRecord != null) {
                                println("AddScreen: Record exists for date ${newRecord.date} - updating with new time period")
                                // Update existing record with merged values
                                repository.updateRecord(
                                    newRecord.date,
                                    beforeBreakfast = updatedBeforeBreakfast,
                                    afterBreakfast = updatedAfterBreakfast,
                                    beforeLunch = updatedBeforeLunch,
                                    afterLunch = updatedAfterLunch,
                                    beforeDinner = updatedBeforeDinner,
                                    afterDinner = updatedAfterDinner,
                                    bedtime = updatedBedtime,
                                    time = newRecord.time,
                                    mealType = newRecord.mealType,
                                    notes = newRecord.notes
                                )
                                println("AddScreen: Record updated successfully, refreshing data...")
                            } else {
                                // Insert new record
                                repository.insertRecord(
                                    newRecord.date,
                                    beforeBreakfast = updatedBeforeBreakfast,
                                    afterBreakfast = updatedAfterBreakfast,
                                    beforeLunch = updatedBeforeLunch,
                                    afterLunch = updatedAfterLunch,
                                    beforeDinner = updatedBeforeDinner,
                                    afterDinner = updatedAfterDinner,
                                    bedtime = updatedBedtime,
                                    time = newRecord.time,
                                    mealType = newRecord.mealType,
                                    notes = newRecord.notes
                                )
                                println("AddScreen: Record inserted successfully, refreshing data...")
                            }
                            // Trigger data refresh
                            refreshTrigger++
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
