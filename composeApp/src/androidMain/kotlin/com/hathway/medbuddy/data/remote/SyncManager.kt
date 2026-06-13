package com.hathway.medbuddy.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.FirebaseManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SyncManager(context: Context) {

    private val context = context.applicationContext
    private val firebaseSyncService = FirebaseSyncService(context)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.Idle)
    val syncStatus: StateFlow<SyncStatus> = _syncStatus

    private val syncQueue = mutableListOf<GlucoseRecord>()
    private var isSyncing = false

    fun addToSyncQueue(record: GlucoseRecord) {
        syncQueue.add(record)
        Log.d("SyncManager", "Added record to sync queue. Queue size: ${syncQueue.size}")
        trySync()
    }

    fun syncAllRecords(records: List<GlucoseRecord>) {
        syncQueue.addAll(records)
        Log.d("SyncManager", "Added ${records.size} records to sync queue. Queue size: ${syncQueue.size}")
        trySync()
    }

    private fun trySync() {
        if (isSyncing || syncQueue.isEmpty()) {
            return
        }

        if (!isNetworkAvailable()) {
            Log.d("SyncManager", "No network available. Records will sync when connection is restored.")
            _syncStatus.value = SyncStatus.WaitingForNetwork
            return
        }

        isSyncing = true
        _syncStatus.value = SyncStatus.Syncing

        scope.launch {
            try {
                val userId = getCurrentUserId()
                if (userId.isEmpty()) {
                    Log.e("SyncManager", "User not authenticated")
                    _syncStatus.value = SyncStatus.Error("User not authenticated")
                    isSyncing = false
                    return@launch
                }

                var successCount = 0
                var failureCount = 0

                while (syncQueue.isNotEmpty()) {
                    val record = syncQueue.removeAt(0)
                    val success = firebaseSyncService.syncRecordToFirebase(record, userId)
                    if (success) {
                        successCount++
                    } else {
                        failureCount++
                        // Re-add to queue for retry
                        syncQueue.add(record)
                    }

                    // Small delay between syncs to avoid rate limiting
                    delay(100)
                }

                Log.d("SyncManager", "Sync completed: $successCount success, $failureCount failures")
                _syncStatus.value = if (failureCount == 0) {
                    SyncStatus.Success
                } else {
                    SyncStatus.PartialSuccess(successCount, failureCount)
                }
            } catch (e: Exception) {
                Log.e("SyncManager", "Sync failed", e)
                _syncStatus.value = SyncStatus.Error(e.message ?: "Unknown error")
            } finally {
                isSyncing = false
            }
        }
    }

    fun forceSync() {
        Log.d("SyncManager", "Force sync requested")
        trySync()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun getCurrentUserId(): String {
        return FirebaseManager.auth.currentUser?.uid ?: ""
    }

    fun getQueueSize(): Int = syncQueue.size
}

sealed class SyncStatus {
    object Idle : SyncStatus()
    object Syncing : SyncStatus()
    object WaitingForNetwork : SyncStatus()
    object Success : SyncStatus()
    data class PartialSuccess(val successCount: Int, val failureCount: Int) : SyncStatus()
    data class Error(val message: String) : SyncStatus()
}
