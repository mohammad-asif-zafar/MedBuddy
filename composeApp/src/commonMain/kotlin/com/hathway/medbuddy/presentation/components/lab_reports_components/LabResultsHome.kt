package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.model.LabResultData
import com.hathway.medbuddy.presentation.theme.StatusInRange
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.all_looks_good
import medbuddy.composeapp.generated.resources.health_at_glance
import medbuddy.composeapp.generated.resources.lab_add_title
import medbuddy.composeapp.generated.resources.lab_status_good
import medbuddy.composeapp.generated.resources.lab_status_normal
import medbuddy.composeapp.generated.resources.lab_test_cholesterol
import medbuddy.composeapp.generated.resources.lab_test_hba1c
import medbuddy.composeapp.generated.resources.lab_test_hdl
import medbuddy.composeapp.generated.resources.lab_test_triglycerides
import medbuddy.composeapp.generated.resources.latest_results
import medbuddy.composeapp.generated.resources.view_all
import org.jetbrains.compose.resources.stringResource

@Composable
fun LabResultsHome(onAddClick: () -> Unit, onViewAllClick: () -> Unit, onTrendsClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onTrendsClick() },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(Res.string.health_at_glance),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        stringResource(Res.string.all_looks_good),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 13.sp
                    )
                }
                Icon(
                    Icons.Outlined.Science,
                    null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp).alpha(0.5f)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(Res.string.latest_results),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            TextButton(onClick = onViewAllClick) {
                Text(stringResource(Res.string.view_all))
            }
        }

        val latest = listOf(
            LabResultData(stringResource(Res.string.lab_test_hba1c), "5.8 %", stringResource(Res.string.lab_status_good), StatusInRange, "12 May 2026"),
            LabResultData(stringResource(Res.string.lab_test_cholesterol), "180 mg/dL", stringResource(Res.string.lab_status_normal), StatusInRange, "10 May 2026"),
            LabResultData(stringResource(Res.string.lab_test_triglycerides), "120 mg/dL", stringResource(Res.string.lab_status_normal), StatusInRange, "10 May 2026"),
            LabResultData(
                stringResource(Res.string.lab_test_hdl),
                "55 mg/dL",
                stringResource(Res.string.lab_status_good),
                StatusInRange,
                "10 May 2026"
            )
        )

        latest.forEach { result ->
            LabResultItem(result, onClick = {})
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onAddClick,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Add, null)
            Spacer(Modifier.width(8.dp))
            Text(stringResource(Res.string.lab_add_title), fontWeight = FontWeight.Bold)
        }
    }
}
