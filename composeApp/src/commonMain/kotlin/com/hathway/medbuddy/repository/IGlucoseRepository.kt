package com.hathway.medbuddy.repository

import com.hathway.medbuddy.data.GlucoseRecord

interface IGlucoseRepository {
    suspend fun getAllRecords(): List<GlucoseRecord>
    suspend fun insertRecord(
        date: String,
        fasting: Int?,
        breakfast: Int?,
        lunch: Int?,
        dinner: Int?
    )
    suspend fun hasTimePeriodForDate(date: String, timePeriod: String): Boolean
}
