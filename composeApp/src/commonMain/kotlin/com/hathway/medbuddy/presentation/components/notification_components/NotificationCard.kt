package com.hathway.medbuddy.presentation.components.notification_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.usecase.NotificationType
import com.hathway.medbuddy.presentation.theme.BrandGreen
import com.hathway.medbuddy.presentation.theme.CardBorder
import com.hathway.medbuddy.presentation.theme.Danger
import com.hathway.medbuddy.presentation.theme.DangerContainer
import com.hathway.medbuddy.presentation.theme.Divider
import com.hathway.medbuddy.presentation.theme.Info
import com.hathway.medbuddy.presentation.theme.MiniCardBackground
import com.hathway.medbuddy.presentation.theme.Success
import com.hathway.medbuddy.presentation.theme.SuccessContainer
import com.hathway.medbuddy.presentation.theme.Surface
import com.hathway.medbuddy.presentation.theme.TextDark
import com.hathway.medbuddy.presentation.theme.TextSecondaryMuted
import com.hathway.medbuddy.util.getNowEpochMillis
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.notification_action_ok
import medbuddy.composeapp.generated.resources.notification_action_resolved
import medbuddy.composeapp.generated.resources.notification_action_snooze
import medbuddy.composeapp.generated.resources.notification_action_taken
import medbuddy.composeapp.generated.resources.notification_action_view
import medbuddy.composeapp.generated.resources.notification_timestamp_days_ago
import medbuddy.composeapp.generated.resources.notification_timestamp_hours_ago
import medbuddy.composeapp.generated.resources.notification_timestamp_minutes_ago
import medbuddy.composeapp.generated.resources.notification_timestamp_now
import org.jetbrains.compose.resources.stringResource

enum class NotificationAction {
    VIEW, RESOLVE, SNOOZE, TAKEN, OK
}

@Composable
fun NotificationCard(
    title: String? = null,
    message: String? = null,
    timestamp: Long? = null,
    type: NotificationType = NotificationType.REMINDER,
    isResolved: Boolean = false,
    onActionClick: (NotificationAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val timestampText = timestamp?.let { formatNotificationTimestamp(it) }

    val containerColor = when (type) {
        NotificationType.HIGH_GLUCOSE -> DangerContainer
        NotificationType.LOW_GLUCOSE -> if (isResolved) MiniCardBackground else DangerContainer
        NotificationType.ALERT -> DangerContainer
        NotificationType.REMINDER, NotificationType.MEDICATION_REMINDER -> MiniCardBackground
        NotificationType.INFO -> Surface
    }

    val borderStroke = if (type == NotificationType.REMINDER || type == NotificationType.MEDICATION_REMINDER || isResolved) {
        BorderStroke(1.dp, CardBorder)
    } else {
        null
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = borderStroke,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                val iconVector = when (type) {
                    NotificationType.ALERT, NotificationType.HIGH_GLUCOSE, NotificationType.LOW_GLUCOSE -> Icons.Filled.NotificationsActive
                    NotificationType.REMINDER, NotificationType.MEDICATION_REMINDER -> Icons.Outlined.CalendarMonth
                    NotificationType.INFO -> Icons.Outlined.Info
                }

                val iconTint = when (type) {
                    NotificationType.HIGH_GLUCOSE, NotificationType.ALERT -> Danger
                    NotificationType.LOW_GLUCOSE -> Danger
                    NotificationType.REMINDER, NotificationType.MEDICATION_REMINDER -> TextDark
                    NotificationType.INFO -> Info
                }

                Icon(
                    imageVector = iconVector,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    title?.let {
                        Text(
                            text = it,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (type) {
                                NotificationType.HIGH_GLUCOSE, NotificationType.ALERT -> Danger
                                NotificationType.LOW_GLUCOSE -> Danger
                                else -> TextDark
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    if (message != null) {
                        Text(
                            text = message,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextDark,
                            lineHeight = 18.sp
                        )
                    }
                    if (timestampText != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = timestampText,
                            fontSize = 13.sp,
                            color = TextSecondaryMuted,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                when (type) {
                    NotificationType.HIGH_GLUCOSE, NotificationType.ALERT -> {
                        Button(
                            onClick = { onActionClick(NotificationAction.VIEW) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DangerContainer,
                                contentColor = Danger
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp)
                        ) {
                            Text(text = stringResource(Res.string.notification_action_view), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        }
                    }
                    NotificationType.LOW_GLUCOSE -> {
                        if (isResolved) {
                            Button(
                                onClick = { },
                                enabled = false,
                                colors = ButtonDefaults.buttonColors(
                                    disabledContainerColor = SuccessContainer,
                                    disabledContentColor = Success
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(text = stringResource(Res.string.notification_action_resolved), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        } else {
                            Button(
                                onClick = { onActionClick(NotificationAction.RESOLVE) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DangerContainer,
                                    contentColor = Danger
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp)
                            ) {
                                Text(text = stringResource(Res.string.notification_action_view), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                    NotificationType.REMINDER, NotificationType.MEDICATION_REMINDER -> {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { onActionClick(NotificationAction.SNOOZE) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Divider,
                                    contentColor = TextDark
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                            ) {
                                Text(text = stringResource(Res.string.notification_action_snooze), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Spacer(Modifier.width(12.dp))
                            Button(
                                onClick = { onActionClick(NotificationAction.TAKEN) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SuccessContainer,
                                    contentColor = BrandGreen
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp)
                            ) {
                                Text(text = stringResource(Res.string.notification_action_taken), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                    NotificationType.INFO -> {
                        TextButton(onClick = { onActionClick(NotificationAction.OK) }) {
                            Text(stringResource(Res.string.notification_action_ok), color = BrandGreen, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun formatNotificationTimestamp(timestamp: Long): String {
    val elapsedMinutes = ((getNowEpochMillis() - timestamp).coerceAtLeast(0L) / 60_000L).toInt()
    return when {
        elapsedMinutes < 1 -> stringResource(Res.string.notification_timestamp_now)
        elapsedMinutes < 60 -> stringResource(Res.string.notification_timestamp_minutes_ago, elapsedMinutes)
        elapsedMinutes < 1_440 -> stringResource(Res.string.notification_timestamp_hours_ago, elapsedMinutes / 60)
        else -> stringResource(Res.string.notification_timestamp_days_ago, elapsedMinutes / 1_440)
    }
}

@Preview
@Composable
fun NotificationCardPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            NotificationCard(
                title = "High Glucose Alert",
                message = "Your last reading was 210 mg/dL. Consider contacting your doctor.",
                timestamp = getNowEpochMillis(),
                type = NotificationType.HIGH_GLUCOSE,
                onActionClick = {}
            )
            NotificationCard(
                title = "Medication Reminder",
                message = "Time to take your Metformin (1 tablet).",
                timestamp = getNowEpochMillis() - 600000,
                type = NotificationType.MEDICATION_REMINDER,
                onActionClick = {}
            )
        }
    }
}
