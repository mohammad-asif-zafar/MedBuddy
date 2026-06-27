package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.LocalDate
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString

class GetFullHistoryUseCase(
    private val repository: IGlucoseRepository
) {
    suspend operator fun invoke(): List<RecentReading> {
        val records = repository.getAllRecords()
        
        // Priority for meal periods to sort within the same day (Higher number = later in day)
        val periodPriority = mapOf(
            getString(Res.string.before_breakfast) to 1,
            getString(Res.string.after_breakfast) to 2,
            getString(Res.string.before_lunch) to 3,
            getString(Res.string.after_lunch) to 4,
            getString(Res.string.before_dinner) to 5,
            getString(Res.string.after_dinner) to 6,
            getString(Res.string.bedtime) to 7
        )

        return records.flatMap { record ->
            listOfNotNull(
                record.beforeBreakfast?.let { RecentReading(record.date, getString(Res.string.before_breakfast), it, record.time, record.notes) },
                record.afterBreakfast?.let { RecentReading(record.date, getString(Res.string.after_breakfast), it, record.time, record.notes) },
                record.beforeLunch?.let { RecentReading(record.date, getString(Res.string.before_lunch), it, record.time, record.notes) },
                record.afterLunch?.let { RecentReading(record.date, getString(Res.string.after_lunch), it, record.time, record.notes) },
                record.beforeDinner?.let { RecentReading(record.date, getString(Res.string.before_dinner), it, record.time, record.notes) },
                record.afterDinner?.let { RecentReading(record.date, getString(Res.string.after_dinner), it, record.time, record.notes) },
                record.bedtime?.let { RecentReading(record.date, getString(Res.string.bedtime), it, record.time, record.notes) }
            )
        }.sortedWith(
            compareByDescending<RecentReading> { 
                try { parseDisplayDate(it.date) } catch (e: Exception) { LocalDate(1900, 1, 1) } 
            }.thenByDescending { 
                periodPriority[it.timePeriod] ?: 0 
            }
        )
    }
}
