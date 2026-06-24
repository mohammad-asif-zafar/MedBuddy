package com.hathway.medbuddy.presentation.ui.notification_flow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.util.getNowEpochMillis
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnoozeReminderScreen(
    title: String,
    timestamp: Long? = null,
    onBack: () -> Unit,
    onSnooze: (Int) -> Unit
) {
    var selectedDuration by remember { mutableStateOf(15) }

    val options = listOf(
        15 to stringResource(Res.string.snooze_15m),
        30 to stringResource(Res.string.snooze_30m),
        60 to stringResource(Res.string.snooze_1h),
        120 to stringResource(Res.string.snooze_2h)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.snooze_reminder_title), fontWeight = FontWeight.Bold) },
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
                .padding(24.dp)
        ) {
            // Context Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Timer, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(title, fontWeight = FontWeight.Bold)
                        timestamp?.let {
                            Text(formatSnoozeTimestamp(it), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            Spacer(Modifier.height(32.dp))

            Text(
                stringResource(Res.string.snooze_for_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            options.forEach { (minutes, label) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedDuration = minutes }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selectedDuration == minutes, onClick = { selectedDuration = minutes })
                        Spacer(Modifier.width(12.dp))
                        Text(label, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            }

            // Custom Time Option
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = false, onClick = { })
                    Spacer(Modifier.width(12.dp))
                    Text(stringResource(Res.string.snooze_custom), style = MaterialTheme.typography.bodyLarge)
                }
                Icon(Icons.Outlined.CalendarToday, null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = { onSnooze(selectedDuration) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(stringResource(Res.string.notification_action_snooze), fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
private fun formatSnoozeTimestamp(timestamp: Long): String {
    val elapsedMinutes = ((getNowEpochMillis() - timestamp).coerceAtLeast(0L) / 60_000L).toInt()
    return when {
        elapsedMinutes < 1 -> stringResource(Res.string.notification_timestamp_now)
        elapsedMinutes < 60 -> stringResource(Res.string.notification_timestamp_minutes_ago, elapsedMinutes)
        elapsedMinutes < 1_440 -> stringResource(Res.string.notification_timestamp_hours_ago, elapsedMinutes / 60)
        else -> stringResource(Res.string.notification_timestamp_days_ago, elapsedMinutes / 1_440)
    }
}
