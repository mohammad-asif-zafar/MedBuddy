package com.hathway.medbuddy.repository

import android.content.Context
import android.util.Log
import com.hathway.medbuddy.database.GlucoseDatabaseHelper
import com.hathway.medbuddy.data.GlucoseRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GlucoseRepository(context: Context) {
    private val databaseHelper = GlucoseDatabaseHelper(context)
    
    suspend fun getAllRecords(): List<GlucoseRecord> = withContext(Dispatchers.IO) {
        val records = databaseHelper.getAllRecords()
        Log.d("GlucoseRepository", "Loaded ${records.size} records from database")
        records.forEach { record ->
            Log.d("GlucoseRepository", "Record: ${record.date}, F: ${record.fasting}, B: ${record.breakfast}, L: ${record.lunch}, D: ${record.dinner}")
        }
        records
    }
    
    suspend fun insertRecord(
        date: String,
        fasting: Int?,
        breakfast: Int?,
        lunch: Int?,
        dinner: Int?
    ) = withContext(Dispatchers.IO) {
        Log.d("GlucoseRepository", "Inserting record: $date, F: $fasting, B: $breakfast, L: $lunch, D: $dinner")
        databaseHelper.insertRecord(date, fasting, breakfast, lunch, dinner)
        Log.d("GlucoseRepository", "Record inserted successfully")
    }
}
