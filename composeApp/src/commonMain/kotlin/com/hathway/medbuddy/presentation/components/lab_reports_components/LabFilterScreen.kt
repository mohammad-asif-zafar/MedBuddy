package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_filter_apply
import medbuddy.composeapp.generated.resources.lab_filter_search
import medbuddy.composeapp.generated.resources.lab_filter_test_type
import org.jetbrains.compose.resources.stringResource

@Composable
fun LabFilterScreen(onApply: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(Res.string.lab_filter_search)) },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            shape = RoundedCornerShape(12.dp)
        )

        Column {
            Text(stringResource(Res.string.lab_filter_test_type), fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text("Blood") })
                AssistChip(onClick = {}, label = { Text("Urine") })
            }
        }

        Button(onClick = onApply, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text(stringResource(Res.string.lab_filter_apply))
        }
    }
}
