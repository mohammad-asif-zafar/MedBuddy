package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.usecase.NotificationType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hathway.medbuddy.domain.usecase.MedBuddyNotification
import com.hathway.medbuddy.presentation.components.notification_components.EmptyNotificationsView
import com.hathway.medbuddy.presentation.components.notification_components.NotificationAction
import com.hathway.medbuddy.presentation.components.notification_components.NotificationCard
import com.hathway.medbuddy.presentation.viewmodel.NotificationViewModel
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedBuddyNotification(
    viewModel: NotificationViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: (MedBuddyNotification) -> Unit,
    onSettingsClick: () -> Unit,
    onSnoozeClick: (MedBuddyNotification) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val notifications = uiState.notifications
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf(
        stringResource(Res.string.filter_all),
        stringResource(Res.string.filter_alerts),
        stringResource(Res.string.filter_reminders),
        stringResource(Res.string.filter_updates)
    )
    val filteredNotifications = remember(notifications, selectedTab) {
        when (selectedTab) {
            1 -> notifications.filter {
                it.type == NotificationType.ALERT ||
                    it.type == NotificationType.HIGH_GLUCOSE ||
                    it.type == NotificationType.LOW_GLUCOSE
            }
            2 -> notifications.filter {
                it.type == NotificationType.REMINDER || it.type == NotificationType.MEDICATION_REMINDER
            }
            3 -> notifications.filter { it.type == NotificationType.INFO }
            else -> notifications
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.notifications_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(Res.string.back_button_desc),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = stringResource(Res.string.settings_desc),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PrimaryScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.background,
                divider = {}
            ) {
                tabs.forEachIndexed { index, label ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(label) }
                    )
                }
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    filteredNotifications.isEmpty() -> {
                        EmptyNotificationsView()
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                items = filteredNotifications,
                                key = { it.id }
                            ) { notification ->
                                NotificationCard(
                                    title = notification.title,
                                    message = notification.message,
                                    timestamp = notification.timestamp,
                                    type = notification.type,
                                    isResolved = false,
                                    onActionClick = { actionType ->
                                        when (actionType) {
                                            NotificationAction.VIEW -> onNotificationClick(notification)
                                            NotificationAction.SNOOZE -> onSnoozeClick(notification)
                                            else -> Unit
                                        }
                                    }
                                )
                            }
                            
                            item {
                                TextButton(
                                    onClick = { viewModel.markAllAsRead() },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(stringResource(Res.string.mark_all_read), color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
