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
            Log.d("GlucoseRepository", "Record: ${record.date}, F: ${record.fasting}, B: ${record.breakfast}, L: ${record.lunch}, D: ${record.dinner}")
        }
        records
    }
    
    override suspend fun insertRecord(
        date: String,
        fasting: Int?,
        breakfast: Int?,
        lunch: Int?,
        dinner: Int?
    ) = withContext(Dispatchers.IO) {
        Log.d("GlucoseRepository", "Inserting record: $date, F: $fasting, B: $breakfast, L: $lunch, D: $dinner")
        databaseHelper.insertRecord(date, fasting, breakfast, lunch, dinner)
        Log.d("GlucoseRepository", "Record inserted successfully")
        Unit // Explicitly return Unit to match interface
    }
    
    override suspend fun hasTimePeriodForDate(date: String, timePeriod: String): Boolean = withContext(Dispatchers.IO) {
        val records = databaseHelper.getAllRecords()
        val existingRecord = records.find { it.date == date }
        
        when (timePeriod) {
            "Fasting" -> existingRecord?.fasting != null
            "Before Breakfast" -> existingRecord?.breakfast != null
            "After Breakfast" -> existingRecord?.breakfast != null
            "Before Lunch" -> existingRecord?.lunch != null
            "After Lunch" -> existingRecord?.lunch != null
            "Before Dinner" -> existingRecord?.dinner != null
            "After Dinner" -> existingRecord?.dinner != null
            "Bedtime" -> existingRecord?.dinner != null
            else -> false
        }
    }
}
