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

@Composable
fun NotificationCard(
    title: String? = null,
    message: String? = null,
    timestamp: Long? = null,
    type: NotificationType = NotificationType.REMINDER,
    isResolved: Boolean = false, // Added to handle the "Resolved" button state seen in the SS
    onActionClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Dynamic container colors matched to your theme configuration
    val containerColor = when (type) {
        NotificationType.HIGH_GLUCOSE -> DangerContainer
        NotificationType.LOW_GLUCOSE -> if (isResolved) MiniCardBackground else DangerContainer
        NotificationType.ALERT -> DangerContainer
        NotificationType.REMINDER, NotificationType.MEDICATION_REMINDER -> MiniCardBackground
        NotificationType.INFO -> Surface
    }

    // Border stroke matching the bottom clean card layout
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
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Flat styling matching the design
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

                // Pure status coloring logic from your theme file
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
                    Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = timestamp.toString(),
                            fontSize = 13.sp,
                            color = TextSecondaryMuted,
                            fontWeight = FontWeight.Normal
                        )

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
                            onClick = { onActionClick("VIEW") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DangerContainer,
                                contentColor = Danger
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp)
                        ) {
                            Text(text = "View", fontWeight = FontWeight.Bold, fontSize = 13.sp)
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
                                Text(text = "Resolved", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        } else {
                            Button(
                                onClick = { onActionClick("RESOLVE") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = DangerContainer,
                                    contentColor = Danger
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 6.dp)
                            ) {
                                Text(text = "View", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                    NotificationType.REMINDER, NotificationType.MEDICATION_REMINDER -> {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { onActionClick("SNOOZE") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Divider,
                                    contentColor = TextDark
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                            ) {
                                Text(text = "Snooze", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                            Spacer(Modifier.width(12.dp))
                            Button(
                                onClick = { onActionClick("TAKEN") },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = SuccessContainer,
                                    contentColor = BrandGreen
                                ),
                                shape = RoundedCornerShape(12.dp),
                                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp)
                            ) {
                                Text(text = "Taken", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            }
                        }
                    }
                    NotificationType.INFO -> {
                        TextButton(onClick = { onActionClick("OK") }) {
                            Text("OK", color = BrandGreen, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
