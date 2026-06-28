package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.util.calculateGlucoseTargets
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.LocalDate
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString

class GetFullHistoryUseCase(
    private val repository: IGlucoseRepository
) {
    suspend operator fun invoke(records: List<GlucoseRecord>): List<RecentReading> {
        return records.flatMap { record ->
            TimePeriod.entries.mapNotNull { period ->
                val value = when (period) {
                    TimePeriod.BEFORE_BREAKFAST -> record.beforeBreakfast
                    TimePeriod.AFTER_BREAKFAST -> record.afterBreakfast
                    TimePeriod.BEFORE_LUNCH -> record.beforeLunch
                    TimePeriod.AFTER_LUNCH -> record.afterLunch
                    TimePeriod.BEFORE_DINNER -> record.beforeDinner
                    TimePeriod.AFTER_DINNER -> record.afterDinner
                    TimePeriod.BEDTIME -> record.bedtime
                }

                if (value != null) {
                    val target = calculateGlucoseTargets(
                        valueMgMl = value.toDouble() / 100.0,
                        mealType = period,
                        hasDiabetes = true
                    )
                    
                    RecentReading(
                        date = record.date,
                        timePeriod = getLocalizedPeriodName(period),
                        value = value,
                        time = record.time,
                        notes = record.notes,
                        status = target.statusText,
                        category = getCategory(period),
                        abbreviatedPeriod = getAbbreviatedPeriod(period),
                        mealTimingLabel = getMealTimingLabel(period)
                    )
                } else null
            }
        }.sortedWith(
            compareByDescending<RecentReading> { 
                try { parseDisplayDate(it.date) } catch (e: Exception) { LocalDate(1900, 1, 1) } 
            }.thenByDescending { it.time } // Fallback to time if date is same
        )
    }

    private fun getAbbreviatedPeriod(period: TimePeriod): String {
        return when (period) {
            TimePeriod.BEFORE_BREAKFAST -> "BBF"
            TimePeriod.AFTER_BREAKFAST -> "ABF"
            TimePeriod.BEFORE_LUNCH -> "BL"
            TimePeriod.AFTER_LUNCH -> "AL"
            TimePeriod.BEFORE_DINNER -> "BD"
            TimePeriod.AFTER_DINNER -> "AD"
            TimePeriod.BEDTIME -> "BT"
        }
    }

    private fun getMealTimingLabel(period: TimePeriod): String {
        return when (period) {
            TimePeriod.BEFORE_BREAKFAST, TimePeriod.BEFORE_LUNCH, TimePeriod.BEFORE_DINNER -> "Before Meal"
            TimePeriod.AFTER_BREAKFAST, TimePeriod.AFTER_LUNCH, TimePeriod.AFTER_DINNER -> "After Meal"
            TimePeriod.BEDTIME -> "Before Sleep"
        }
    }

    private suspend fun getLocalizedPeriodName(period: TimePeriod): String {
        return when (period) {
            TimePeriod.BEFORE_BREAKFAST -> getString(Res.string.before_breakfast)
            TimePeriod.AFTER_BREAKFAST -> getString(Res.string.after_breakfast)
            TimePeriod.BEFORE_LUNCH -> getString(Res.string.before_lunch)
            TimePeriod.AFTER_LUNCH -> getString(Res.string.after_lunch)
            TimePeriod.BEFORE_DINNER -> getString(Res.string.before_dinner)
            TimePeriod.AFTER_DINNER -> getString(Res.string.after_dinner)
            TimePeriod.BEDTIME -> getString(Res.string.bedtime)
        }
    }

    private fun getCategory(period: TimePeriod): String {
        return when (period) {
            TimePeriod.BEFORE_BREAKFAST, TimePeriod.AFTER_BREAKFAST -> "Morning"
            TimePeriod.BEFORE_LUNCH, TimePeriod.AFTER_LUNCH -> "Afternoon"
            TimePeriod.BEFORE_DINNER, TimePeriod.AFTER_DINNER -> "Evening"
            TimePeriod.BEDTIME -> "Night"
        }
    }
}
