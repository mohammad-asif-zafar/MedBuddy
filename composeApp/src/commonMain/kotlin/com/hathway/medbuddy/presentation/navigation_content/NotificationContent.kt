package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.presentation.navigation.NavigationDestination
import com.hathway.medbuddy.presentation.ui.MedBuddyNotification
import com.hathway.medbuddy.presentation.ui.notification_flow.*
import com.hathway.medbuddy.presentation.viewmodel.NotificationViewModel

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
                        timestamp = "10:30 AM",
                        type = notification.type,
                        onBack = { flowState = NotificationFlowState.LIST },
                        onAcknowledge = { flowState = NotificationFlowState.TAKE_ACTION }
                    )
                }
            }
            NotificationFlowState.TAKE_ACTION -> {
                TakeActionScreen(
                    title = uiState.selectedNotification?.title ?: "High Glucose Alert",
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
                    title = uiState.selectedNotification?.title ?: "Reminder",
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
