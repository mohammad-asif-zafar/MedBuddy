package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.components.glucose_components.AddGlucoseRecordDialog
import com.hathway.medbuddy.presentation.components.glucose_components.GlucoseRecordHistory
import com.hathway.medbuddy.presentation.viewmodel.AddViewModel
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.add_glucose_reading
import medbuddy.composeapp.generated.resources.add_reading
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddScreen(
    repository: IGlucoseRepository? = null
) {
    val viewModel: AddViewModel = viewModel {
        AddViewModel(repository)
    }

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
    ) {
        GlucoseRecordHistory(records = uiState.records)

        // Floating Action Button
        ExtendedFloatingActionButton(onClick = {
            viewModel.onShowDialog()
        }, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp), icon = {
            Icon(
                imageVector = Icons.Default.Add, contentDescription = stringResource(
                    Res.string.add_glucose_reading
                )
            )
        }, text = {
            Text(
                stringResource(
                    Res.string.add_reading
                )
            )
        })
    }

    // Add Glucose Record Dialog
    if (uiState.showAddDialog) {
        AddGlucoseRecordDialog(onDismiss = { viewModel.onDismissDialog() }, onSave = { newRecord ->
            viewModel.saveRecord(newRecord)
        })
    }
}
