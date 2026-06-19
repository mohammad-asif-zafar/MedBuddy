package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.usecase.DailyAverageReading
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.datetime.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString
import kotlin.collections.List

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
    val date: String, val timePeriod: String, val value: Int, val time: String
)

class GetGlucoseDashboardUseCase(
    private val repository: IGlucoseRepository
) {
    /**
     * Builds the complete dashboard data used by Home Screen.
     *
     * This use case:
     * 1. Gets all glucose records from database
     * 2. Finds today's latest reading
     * 3. Calculates glucose status
     * 4. Calculates 7-day statistics
     * 5. Prepares chart data
     * 6. Prepares recent readings list
     */
    suspend operator fun invoke(): GlucoseDashboard {

        // Get all glucose records stored in database
        val records = repository.getAllRecords()

        // Current device date
        val today = getNowLocalDateTime().date

        // Convert current date to app display format
        // Example: "15 June 2026"
        val todayDateString = formatDate(today)

        /**
         * Get only today's records
         */
        val todayRecords = records.filter {
            it.date == todayDateString
        }

        /**
         * Find latest record of today
         *
         * Example:
         * 08:00 AM
         * 12:00 PM
         * 08:00 PM ← selected
         */
        val latestTodayRecord = todayRecords.maxByOrNull {
            it.createdAt
        }

        /**
         * Extract actual glucose value from latest record
         *
         * Example:
         * Before Breakfast = 100
         * After Lunch = null
         * Bedtime = null
         *
         * Returns 100
         */
        val todayGlucose = if (latestTodayRecord != null) getGlucoseValue(latestTodayRecord)
        else null

        /**
         * Determine glucose status text
         *
         * < 70          = Low
         * 70 - 100      = Normal
         * 101 - 140     = Above Target
         * > 140         = High
         */
        val glucoseStatus = when {

            todayGlucose == null -> getString(Res.string.normal)

            todayGlucose < 70 -> getString(Res.string.low)

            todayGlucose <= 100 -> getString(Res.string.normal)

            todayGlucose <= 140 -> getString(Res.string.above_target)

            else -> getString(Res.string.high)
        }

        /**
         * Get last 7 days records
         *
         * Example:
         * Today = 15 June
         *
         * Includes:
         * 09 June
         * 10 June
         * 11 June
         * 12 June
         * 13 June
         * 14 June
         * 15 June
         */
        val sevenDayRecords = records.filter {

            try {

                val recordDate = parseDisplayDate(it.date)

                recordDate >= today.minus(7, DateTimeUnit.DAY) && recordDate <= today

            } catch (e: Exception) {

                false
            }
        }

        /**
         * Flatten all glucose values into a single list
         *
         * Example:
         * [100, 120, 95, 110, 130, 90]
         */
        val sevenDayReadings = sevenDayRecords.flatMap {

            listOfNotNull(
                it.beforeBreakfast,
                it.afterBreakfast,
                it.beforeLunch,
                it.afterLunch,
                it.beforeDinner,
                it.afterDinner,
                it.bedtime
            )
        }

        /**
         * Average glucose over 7 days
         *
         * Example:
         * [100,120,90]
         *
         * Average = 103
         */
        val sevenDayAverage = if (sevenDayReadings.isNotEmpty()) sevenDayReadings.average().toInt()
        else 0

        /**
         * Estimated HbA1c
         *
         * Formula:
         *
         * HbA1c = (Average Glucose + 46.7) / 28.7
         *
         * Example:
         * Average = 124
         *
         * HbA1c ≈ 5.9%
         */
        val hbA1cEstimate = if (sevenDayAverage > 0) (sevenDayAverage + 46.7) / 28.7
        else 0.0

        /**
         * Latest reading time
         *
         * Example:
         * 08:15 AM
         */
        val recordedTime = latestTodayRecord?.time ?: ""

        /**
         * Highest glucose value in last 7 days
         *
         * Example:
         * 310
         */
        val highestGlucose = if (sevenDayReadings.isNotEmpty()) sevenDayReadings.maxOrNull() ?: 0
        else 0

        /**
         * Lowest glucose value in last 7 days
         *
         * Example:
         * 60
         */
        val lowestGlucose = if (sevenDayReadings.isNotEmpty()) sevenDayReadings.minOrNull() ?: 0
        else 0

        /**
         * Chart data for 7 days
         *
         * Returns one value per day.
         *
         * Example:
         * [
         *   120f,
         *   110f,
         *   100f,
         *   180f,
         *   150f,
         *   130f,
         *   120f
         * ]
         *
         * Used by TrendChartCard()
         */
        val chartReadings = (0..6).reversed().map { dayOffset ->

            val date = today.minus(dayOffset, DateTimeUnit.DAY)

            val dateString = formatDate(date)

            val dayRecords = records.filter {
                it.date == dateString
            }

            val dayReadings = dayRecords.flatMap {

                listOfNotNull(
                    it.beforeBreakfast,
                    it.afterBreakfast,
                    it.beforeLunch,
                    it.afterLunch,
                    it.beforeDinner,
                    it.afterDinner,
                    it.bedtime
                )
            }

            if (dayReadings.isNotEmpty()) dayReadings.average().toFloat()
            else 0f
        }

        /**
         * Last few readings shown on Home Screen
         *
         * Example:
         * 100 mg/dL
         * Before Breakfast
         *
         * 90 mg/dL
         * Before Lunch
         */
        val recentRecords = getRecentRecords(records)

        /**
         * Final dashboard object
         *
         * Used by HomeScreen UI
         */
        val dailyAverageReadings = (0..6).reversed().map { dayOffset ->

            val date = today.minus(dayOffset, DateTimeUnit.DAY)

            val dateString = formatDate(date)

            val dayRecords = records.filter {
                it.date == dateString
            }

            val dayReadings = dayRecords.flatMap {

                listOfNotNull(
                    it.beforeBreakfast,
                    it.afterBreakfast,
                    it.beforeLunch,
                    it.afterLunch,
                    it.beforeDinner,
                    it.afterDinner,
                    it.bedtime
                )
            }

            DailyAverageReading(
                date = "${date.dayOfMonth} ${
                    date.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }
                }", averageValue = if (dayReadings.isNotEmpty()) dayReadings.average().toFloat()
                else 0f)
        }
        return GlucoseDashboard(

            // Today's latest glucose value
            todayGlucose = todayGlucose,

            // Low / Normal / Above Target / High
            glucoseStatus = glucoseStatus,

            // 7 day average glucose
            sevenDayAverage = sevenDayAverage,

            // Estimated HbA1c %
            hbA1cEstimate = hbA1cEstimate,

            // Highest reading in last 7 days
            highestGlucose = highestGlucose,

            // Lowest reading in last 7 days
            lowestGlucose = lowestGlucose,

            // Chart values
            chartReadings = chartReadings,

            // Latest reading time
            recordedTime = recordedTime,

            // Human readable meal type
            mealType = formatMealType(
                latestTodayRecord?.mealType ?: ""
            ),

            lastMealPeriod = TimePeriod.fromString(latestTodayRecord?.mealType ?: ""),

            // Recent readings list
            recentRecords = recentRecords,
            dailyAverageReadings = dailyAverageReadings,
            last7Readings = getLast7Readings(records),

            )
    }


    private fun getLast7Readings(
        records: List<GlucoseRecord>
    ): List<RecentReading> {

        return records.sortedByDescending { it.createdAt }.flatMap { record ->

            listOfNotNull(

                record.beforeBreakfast?.let {
                    RecentReading(
                        date = record.date,
                        timePeriod = "Before Breakfast",
                        value = it,
                        time = record.time
                    )
                },

                record.afterBreakfast?.let {
                    RecentReading(
                        date = record.date,
                        timePeriod = "After Breakfast",
                        value = it,
                        time = record.time
                    )
                },

                record.beforeLunch?.let {
                    RecentReading(
                        date = record.date,
                        timePeriod = "Before Lunch",
                        value = it,
                        time = record.time
                    )
                },

                record.afterLunch?.let {
                    RecentReading(
                        date = record.date,
                        timePeriod = "After Lunch",
                        value = it,
                        time = record.time
                    )
                },

                record.beforeDinner?.let {
                    RecentReading(
                        date = record.date,
                        timePeriod = "Before Dinner",
                        value = it,
                        time = record.time
                    )
                },

                record.afterDinner?.let {
                    RecentReading(
                        date = record.date,
                        timePeriod = "After Dinner",
                        value = it,
                        time = record.time
                    )
                },

                record.bedtime?.let {
                    RecentReading(
                        date = record.date, timePeriod = "Bedtime", value = it, time = record.time
                    )
                })
        }.take(7)
    }

    private suspend fun getRecentRecords(records: List<GlucoseRecord>): List<DashboardRecentRecord> {
        val sortedRecords = records.sortedByDescending {
            try {
                parseDisplayDate(it.date)
            } catch (e: Exception) {
                LocalDate(1900, 1, 1)
            }
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
