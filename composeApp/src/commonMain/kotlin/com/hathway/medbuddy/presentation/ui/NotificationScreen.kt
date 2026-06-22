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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hathway.medbuddy.domain.usecase.MedBuddyNotification
import com.hathway.medbuddy.presentation.components.notification_components.EmptyNotificationsView
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
                            contentDescription = "Settings",
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
            // Filter Tabs
            PrimaryScrollableTabRow(
                selectedTabIndex = 0,
                edgePadding = 16.dp,
                containerColor = MaterialTheme.colorScheme.background,
                divider = {}
            ) {
                Tab(selected = true, onClick = {}, text = { Text("All") })
                Tab(selected = false, onClick = {}, text = { Text(stringResource(Res.string.filter_alerts)) })
                Tab(selected = false, onClick = {}, text = { Text(stringResource(Res.string.filter_reminders)) })
                Tab(selected = false, onClick = {}, text = { Text(stringResource(Res.string.filter_updates)) })
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

                    notifications.isEmpty() -> {
                        EmptyNotificationsView()
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(
                                items = notifications,
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
                                            "VIEW" -> onNotificationClick(notification)
                                            "SNOOZE" -> onSnoozeClick(notification)
                                        }
                                    }
                                )
                            }
                            
                            item {
                                TextButton(
                                    onClick = { },
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
