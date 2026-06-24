package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.ui.MedBuddyNotification
import com.hathway.medbuddy.presentation.ui.notification_flow.AlertDetailsScreen
import com.hathway.medbuddy.presentation.ui.notification_flow.AlertSettingsScreen
import com.hathway.medbuddy.presentation.ui.notification_flow.NotificationChannelsScreen
import com.hathway.medbuddy.presentation.ui.notification_flow.SnoozeReminderScreen
import com.hathway.medbuddy.presentation.ui.notification_flow.TakeActionScreen
import com.hathway.medbuddy.presentation.viewmodel.NotificationViewModel
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.high_glucose_alert
import medbuddy.composeapp.generated.resources.snooze_reminder_title
import org.jetbrains.compose.resources.stringResource

enum class NotificationFlowState {
    LIST, DETAILS, TAKE_ACTION, SETTINGS, SNOOZE, CHANNELS
}

@Composable
fun NotificationContent(
    repository: IGlucoseRepository? = null,
    doctorRepository: IDoctorRepository? = null,
    onBack: () -> Unit
) {
    if (repository == null || doctorRepository == null) return
    
    val viewModel: NotificationViewModel = viewModel {
        NotificationViewModel(repository, doctorRepository)
    }

    val uiState by viewModel.uiState.collectAsState()
    var flowState by remember { mutableStateOf(NotificationFlowState.LIST) }

    AnimatedContent(
        targetState = flowState,
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { state ->
        when (state) {
            NotificationFlowState.LIST -> {
                MedBuddyNotification(
                    viewModel = viewModel,
                    onBackClick = onBack,
                    onNotificationClick = { notification ->
                        viewModel.selectNotification(notification)
                        flowState = NotificationFlowState.DETAILS
                    },
                    onSettingsClick = {
                        flowState = NotificationFlowState.SETTINGS
                    },
                    onSnoozeClick = { notification ->
                        viewModel.selectNotification(notification)
                        flowState = NotificationFlowState.SNOOZE
                    }
                )
            }
            NotificationFlowState.DETAILS -> {
                uiState.selectedNotification?.let { notification ->
                    AlertDetailsScreen(
                        title = notification.title,
                        message = notification.message,
                        timestamp = notification.timestamp,
                        type = notification.type,
                        onBack = { flowState = NotificationFlowState.LIST },
                        onAcknowledge = { flowState = NotificationFlowState.TAKE_ACTION }
                    )
                }
            }
            NotificationFlowState.TAKE_ACTION -> {
                TakeActionScreen(
                    title = uiState.selectedNotification?.title ?: stringResource(Res.string.high_glucose_alert),
                    onBack = { flowState = NotificationFlowState.DETAILS },
                    onSave = { action, notes ->
                        // Process action
                        flowState = NotificationFlowState.LIST
                    }
                )
            }
            NotificationFlowState.SETTINGS -> {
                AlertSettingsScreen(
                    onBack = { flowState = NotificationFlowState.LIST },
                    onManageChannels = { flowState = NotificationFlowState.CHANNELS }
                )
            }
            NotificationFlowState.SNOOZE -> {
                SnoozeReminderScreen(
                    title = uiState.selectedNotification?.title ?: stringResource(Res.string.snooze_reminder_title),
                    timestamp = uiState.selectedNotification?.timestamp,
                    onBack = { flowState = NotificationFlowState.LIST },
                    onSnooze = { minutes ->
                        flowState = NotificationFlowState.LIST
                    }
                )
            }
            NotificationFlowState.CHANNELS -> {
                NotificationChannelsScreen(
                    onBack = { flowState = NotificationFlowState.SETTINGS }
                )
            }
        }
    }
}
