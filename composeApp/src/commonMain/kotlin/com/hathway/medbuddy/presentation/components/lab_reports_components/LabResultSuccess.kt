package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.LabResultData
import com.hathway.medbuddy.presentation.theme.StatusInRange
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_action_add_another
import medbuddy.composeapp.generated.resources.lab_action_view_results
import medbuddy.composeapp.generated.resources.lab_status_good
import medbuddy.composeapp.generated.resources.lab_success_msg
import medbuddy.composeapp.generated.resources.lab_success_title
import medbuddy.composeapp.generated.resources.lab_test_hba1c
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme


@Composable
fun LabResultSuccess(onViewResults: () -> Unit, onAddAnother: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = StatusInRange.copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Default.CheckCircle, null, tint = StatusInRange, modifier = Modifier.size(64.dp))
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            stringResource(Res.string.lab_success_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black
        )

        Text(stringResource(Res.string.lab_success_msg), textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(Modifier.height(48.dp))

        LabResultItem(
            LabResultData(
                stringResource(Res.string.lab_test_hba1c),
                "5.9 %",
                stringResource(Res.string.lab_status_good),
                StatusInRange,
                "12 May 2026"
            ),
            onClick = {}
        )

        Spacer(Modifier.height(48.dp))

        Button(
            onClick = onViewResults,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(stringResource(Res.string.lab_action_view_results), fontWeight = FontWeight.Bold)
        }

        TextButton(onClick = onAddAnother) {
            Text(stringResource(Res.string.lab_action_add_another))
        }
    }
}

@Preview
@Composable
fun LabResultSuccessPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        LabResultSuccess(onViewResults = {}, onAddAnother = {})
    }
}

