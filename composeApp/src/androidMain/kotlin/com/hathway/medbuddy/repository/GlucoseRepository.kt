package com.hathway.medbuddy.repository

import android.content.Context
import android.util.Log
import com.hathway.medbuddy.FirebaseSyncService
import com.hathway.medbuddy.SyncManager
import com.hathway.medbuddy.database.GlucoseDatabaseHelper
import com.hathway.medbuddy.data.GlucoseRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GlucoseRepository(context: Context) : IGlucoseRepository {
    private val databaseHelper = GlucoseDatabaseHelper(context)
    private val firebaseSyncService = FirebaseSyncService(context)
    private val syncManager = SyncManager(context)
    
    override suspend fun getAllRecords(): List<GlucoseRecord> = withContext(Dispatchers.IO) {
        val localRecords = databaseHelper.getAllRecords()
        Log.d("GlucoseRepository", "Loaded ${localRecords.size} records from database")

        // Fetch from Firebase and merge
        try {
            val firebaseRecords = firebaseSyncService.fetchRecordsFromFirebase()
            Log.d("GlucoseRepository", "Fetched ${firebaseRecords.size} records from Firebase")

            // Merge records by date, preferring Firebase data if it exists
            val mergedRecords = mergeRecords(localRecords, firebaseRecords)
            Log.d("GlucoseRepository", "Merged to ${mergedRecords.size} records")

            // Update local database with merged records
            mergedRecords.forEach { record ->
                databaseHelper.updateRecord(
                    record.date,
                    record.beforeBreakfast,
                    record.afterBreakfast,
                    record.beforeLunch,
                    record.afterLunch,
                    record.beforeDinner,
                    record.afterDinner,
                    record.bedtime
                )
            }

            mergedRecords
        } catch (e: Exception) {
            Log.e("GlucoseRepository", "Failed to fetch from Firebase, using local data", e)
            localRecords
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
        Log.d("GlucoseRepository", "Inserting record: $date, BBF: $beforeBreakfast, ABF: $afterBreakfast, BL: $beforeLunch, AL: $afterLunch, BD: $beforeDinner, AD: $afterDinner, NGT: $bedtime")
        databaseHelper.insertRecord(date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch, beforeDinner, afterDinner, bedtime, time, mealType, notes)
        Log.d("GlucoseRepository", "Record inserted successfully")

        // Sync to Firebase in background
        launch {
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

        Unit // Explicitly return Unit to match interface
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
        Log.d("GlucoseRepository", "Updating record: $date, BBF: $beforeBreakfast, ABF: $afterBreakfast, BL: $beforeLunch, AL: $afterLunch, BD: $beforeDinner, AD: $afterDinner, NGT: $bedtime")
        databaseHelper.updateRecord(date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch, beforeDinner, afterDinner, bedtime, time, mealType, notes)
        Log.d("GlucoseRepository", "Record updated successfully")

        // Sync to Firebase in background
        launch {
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

        Unit // Explicitly return Unit to match interface
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

    private fun mergeRecords(localRecords: List<GlucoseRecord>, firebaseRecords: List<GlucoseRecord>): List<GlucoseRecord> {
        val mergedMap = mutableMapOf<String, GlucoseRecord>()

        // Add local records
        localRecords.forEach { record ->
            mergedMap[record.date] = record
        }

        // Merge Firebase records, preferring Firebase data if it exists
        firebaseRecords.forEach { firebaseRecord ->
            val existing = mergedMap[firebaseRecord.date]
            if (existing == null || firebaseRecord.createdAt > existing.createdAt) {
                mergedMap[firebaseRecord.date] = firebaseRecord
            }
        }

        return mergedMap.values.toList()
    }
}
