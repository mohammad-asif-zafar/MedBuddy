package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.datetime.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString

data class GlucoseDashboard(
    val todayGlucose: Int?,
    val glucoseStatus: String,
    val sevenDayAverage: Int,
    val hbA1cEstimate: Double,
    val highestGlucose: Int,
    val lowestGlucose: Int,
    val chartReadings: List<Float>,
    val recordedTime: String,
    val mealType: String,
    val recentRecords: List<DashboardRecentRecord>
)

data class DashboardRecentRecord(
    val date: String,
    val timePeriod: String,
    val value: Int,
    val time: String
)

class GetGlucoseDashboardUseCase(
    private val repository: IGlucoseRepository
) {
    suspend operator fun invoke(): GlucoseDashboard {
        val records = repository.getAllRecords()
        val today = getNowLocalDateTime().date
        val todayDateString = formatDate(today)

        val todayRecords = records.filter { it.date == todayDateString }
        val latestTodayRecord = todayRecords.maxByOrNull { it.createdAt }
        val todayGlucose = if (latestTodayRecord != null) getGlucoseValue(latestTodayRecord) else null

        val glucoseStatus = when {
            todayGlucose == null -> getString(Res.string.normal)
            todayGlucose < 70 -> getString(Res.string.low)
            todayGlucose <= 100 -> getString(Res.string.normal)
            todayGlucose <= 140 -> getString(Res.string.above_target)
            else -> getString(Res.string.high)
        }

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
        val hbA1cEstimate = if (sevenDayAverage > 0) ((sevenDayAverage + 46.7) / 28.7) else 0.0
        val recordedTime = latestTodayRecord?.time ?: ""

        val highestGlucose = if (sevenDayReadings.isNotEmpty()) sevenDayReadings.maxOrNull() ?: 0 else 0
        val lowestGlucose = if (sevenDayReadings.isNotEmpty()) sevenDayReadings.minOrNull() ?: 0 else 0

        val chartReadings = (0..6).reversed().map { dayOffset ->
            val date = today.minus(dayOffset, DateTimeUnit.DAY)
            val dateString = formatDate(date)
            val dayRecords = records.filter { it.date == dateString }
            val dayReadings = dayRecords.flatMap {
                listOfNotNull(
                    it.beforeBreakfast, it.afterBreakfast, it.beforeLunch,
                    it.afterLunch, it.beforeDinner, it.afterDinner, it.bedtime
                )
            }
            if (dayReadings.isNotEmpty()) dayReadings.average().toFloat() else 0f
        }

        val recentRecords = getRecentRecords(records)

        return GlucoseDashboard(
            todayGlucose = todayGlucose,
            glucoseStatus = glucoseStatus,
            sevenDayAverage = sevenDayAverage,
            hbA1cEstimate = hbA1cEstimate,
            highestGlucose = highestGlucose,
            lowestGlucose = lowestGlucose,
            chartReadings = chartReadings,
            recordedTime = recordedTime,
            mealType = formatMealType(latestTodayRecord?.mealType ?: ""),
            recentRecords = recentRecords
        )
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
                val type = item.first
                val value = item.second
                if (value != null) {
                    recentItems.add(
                        DashboardRecentRecord(
                            date = record.date,
                            timePeriod = formatMealType(type),
                            value = value,
                            time = record.time
                        )
                    )
                    if (recentItems.size >= 3) return recentItems
                }
            }
        }
        return recentItems
    }

    private fun formatDate(date: LocalDate): String {
        val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
        return "${date.dayOfMonth} $month ${date.year}"
    }

    private fun parseDisplayDate(date: String): LocalDate {
        val parts = date.split(" ")
        val day = parts[0].toInt()
        val monthName = parts[1].lowercase()
        val year = parts[2].toInt()

        val month = when (monthName) {
            "january" -> 1; "february" -> 2; "march" -> 3; "april" -> 4; "may" -> 5; "june" -> 6
            "july" -> 7; "august" -> 8; "september" -> 9; "october" -> 10; "november" -> 11; "december" -> 12
            else -> 1
        }
        return LocalDate(year, month, day)
    }

    private fun getGlucoseValue(record: GlucoseRecord?): Int {
        if (record == null) return 0
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
