package com.hathway.medbuddy.data.repository

import android.content.Context
import android.util.Log
import com.hathway.medbuddy.data.local.GlucoseDatabaseHelper
import com.hathway.medbuddy.data.remote.FirebaseSyncService
import com.hathway.medbuddy.data.remote.SyncManager
import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart

class GlucoseRepository(context: Context) : IGlucoseRepository {
    private val databaseHelper = GlucoseDatabaseHelper(context)
    private val firebaseSyncService = FirebaseSyncService(context)
    private val syncManager = SyncManager(context)
    private val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _recordsFlow = MutableSharedFlow<List<GlucoseRecord>>(replay = 1)
    override val recordsFlow: Flow<List<GlucoseRecord>> = _recordsFlow.asSharedFlow()
        .onStart {
            // Emit current local records when first collected
            val local = databaseHelper.getAllRecords()
            emit(local)
            // Also trigger background sync
            repositoryScope.launch { syncWithFirebase() }
        }
    
    override suspend fun getAllRecords(): List<GlucoseRecord> = withContext(Dispatchers.IO) {
        val localRecords = databaseHelper.getAllRecords()
        Log.d("GlucoseRepository", "Loaded ${localRecords.size} records from database")

        // Trigger background sync
        repositoryScope.launch {
            syncWithFirebase()
        }

        localRecords
    }

    private suspend fun syncWithFirebase() {
        try {
            val localRecords = databaseHelper.getAllRecords()
            val firebaseRecords = firebaseSyncService.fetchRecordsFromFirebase()
            Log.d("GlucoseRepository", "Fetched ${firebaseRecords.size} records from Firebase for sync")

            val mergedRecords = mergeRecords(localRecords, firebaseRecords)
            
            // Update local database with merged data
            mergedRecords.forEach { record ->
                databaseHelper.insertRecord(
                    date = record.date,
                    beforeBreakfast = record.beforeBreakfast,
                    afterBreakfast = record.afterBreakfast,
                    beforeLunch = record.beforeLunch,
                    afterLunch = record.afterLunch,
                    beforeDinner = record.beforeDinner,
                    afterDinner = record.afterDinner,
                    bedtime = record.bedtime,
                    time = record.time,
                    mealType = record.mealType,
                    notes = record.notes,
                )
            }
            
            // Notify observers about updated data
            val finalLocal = databaseHelper.getAllRecords()
            _recordsFlow.emit(finalLocal)
            Log.d("GlucoseRepository", "Sync finished and data emitted to flow: ${finalLocal.size} records")
            
        } catch (e: Exception) {
            Log.e("GlucoseRepository", "Sync failed", e)
        }
    }

    override suspend fun insertRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?,
        time: String,
        mealType: String,
        notes: String
    ) = withContext(Dispatchers.IO) {
        databaseHelper.insertRecord(date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch, beforeDinner, afterDinner, bedtime, time, mealType, notes)
        
        // Notify about change
        _recordsFlow.emit(databaseHelper.getAllRecords())

        val record = GlucoseRecord(
            date = date,
            beforeBreakfast = beforeBreakfast,
            afterBreakfast = afterBreakfast,
            beforeLunch = beforeLunch,
            afterLunch = afterLunch,
            beforeDinner = beforeDinner,
            afterDinner = afterDinner,
            bedtime = bedtime,
            time = time,
            mealType = mealType,
            notes = notes
        )
        syncManager.addToSyncQueue(record)
    }

    override suspend fun updateRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?,
        time: String,
        mealType: String,
        notes: String
    ) = withContext(Dispatchers.IO) {
        databaseHelper.updateRecord(date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch, beforeDinner, afterDinner, bedtime, time, mealType, notes)
        
        // Notify about change
        _recordsFlow.emit(databaseHelper.getAllRecords())

        val record = GlucoseRecord(
            date = date,
            beforeBreakfast = beforeBreakfast,
            afterBreakfast = afterBreakfast,
            beforeLunch = beforeLunch,
            afterLunch = afterLunch,
            beforeDinner = beforeDinner,
            afterDinner = afterDinner,
            bedtime = bedtime,
            time = time,
            mealType = mealType,
            notes = notes
        )
        syncManager.addToSyncQueue(record)
    }

    override suspend fun hasTimePeriodForDate(date: String, timePeriod: String): Boolean = withContext(Dispatchers.IO) {
        val records = databaseHelper.getAllRecords()
        val existingRecord = records.find { it.date == date }

        when (timePeriod) {
            "BEFORE_BREAKFAST" -> existingRecord?.beforeBreakfast != null
            "AFTER_BREAKFAST" -> existingRecord?.afterBreakfast != null
            "BEFORE_LUNCH" -> existingRecord?.beforeLunch != null
            "AFTER_LUNCH" -> existingRecord?.afterLunch != null
            "BEFORE_DINNER" -> existingRecord?.beforeDinner != null
            "AFTER_DINNER" -> existingRecord?.afterDinner != null
            "BEDTIME" -> existingRecord?.bedtime != null
            else -> false
        }
    }

    override fun close() {
        databaseHelper.close()
    }

    private fun mergeRecords(localRecords: List<GlucoseRecord>, firebaseRecords: List<GlucoseRecord>): List<GlucoseRecord> {
        val mergedMap = mutableMapOf<String, GlucoseRecord>()

        fun combine(existing: GlucoseRecord?, new: GlucoseRecord): GlucoseRecord {
            if (existing == null) return new
            val isNewer = new.createdAt >= existing.createdAt
            return GlucoseRecord(
                id = if (isNewer) new.id else existing.id,
                date = existing.date,
                beforeBreakfast = if (isNewer) (new.beforeBreakfast ?: existing.beforeBreakfast) else (existing.beforeBreakfast ?: new.beforeBreakfast),
                afterBreakfast = if (isNewer) (new.afterBreakfast ?: existing.afterBreakfast) else (existing.afterBreakfast ?: new.afterBreakfast),
                beforeLunch = if (isNewer) (new.beforeLunch ?: existing.beforeLunch) else (existing.beforeLunch ?: new.beforeLunch),
                afterLunch = if (isNewer) (new.afterLunch ?: existing.afterLunch) else (existing.afterLunch ?: new.afterLunch),
                beforeDinner = if (isNewer) (new.beforeDinner ?: existing.beforeDinner) else (existing.beforeDinner ?: new.beforeDinner),
                afterDinner = if (isNewer) (new.afterDinner ?: existing.afterDinner) else (existing.afterDinner ?: new.afterDinner),
                bedtime = if (isNewer) (new.bedtime ?: existing.bedtime) else (existing.bedtime ?: new.bedtime),
                time = if (isNewer && new.time.isNotEmpty()) new.time else existing.time,
                mealType = if (isNewer && new.mealType.isNotEmpty()) new.mealType else existing.mealType,
                notes = if (isNewer && new.notes.isNotEmpty()) new.notes else existing.notes,
                createdAt = if (isNewer) new.createdAt else existing.createdAt
            )
        }

        localRecords.forEach { mergedMap[it.date] = combine(mergedMap[it.date], it) }
        firebaseRecords.forEach { mergedMap[it.date] = combine(mergedMap[it.date], it) }
        return mergedMap.values.toList()
    }
}
