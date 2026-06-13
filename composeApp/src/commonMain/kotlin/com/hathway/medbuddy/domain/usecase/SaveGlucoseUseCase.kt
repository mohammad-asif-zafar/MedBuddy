package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.repository.IGlucoseRepository

class SaveGlucoseUseCase(
    private val repository: IGlucoseRepository
) {
    suspend operator fun invoke(
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
    ) {
        // Here you could add domain validation logic if needed
        // For now, we'll delegate to the repository
        
        val existing = repository.hasTimePeriodForDate(date, mealType)
        
        if (existing) {
            repository.updateRecord(
                date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch,
                beforeDinner, afterDinner, bedtime, time, mealType, notes
            )
        } else {
            repository.insertRecord(
                date, beforeBreakfast, afterBreakfast, beforeLunch, afterLunch,
                beforeDinner, afterDinner, bedtime, time, mealType, notes
            )
        }
    }
}
