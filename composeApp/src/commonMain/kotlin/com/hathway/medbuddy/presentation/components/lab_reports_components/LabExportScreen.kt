package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_export_btn
import medbuddy.composeapp.generated.resources.lab_export_format
import org.jetbrains.compose.resources.stringResource

@Composable
fun LabExportScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(stringResource(Res.string.lab_export_format), fontWeight = FontWeight.Bold)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            FilterChip(selected = true, onClick = {}, label = { Text("PDF") }, modifier = Modifier.weight(1f))
            FilterChip(selected = false, onClick = {}, label = { Text("CSV") }, modifier = Modifier.weight(1f))
        }

        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text(stringResource(Res.string.lab_export_btn))
        }
    }
}