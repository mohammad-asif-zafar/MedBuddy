package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.theme.StatusInRange
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_insights_overall
import medbuddy.composeapp.generated.resources.lab_insights_recommendations
import org.jetbrains.compose.resources.stringResource

@Composable
fun LabInsightsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(Res.string.lab_insights_overall), fontWeight = FontWeight.Bold)
            Surface(color = StatusInRange.copy(alpha = 0.1f), shape = CircleShape) {
                Text("Good", color = StatusInRange, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
            }
        }

        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = StatusInRange.copy(alpha = 0.1f))) {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(Icons.Outlined.Lightbulb, null, tint = StatusInRange)
                Spacer(Modifier.width(12.dp))
                Text("Your HbA1c level is within the target range. Keep up the good work and maintain your current lifestyle.")
            }
        }

        Text(stringResource(Res.string.lab_insights_recommendations), fontWeight = FontWeight.Bold)
        listOf("Maintain a balanced diet", "Stay active for 30 min daily").forEach { rec ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Check, null, tint = StatusInRange, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text(rec)
            }
        }
    }
}