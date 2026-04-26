package com.hathway.medbuddy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Notification(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: String,
    val isRead: Boolean = false
)

class NotificationsViewModel : ViewModel() {
    
    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _unreadCount = MutableStateFlow(0)
    val unreadCount: StateFlow<Int> = _unreadCount.asStateFlow()
    
    init {
        loadNotifications()
    }
    
    private fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            // Simulate loading notifications
            delay(500)
            val mockNotifications = listOf(
                Notification("1", "Appointment Reminder", "Your appointment is tomorrow at 10 AM", "2 hours ago"),
                Notification("2", "Medicine Reminder", "Time to take your daily medicine", "3 hours ago"),
                Notification("3", "Lab Results", "Your lab results are available", "1 day ago", false),
                Notification("4", "Health Tip", "Remember to drink 8 glasses of water today", "2 days ago", true)
            )
            _notifications.value = mockNotifications
            _unreadCount.value = mockNotifications.count { !it.isRead }
            _isLoading.value = false
        }
    }
    
    fun markAsRead(notificationId: String) {
        viewModelScope.launch {
            val updatedNotifications = _notifications.value.map { notification ->
                if (notification.id == notificationId) {
                    notification.copy(isRead = true)
                } else {
                    notification
                }
            }
            _notifications.value = updatedNotifications
            _unreadCount.value = updatedNotifications.count { !it.isRead }
        }
    }
    
    fun markAllAsRead() {
        viewModelScope.launch {
            val updatedNotifications = _notifications.value.map { it.copy(isRead = true) }
            _notifications.value = updatedNotifications
            _unreadCount.value = 0
        }
    }
    
    fun refreshNotifications() {
        loadNotifications()
    }
}
