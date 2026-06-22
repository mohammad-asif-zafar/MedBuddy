package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_ocr_desc
import medbuddy.composeapp.generated.resources.lab_ocr_save_all
import org.jetbrains.compose.resources.stringResource


@Composable
fun LabOCRScreen(onSave: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(Res.string.lab_ocr_desc), fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        repeat(3) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
                Checkbox(checked = true, onCheckedChange = {})
                Text("HbA1c", modifier = Modifier.weight(1f))
                Text("5.9 %", fontWeight = FontWeight.Bold)
            }
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = onSave, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text(stringResource(Res.string.lab_ocr_save_all))
        }
    }
}