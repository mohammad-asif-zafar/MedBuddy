package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.LabResultData
import com.hathway.medbuddy.presentation.theme.StatusInRange
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_add_title
import medbuddy.composeapp.generated.resources.lab_list_all
import medbuddy.composeapp.generated.resources.lab_list_blood
import medbuddy.composeapp.generated.resources.lab_status_good
import medbuddy.composeapp.generated.resources.lab_status_normal
import medbuddy.composeapp.generated.resources.lab_test_cholesterol
import medbuddy.composeapp.generated.resources.lab_test_hba1c
import medbuddy.composeapp.generated.resources.lab_test_hdl
import medbuddy.composeapp.generated.resources.lab_test_ldl
import medbuddy.composeapp.generated.resources.lab_test_triglycerides
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabResultsList(onResultClick: (LabResultData) -> Unit, onAddClick: () -> Unit, onFilterClick: () -> Unit) {
    val results = listOf(
        LabResultData(stringResource(Res.string.lab_test_hba1c), "5.9 %", stringResource(Res.string.lab_status_good), StatusInRange, "12 May 2026"),
        LabResultData(stringResource(Res.string.lab_test_cholesterol), "190 mg/dL", stringResource(Res.string.lab_status_normal), StatusInRange, "10 May 2026"),
        LabResultData(stringResource(Res.string.lab_test_triglycerides), "120 mg/dL", stringResource(Res.string.lab_status_normal), StatusInRange, "10 May 2026"),
        LabResultData(stringResource(Res.string.lab_test_hdl), "45 mg/dL", stringResource(Res.string.lab_status_good), StatusInRange, "10 May 2026")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(selected = true, onClick = {}, label = { Text(stringResource(Res.string.lab_list_all)) }, modifier = Modifier.weight(1f))
            FilterChip(selected = false, onClick = {}, label = { Text(stringResource(Res.string.lab_list_blood)) }, modifier = Modifier.weight(1f))
            IconButton(onClick = onFilterClick) { Icon(Icons.Default.FilterList, null) }
        }

        Spacer(Modifier.height(16.dp))

        Text("May 2026", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(results) { result ->
                LabResultItem(result, onClick = { onResultClick(result) })
            }
        }

        Text("April 2026", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 16.dp))
        LabResultItem(LabResultData(stringResource(Res.string.lab_test_ldl), "110 mg/dL", stringResource(Res.string.lab_status_normal), StatusInRange, "28 April 2026"), onClick = {})

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onAddClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Add, null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(Res.string.lab_add_title))
        }
    }
}
