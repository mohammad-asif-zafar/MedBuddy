@file:Suppress("DEPRECATION")

package com.hathway.medbuddy.presentation.ui.notification_flow

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.usecase.NotificationType
import com.hathway.medbuddy.presentation.theme.Danger
import com.hathway.medbuddy.presentation.theme.DangerContainer
import com.hathway.medbuddy.util.getNowEpochMillis
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.acknowledge_btn
import medbuddy.composeapp.generated.resources.action_back
import medbuddy.composeapp.generated.resources.alert_details_title
import medbuddy.composeapp.generated.resources.date_time_label
import medbuddy.composeapp.generated.resources.glucose_level_label
import medbuddy.composeapp.generated.resources.glucose_level_demo
import medbuddy.composeapp.generated.resources.lab_form_ref_range
import medbuddy.composeapp.generated.resources.notification_timestamp_days_ago
import medbuddy.composeapp.generated.resources.notification_timestamp_hours_ago
import medbuddy.composeapp.generated.resources.notification_timestamp_minutes_ago
import medbuddy.composeapp.generated.resources.notification_timestamp_now
import medbuddy.composeapp.generated.resources.recommended_action_label
import medbuddy.composeapp.generated.resources.recommended_action_high_glucose
import medbuddy.composeapp.generated.resources.source_label
import medbuddy.composeapp.generated.resources.source_glucose_reading
import medbuddy.composeapp.generated.resources.status_high
import medbuddy.composeapp.generated.resources.status_label
import medbuddy.composeapp.generated.resources.target_range_default
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun AlertDetailsScreen(
    title: String,
    message: String,
    timestamp: Long,
    type: NotificationType,
    onBack: () -> Unit,
    onAcknowledge: () -> Unit
) {
    BackHandler(enabled = true) {
        onBack()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.alert_details_title), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.action_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Alert Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = DangerContainer.copy(alpha = 0.5f)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = Danger,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        color = Danger
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = message,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // Info Grid
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                DetailRow(stringResource(Res.string.date_time_label), formatAlertTimestamp(timestamp))
                DetailRow(stringResource(Res.string.glucose_level_label), stringResource(Res.string.glucose_level_demo))
                DetailRow(stringResource(Res.string.status_label), stringResource(Res.string.status_high))
                
                Column {
                    Text(
                        stringResource(Res.string.recommended_action_label),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        stringResource(Res.string.recommended_action_high_glucose),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }

                DetailRow(stringResource(Res.string.source_label), stringResource(Res.string.source_glucose_reading))
                DetailRow(stringResource(Res.string.lab_form_ref_range), stringResource(Res.string.target_range_default))
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onAcknowledge,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(Res.string.acknowledge_btn), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    }
}

@Composable
private fun formatAlertTimestamp(timestamp: Long): String {
    val elapsedMinutes = ((getNowEpochMillis() - timestamp).coerceAtLeast(0L) / 60_000L).toInt()
    return when {
        elapsedMinutes < 1 -> stringResource(Res.string.notification_timestamp_now)
        elapsedMinutes < 60 -> stringResource(Res.string.notification_timestamp_minutes_ago, elapsedMinutes)
        elapsedMinutes < 1_440 -> stringResource(Res.string.notification_timestamp_hours_ago, elapsedMinutes / 60)
        else -> stringResource(Res.string.notification_timestamp_days_ago, elapsedMinutes / 1_440)
    }
}
