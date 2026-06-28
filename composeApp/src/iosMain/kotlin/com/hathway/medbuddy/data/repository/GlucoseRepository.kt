package com.hathway.medbuddy.data.repository

import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GlucoseRepository : IGlucoseRepository {
    private val records = mutableListOf<GlucoseRecord>()
    private val _recordsFlow = MutableStateFlow<List<GlucoseRecord>>(emptyList())
    override val recordsFlow: Flow<List<GlucoseRecord>> = _recordsFlow.asStateFlow()

    override suspend fun getAllRecords(): List<GlucoseRecord> {
        return records
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
    ) {
        val newRecord = GlucoseRecord(
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
        records.add(newRecord)
        _recordsFlow.value = records.toList()
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
    ) {
        val index = records.indexOfFirst { it.date == date }
        val updatedRecord = GlucoseRecord(
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
        if (index != -1) {
            records[index] = updatedRecord
        } else {
            records.add(updatedRecord)
        }
        _recordsFlow.value = records.toList()
    }

    override suspend fun hasTimePeriodForDate(date: String, timePeriod: String): Boolean {
        val record = records.find { it.date == date } ?: return false
        return when (timePeriod) {
            "BEFORE_BREAKFAST" -> record.beforeBreakfast != null
            "AFTER_BREAKFAST" -> record.afterBreakfast != null
            "BEFORE_LUNCH" -> record.beforeLunch != null
            "AFTER_LUNCH" -> record.afterLunch != null
            "BEFORE_DINNER" -> record.beforeDinner != null
            "AFTER_DINNER" -> record.afterDinner != null
            "BEDTIME" -> record.bedtime != null
            else -> false
        }
    }
}
