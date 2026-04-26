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
import com.hathway.medbuddy.components.GlucoseList
import com.hathway.medbuddy.components.AddGlucoseRecordDialog
import com.hathway.medbuddy.data.GlucoseRecord
import com.hathway.medbuddy.data.UserGlucoseRecord
import com.hathway.medbuddy.data.userGlucoseRecords
import com.hathway.medbuddy.data.convertDemoToUserRecords
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
fun AddScreen() {
    var showAddDialog by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Combine demo data and user records
        val allRecords = convertDemoToUserRecords() + userGlucoseRecords
        
        // Sort by date (latest first)
        val sortedRecords = allRecords.sortedByDescending { record ->
            parseDate(record.date)
        }
        
        GlucoseList(records = sortedRecords)
        
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
                // Add the new record to the user list
                userGlucoseRecords.add(newRecord)
                showAddDialog = false
            }
        )
    }
}
