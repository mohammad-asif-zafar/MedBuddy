package com.hathway.medbuddy.repository

import com.hathway.medbuddy.data.GlucoseRecord

interface IGlucoseRepository {

    suspend fun getAllRecords(): List<GlucoseRecord>

    suspend fun insertRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?
    )

    suspend fun updateRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?
    )
    suspend fun hasTimePeriodForDate(date: String, timePeriod: String): Boolean
}
