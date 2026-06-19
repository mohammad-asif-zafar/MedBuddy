package com.hathway.medbuddy.domain.usecase

import com.hathway.medbuddy.domain.model.GlucoseRecord
import com.hathway.medbuddy.domain.model.TimeInRangeData
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.util.getNowLocalDateTime
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString

data class ReportsData(
    val avgGlucose: Int,
    val hba1c: Double,
    val timeInRange: Int,
    val totalReadings: Int,
    val timeInRangeData: TimeInRangeData,
    val avgByTimeOfDay: AvgByTimeOfDay,
    val trendChartReadings: List<DailyAverageReading>,
    val bestDate: String,
    val bestAvg: Int,
    val worstDate: String,
    val worstAvg: Int
)

data class AvgByTimeOfDay(
    val beforeBreakfast: Int,
    val afterBreakfast: Int,
    val beforeLunch: Int,
    val afterLunch: Int,
    val beforeDinner: Int,
    val afterDinner: Int,
    val bedtime: Int
)

class GetReportsUseCase(
    private val repository: IGlucoseRepository
) {
    suspend operator fun invoke(days: Int): ReportsData {
        val allRecords = repository.getAllRecords()
        val today = getNowLocalDateTime().date
        
        val filteredRecords = allRecords.filter {
            try {
                val recordDate = parseDisplayDate(it.date)
                recordDate >= today.minus(days, DateTimeUnit.DAY) && recordDate <= today
            } catch (e: Exception) {
                false
            }
        }

        val allReadings = filteredRecords.flatMap {
            listOfNotNull(
                it.beforeBreakfast, it.afterBreakfast, it.beforeLunch,
                it.afterLunch, it.beforeDinner, it.afterDinner, it.bedtime
            )
        }

        val avgGlucose = if (allReadings.isNotEmpty()) allReadings.average().toInt() else 0
        val hba1c = if (avgGlucose > 0) (avgGlucose + 46.7) / 28.7 else 0.0
        
        // Time in range calculation (70-140 as example range)
        val inRange = allReadings.count { it in 70..140 }
        val high = allReadings.count { it > 140 }
        val low = allReadings.count { it < 70 }
        val total = allReadings.size
        
        val inRangePct = if (total > 0) (inRange.toFloat() / total * 100) else 0f
        val highPct = if (total > 0) (high.toFloat() / total * 100) else 0f
        val lowPct = if (total > 0) (low.toFloat() / total * 100) else 0f

        // Average by time of day
        val avgByTime = AvgByTimeOfDay(
            beforeBreakfast = filteredRecords.mapNotNull { it.beforeBreakfast }.let { if (it.isNotEmpty()) it.average().toInt() else 0 },
            afterBreakfast = filteredRecords.mapNotNull { it.afterBreakfast }.let { if (it.isNotEmpty()) it.average().toInt() else 0 },
            beforeLunch = filteredRecords.mapNotNull { it.beforeLunch }.let { if (it.isNotEmpty()) it.average().toInt() else 0 },
            afterLunch = filteredRecords.mapNotNull { it.afterLunch }.let { if (it.isNotEmpty()) it.average().toInt() else 0 },
            beforeDinner = filteredRecords.mapNotNull { it.beforeDinner }.let { if (it.isNotEmpty()) it.average().toInt() else 0 },
            afterDinner = filteredRecords.mapNotNull { it.afterDinner }.let { if (it.isNotEmpty()) it.average().toInt() else 0 },
            bedtime = filteredRecords.mapNotNull { it.bedtime }.let { if (it.isNotEmpty()) it.average().toInt() else 0 }
        )

        // Trend chart (daily averages)
        val trendReadings = (0 until days).reversed().map { dayOffset ->
            val date = today.minus(dayOffset, DateTimeUnit.DAY)
            val dateString = "${date.dayOfMonth} ${date.month.name.take(3).lowercase().replaceFirstChar { it.uppercase() }}"
            
            val dayReadings = allRecords.filter { parseDisplayDate(it.date) == date }.flatMap {
                listOfNotNull(it.beforeBreakfast, it.afterBreakfast, it.beforeLunch, it.afterLunch, it.beforeDinner, it.afterDinner, it.bedtime)
            }
            DailyAverageReading(dateString, if (dayReadings.isNotEmpty()) dayReadings.average().toFloat() else 0f)
        }

        // Best and Worst days
        val dailyAverages = filteredRecords.map { record ->
            val readings = listOfNotNull(record.beforeBreakfast, record.afterBreakfast, record.beforeLunch, record.afterLunch, record.beforeDinner, record.afterDinner, record.bedtime)
            record.date to (if (readings.isNotEmpty()) readings.average().toInt() else 0)
        }.filter { it.second > 0 }

        val best = dailyAverages.minByOrNull { it.second }
        val worst = dailyAverages.maxByOrNull { it.second }

        return ReportsData(
            avgGlucose = avgGlucose,
            hba1c = hba1c,
            timeInRange = inRangePct.toInt(),
            totalReadings = total,
            timeInRangeData = TimeInRangeData(inRangePct, highPct, lowPct),
            avgByTimeOfDay = avgByTime,
            trendChartReadings = trendReadings,
            bestDate = best?.first ?: "N/A",
            bestAvg = best?.second ?: 0,
            worstDate = worst?.first ?: "N/A",
            worstAvg = worst?.second ?: 0
        )
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
