package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.util.calculateGlucoseTargets
import com.hathway.medbuddy.util.getNowLocalDateTime
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString
import kotlin.collections.List

data class GlucoseDashboard(
    val todayGlucose: Int?,
    val glucoseStatus: String,
    val isToday: Boolean,
    val sevenDayAverage: Int,
    val hbA1cEstimate: Double,
    val highestGlucose: Int,
    val lowestGlucose: Int,
    val chartReadings: List<Float>,
    val recordedTime: String,
    val mealType: String,
    val lastMealPeriod: TimePeriod,
    val recentRecords: List<DashboardRecentRecord>,
    val dailyAverageReadings: List<DailyAverageReading>,
    val last7Readings: List<RecentReading>
)

data class DashboardRecentRecord(
    val date: String, val timePeriod: String, val value: Int, val time: String
)

data class DailyAverageReading(
    val date: String, val averageValue: Float
)

data class RecentReading(
    val date: String, val timePeriod: String, val value: Int, val time: String, val notes: String = ""
)

class GetGlucoseDashboardUseCase(
    private val repository: IGlucoseRepository
) {
    suspend operator fun invoke(): GlucoseDashboard {
        val records = repository.getAllRecords()
        val today = getNowLocalDateTime().date

        val todayRecords = records.filter {
            try { parseDisplayDate(it.date) == today } catch (e: Exception) { false }
        }

        val isToday = todayRecords.isNotEmpty()
        val latestRecord = if (isToday) {
            todayRecords.maxByOrNull { it.createdAt }
        } else {
            records.maxByOrNull { it.createdAt }
        }

        val latestGlucoseValue = latestRecord?.let { getGlucoseValue(it) }
        val lastMealPeriod = TimePeriod.fromString(latestRecord?.mealType ?: "")

        // ✅ Calculate accurate status based on meal period
        val targetData = latestGlucoseValue?.let {
            calculateGlucoseTargets(
                valueMgMl = it.toDouble() / 100.0,
                mealType = lastMealPeriod,
                hasDiabetes = true
            )
        }

        val glucoseStatus = targetData?.statusText ?: getString(Res.string.normal)

        val sevenDayRecords = records.filter {
            try {
                val recordDate = parseDisplayDate(it.date)
                recordDate >= today.minus(7, DateTimeUnit.DAY) && recordDate <= today
            } catch (e: Exception) {
                false
            }
        }

        val sevenDayReadings = sevenDayRecords.flatMap {
            listOfNotNull(
                it.beforeBreakfast, it.afterBreakfast, it.beforeLunch,
                it.afterLunch, it.beforeDinner, it.afterDinner, it.bedtime
            )
        }

        val sevenDayAverage = if (sevenDayReadings.isNotEmpty()) sevenDayReadings.average().toInt() else 0
        val hbA1cEstimate = if (sevenDayAverage > 0) (sevenDayAverage + 46.7) / 28.7 else 0.0

        val chartReadings = (0..6).reversed().map { dayOffset ->
            val date = today.minus(dayOffset, DateTimeUnit.DAY)
            val dayReadings = records.filter {
                try { parseDisplayDate(it.date) == date } catch (e: Exception) { false }
            }.flatMap {
                listOfNotNull(it.beforeBreakfast, it.afterBreakfast, it.beforeLunch, it.afterLunch, it.beforeDinner, it.afterDinner, it.bedtime)
            }
            if (dayReadings.isNotEmpty()) dayReadings.average().toFloat() else 0f
        }

        val dailyAverageReadings = (0..6).reversed().map { dayOffset ->
            val date = today.minus(dayOffset, DateTimeUnit.DAY)
            val dateString = "${date.dayOfMonth} ${date.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }}"
            val dayReadings = records.filter {
                try { parseDisplayDate(it.date) == date } catch (e: Exception) { false }
            }.flatMap {
                listOfNotNull(it.beforeBreakfast, it.afterBreakfast, it.beforeLunch, it.afterLunch, it.beforeDinner, it.afterDinner, it.bedtime)
            }
            DailyAverageReading(dateString, if (dayReadings.isNotEmpty()) dayReadings.average().toFloat() else 0f)
        }

        return GlucoseDashboard(
            todayGlucose = latestGlucoseValue,
            glucoseStatus = glucoseStatus,
            isToday = isToday,
            sevenDayAverage = sevenDayAverage,
            hbA1cEstimate = hbA1cEstimate,
            highestGlucose = if (sevenDayReadings.isNotEmpty()) sevenDayReadings.maxOrNull() ?: 0 else 0,
            lowestGlucose = if (sevenDayReadings.isNotEmpty()) sevenDayReadings.minOrNull() ?: 0 else 0,
            chartReadings = chartReadings,
            recordedTime = latestRecord?.time ?: "",
            mealType = formatMealType(latestRecord?.mealType ?: ""),
            lastMealPeriod = lastMealPeriod,
            recentRecords = getRecentRecords(records),
            dailyAverageReadings = dailyAverageReadings,
            last7Readings = getLast7Readings(records)
        )
    }

    private fun getLast7Readings(records: List<GlucoseRecord>): List<RecentReading> {
        return records.sortedByDescending { it.createdAt }.flatMap { record ->
            listOfNotNull(
                record.beforeBreakfast?.let { RecentReading(record.date, "Before Breakfast", it, record.time, record.notes) },
                record.afterBreakfast?.let { RecentReading(record.date, "After Breakfast", it, record.time, record.notes) },
                record.beforeLunch?.let { RecentReading(record.date, "Before Lunch", it, record.time, record.notes) },
                record.afterLunch?.let { RecentReading(record.date, "After Lunch", it, record.time, record.notes) },
                record.beforeDinner?.let { RecentReading(record.date, "Before Dinner", it, record.time, record.notes) },
                record.afterDinner?.let { RecentReading(record.date, "After Dinner", it, record.time, record.notes) },
                record.bedtime?.let { RecentReading(record.date, "Bedtime", it, record.time, record.notes) }
            )
        }.take(7)
    }

    private suspend fun getRecentRecords(records: List<GlucoseRecord>): List<DashboardRecentRecord> {
        val sortedRecords = records.sortedByDescending {
            try { parseDisplayDate(it.date) } catch (e: Exception) { LocalDate(1900, 1, 1) }
        }
        val recentItems = mutableListOf<DashboardRecentRecord>()
        for (record in sortedRecords) {
            val dayReadings = listOf(
                "BEDTIME" to record.bedtime,
                "AFTER_DINNER" to record.afterDinner,
                "BEFORE_DINNER" to record.beforeDinner,
                "AFTER_LUNCH" to record.afterLunch,
                "BEFORE_LUNCH" to record.beforeLunch,
                "AFTER_BREAKFAST" to record.afterBreakfast,
                "BEFORE_BREAKFAST" to record.beforeBreakfast
            )
            for (item in dayReadings) {
                if (item.second != null) {
                    recentItems.add(DashboardRecentRecord(record.date, formatMealType(item.first), item.second!!, record.time))
                    if (recentItems.size >= 3) return recentItems
                }
            }
        }
        return recentItems
    }

    private fun getGlucoseValue(record: GlucoseRecord): Int {
        return when (record.mealType) {
            "BBF", "BEFORE_BREAKFAST" -> record.beforeBreakfast
            "ABF", "AFTER_BREAKFAST" -> record.afterBreakfast
            "BL", "BEFORE_LUNCH" -> record.beforeLunch
            "AL", "AFTER_LUNCH" -> record.afterLunch
            "BD", "BEFORE_DINNER" -> record.beforeDinner
            "AD", "AFTER_DINNER" -> record.afterDinner
            "BT", "BEDTIME", "NGT" -> record.bedtime
            else -> null
        } ?: record.bedtime ?: record.afterDinner ?: record.beforeDinner ?: record.afterLunch
        ?: record.beforeLunch ?: record.afterBreakfast ?: record.beforeBreakfast ?: 0
    }

    private suspend fun formatMealType(type: String): String {
        return when (type) {
            "BBF", "BEFORE_BREAKFAST" -> getString(Res.string.before_breakfast)
            "ABF", "AFTER_BREAKFAST" -> getString(Res.string.after_breakfast)
            "BL", "BEFORE_LUNCH" -> getString(Res.string.before_lunch)
            "AL", "AFTER_LUNCH" -> getString(Res.string.after_lunch)
            "BD", "BEFORE_DINNER" -> getString(Res.string.before_dinner)
            "AD", "AFTER_DINNER" -> getString(Res.string.after_dinner)
            "BT", "BEDTIME", "NGT" -> getString(Res.string.bedtime)
            else -> type
        }
    }
}
