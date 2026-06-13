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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.presentation.components.glucose_components.AddGlucoseRecordDialog
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.components.glucose_components.GlucoseRecordHistory
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel

@Composable
fun AddScreen(
    repository: IGlucoseRepository? = null
) {
    val viewModel: AddViewModel = viewModel {
        AddViewModel(repository)
    }
    
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GlucoseRecordHistory(records = uiState.records)

        // Floating Action Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(56.dp)
                .clickable { viewModel.onShowDialog() },
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
    if (uiState.showAddDialog) {
        AddGlucoseRecordDialog(
            onDismiss = { viewModel.onDismissDialog() },
            onSave = { newRecord ->
                viewModel.saveRecord(newRecord)
            }
        )
    }
}
