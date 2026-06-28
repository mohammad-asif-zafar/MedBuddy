package com.hathway.medbuddy.domain.repository

import com.hathway.medbuddy.domain.model.GlucoseRecord
import kotlinx.coroutines.flow.Flow

interface IGlucoseRepository {

    val recordsFlow: Flow<List<GlucoseRecord>>

    suspend fun getAllRecords(): List<GlucoseRecord>

    suspend fun insertRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?,
        time: String = "",
        mealType: String = "",
        notes: String = ""
    )

    suspend fun updateRecord(
        date: String,
        beforeBreakfast: Int?,
        afterBreakfast: Int?,
        beforeLunch: Int?,
        afterLunch: Int?,
        beforeDinner: Int?,
        afterDinner: Int?,
        bedtime: Int?,
        time: String = "",
        mealType: String = "",
        notes: String = ""
    )
    suspend fun hasTimePeriodForDate(date: String, timePeriod: String): Boolean
}
