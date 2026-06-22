package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_form_date
import medbuddy.composeapp.generated.resources.lab_form_notes
import medbuddy.composeapp.generated.resources.lab_form_ref_range
import medbuddy.composeapp.generated.resources.lab_form_result_value
import medbuddy.composeapp.generated.resources.lab_form_save
import medbuddy.composeapp.generated.resources.lab_form_select_test
import medbuddy.composeapp.generated.resources.lab_form_test_details
import org.jetbrains.compose.resources.stringResource


@Composable
fun AddLabResultForm(onSave: () -> Unit) {
    var testName by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(stringResource(Res.string.lab_form_test_details), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)

        OutlinedTextField(
            value = testName,
            onValueChange = { testName = it },
            label = { Text(stringResource(Res.string.lab_form_select_test)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { value = it },
            label = { Text(stringResource(Res.string.lab_form_result_value)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = { Text("%", modifier = Modifier.padding(end = 12.dp)) }
        )

        OutlinedTextField(
            value = "12 May 2026",
            onValueChange = { },
            label = { Text(stringResource(Res.string.lab_form_date)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            readOnly = true,
            trailingIcon = { Icon(Icons.Default.CalendarMonth, null) }
        )

        Text(stringResource(Res.string.lab_form_ref_range), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text("4.0 - 6.0 %", fontWeight = FontWeight.Medium)

        OutlinedTextField(
            value = "",
            onValueChange = { },
            label = { Text(stringResource(Res.string.lab_form_notes)) },
            modifier = Modifier.fillMaxWidth().height(100.dp),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(stringResource(Res.string.lab_form_save), fontWeight = FontWeight.Bold)
        }
    }
}

