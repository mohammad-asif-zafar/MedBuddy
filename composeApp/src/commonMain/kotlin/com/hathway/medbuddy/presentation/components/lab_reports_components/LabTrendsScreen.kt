package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_trends_avg
import medbuddy.composeapp.generated.resources.lab_trends_latest
import org.jetbrains.compose.resources.stringResource


@Composable
fun LabTrendsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        ScrollableTabRow(
            selectedTabIndex = 2,
            edgePadding = 0.dp,
            containerColor = Color.Transparent,
            divider = {}
        ) {
            listOf("7 Days", "30 Days", "3 Months", "1 Year").forEachIndexed { i, title ->
                Tab(selected = i == 2, onClick = {}, text = { Text(title) })
            }
        }

        Spacer(Modifier.height(24.dp))

        // Mock chart area
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
            Text("Line Chart HbA1c", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }

        Spacer(Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            MetricCard(stringResource(Res.string.lab_trends_latest), "5.9 %", "12 May 2026", Modifier.weight(1f))
            MetricCard(stringResource(Res.string.lab_trends_avg), "5.82 %", "", Modifier.weight(1f))
        }
    }
}


