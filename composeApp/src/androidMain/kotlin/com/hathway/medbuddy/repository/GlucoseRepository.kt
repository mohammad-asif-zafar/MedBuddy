package com.hathway.medbuddy.repository

import android.content.Context
import android.util.Log
import com.hathway.medbuddy.database.GlucoseDatabaseHelper
import com.hathway.medbuddy.data.GlucoseRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GlucoseRepository(context: Context) : IGlucoseRepository {
    private val databaseHelper = GlucoseDatabaseHelper(context)
    
    override suspend fun getAllRecords(): List<GlucoseRecord> = withContext(Dispatchers.IO) {
        val records = databaseHelper.getAllRecords()
        Log.d("GlucoseRepository", "Loaded ${records.size} records from database")
        records.forEach { record ->
            Log.d("GlucoseRepository", "Record: ${record.date}, BBF: ${record.beforeBreakfast}, ABF: ${record.afterBreakfast}, BL: ${record.beforeLunch}, AL: ${record.afterLunch}, BD: ${record.beforeDinner}, AD: ${record.afterDinner}, NGT: ${record.bedtime}")
        }
        records
    }

    override suspend fun insertRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?
    ) = withContext(Dispatchers.IO) {
        Log.d("GlucoseRepository", "Inserting record: $date, BBF: $beforeBreakfast, ABF: $afterBreakfast, BL: $beforeLunch, AL: $afterLunch, BD: $beforeDinner, AD: $afterDinner, NGT: $bedtime")
        databaseHelper.insertRecord(date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch, beforeDinner, afterDinner, bedtime)
        Log.d("GlucoseRepository", "Record inserted successfully")
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
        bedtime: Int?
    ) = withContext(Dispatchers.IO) {
        Log.d("GlucoseRepository", "Updating record: $date, BBF: $beforeBreakfast, ABF: $afterBreakfast, BL: $beforeLunch, AL: $afterLunch, BD: $beforeDinner, AD: $afterDinner, NGT: $bedtime")
        databaseHelper.updateRecord(date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch, beforeDinner, afterDinner, bedtime)
        Log.d("GlucoseRepository", "Record updated successfully")
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
}
