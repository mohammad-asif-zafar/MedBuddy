package com.hathway.medbuddy.dashboard_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.data.GlucoseRecord
import com.hathway.medbuddy.repository.IGlucoseRepository
import com.hathway.medbuddy.FirebaseManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

data class HomeUiState(
    val isLoading: Boolean = true,

    val patientName: String = "",
    val patientEmail: String = "",
    val patientPhotoUrl: String = "",
    val greeting: String = "Good Morning",

    val condition: String = "Type 2 Diabetes",
    val todayGlucose: Int? = null,
    val glucoseStatus: GlucoseStatus = GlucoseStatus.Normal,
    val glucoseStatusText: String = "Normal",
    val recordedTime: String = "",
    val sevenDayAverage: Int = 0,
    val hbA1cEstimate: Double = 0.0,

    val doctorName: String = "Dr. Sumit Gulla ",
    val doctorSpecialty: String = "Endocrinologist",
    val nextVisitDate: String = "15 Jun 2026",
    val daysUntilVisit: Int = 12,
    val targetProgress: Int = 60,
    val targetReadings: Int = 18,
    val totalTargetReadings: Int = 30,
    val highestGlucose: Int = 0,
    val lowestGlucose: Int = 0,
    val recentRecords: List<RecentRecord> = emptyList(),
    val medications: List<Medication> = emptyList(),
    val insight: String = "",
    val insightEmoji: String = "",

    val averageGlucose: Double = 0.0,
    val glucoseTrend: Double = 0.0,

    val lastReading: Int = 0,
    val lastReadingTime: String = "",
    val lastMealType: String = ""
)

enum class GlucoseStatus {
    Low, Normal, AboveTarget, High
}

data class RecentRecord(
    val date: String, val timePeriod: String, val value: Int, val time: String
)

data class Medication(
    val id: Int,
    val name: String = "",
    val time: String = "",
    val taken: Boolean = false,
    val dosage: String = "",
    val isTaken: Boolean = false,
    val mealType: String = ""// Before Breakfast, After Lunch, etc.
)

class HomeViewModel(
    private val repository: IGlucoseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCurrentUser()
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val records = repository.getAllRecords()
                val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
                val todayDateString = formatDate(today)

                val todayRecords = records.filter { it.date == todayDateString }
                val latestTodayRecord = todayRecords.maxByOrNull { it.createdAt }
                val todayGlucose =
                    if (latestTodayRecord != null) getGlucoseValue(latestTodayRecord) else null

                val glucoseStatus = when {
                    todayGlucose == null -> GlucoseStatus.Normal
                    todayGlucose < 70 -> GlucoseStatus.Low
                    todayGlucose <= 100 -> GlucoseStatus.Normal
                    todayGlucose <= 140 -> GlucoseStatus.AboveTarget
                    else -> GlucoseStatus.High
                }

                val sevenDayRecords = records.filter {
                    try {
                        val recordDate = parseDisplayDate(it.date)
                        recordDate >= today.minus(
                            7, kotlinx.datetime.DateTimeUnit.DAY
                        ) && recordDate <= today
                    } catch (e: Exception) {
                        false
                    }
                }
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

                val sevenDayAverage =
                    if (sevenDayReadings.isNotEmpty()) sevenDayReadings.average().toInt() else 0
                val hbA1cEstimate = calculateHbA1cEstimate(sevenDayAverage)
                val greeting = getGreeting()
                val recordedTime = latestTodayRecord?.time ?: ""

                val allReadings = records.flatMap {
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
                // Find the chronologically latest record (day)
                val sortedRecords = records.sortedByDescending {
                    try {
                        parseDisplayDate(it.date)
                    } catch (e: Exception) {
                        kotlinx.datetime.LocalDate(1900, 1, 1)
                    }
                }

                val lastRecord = sortedRecords.firstOrNull()

                // For the last record, we want to find the latest time period that has a value
                val lastReadingValue = getLatestGlucoseValueFromRecord(lastRecord)
                val lastReadingMealType = getLatestMealTypeFromRecord(lastRecord)

                val average = if (allReadings.isNotEmpty()) {
                    (allReadings.average() * 10).toInt() / 10.0
                } else {
                    0.0
                }

                val highestGlucose =
                    if (sevenDayReadings.isNotEmpty()) sevenDayReadings.maxOrNull() ?: 0 else 0
                val lowestGlucose =
                    if (sevenDayReadings.isNotEmpty()) sevenDayReadings.minOrNull() ?: 0 else 0
                val recentRecords = getRecentRecords(records)

                val medications = listOf(
                    Medication(
                        id = 1,
                        name = "Metformin",
                        dosage = "500mg",
                        time = "8:00 AM",
                        isTaken = true,
                        mealType = "BBF"
                    ), Medication(
                        id = 2,
                        name = "Bisoprolol",
                        time = "9:00 PM",
                        dosage = "500mg",
                        isTaken = false,
                        mealType = "BDT"
                    )
                )

                val insight = "Glucose is 15% lower than last week"
                val insightEmoji = "📈"

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        todayGlucose = todayGlucose,
                        glucoseStatus = glucoseStatus,
                        glucoseStatusText = formatStatus(glucoseStatus),
                        recordedTime = recordedTime,
                        sevenDayAverage = sevenDayAverage,
                        hbA1cEstimate = hbA1cEstimate,
                        greeting = greeting,
                        highestGlucose = highestGlucose,
                        lowestGlucose = lowestGlucose,
                        recentRecords = recentRecords,
                        medications = medications,
                        insight = insight,
                        insightEmoji = insightEmoji,
                        averageGlucose = average,
                        lastReading = lastReadingValue,
                        lastReadingTime = lastRecord?.time ?: "",
                        lastMealType = formatMealType(lastReadingMealType)
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun formatStatus(status: GlucoseStatus): String {
        return when (status) {
            GlucoseStatus.AboveTarget -> "Above Target"
            else -> status.name
        }
    }

    private fun getRecentRecords(records: List<GlucoseRecord>): List<RecentRecord> {
        val sortedRecords = records.sortedByDescending {
            try {
                parseDisplayDate(it.date)
            } catch (e: Exception) {
                kotlinx.datetime.LocalDate(1900, 1, 1)
            }
        }

        val recentItems = mutableListOf<RecentRecord>()

        for (record in sortedRecords) {
            // Within each day, we want to show the readings in reverse chronological order
            val dayReadings = listOf(
                "BEDTIME" to record.bedtime,
                "AFTER_DINNER" to record.afterDinner,
                "BEFORE_DINNER" to record.beforeDinner,
                "AFTER_LUNCH" to record.afterLunch,
                "BEFORE_LUNCH" to record.beforeLunch,
                "AFTER_BREAKFAST" to record.afterBreakfast,
                "BEFORE_BREAKFAST" to record.beforeBreakfast
            )

            for ((type, value) in dayReadings) {
                if (value != null) {
                    recentItems.add(
                        RecentRecord(
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

    private fun calculateHbA1cEstimate(averageGlucose: Int): Double {
        // Formula: (Average Glucose + 46.7) / 28.7
        return if (averageGlucose > 0) {
            ((averageGlucose + 46.7) / 28.7)
        } else {
            0.0
        }
    }

    private fun getGreeting(): String {
        val hour = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).hour
        return when {
            hour < 12 -> "Good Morning 👋"
            hour < 17 -> "Good Afternoon 👋"
            else -> "Good Evening 👋"
        }
    }

    private fun formatDate(date: kotlinx.datetime.LocalDate): String {
        val month = date.month.name.lowercase().replaceFirstChar { it.uppercase() }
        return "${date.dayOfMonth} $month ${date.year}"
    }

    private fun parseDisplayDate(date: String): kotlinx.datetime.LocalDate {
        val parts = date.split(" ")
        val day = parts[0].toInt()
        val monthName = parts[1].lowercase()
        val year = parts[2].toInt()

        val month = when (monthName) {
            "january" -> 1
            "february" -> 2
            "march" -> 3
            "april" -> 4
            "may" -> 5
            "june" -> 6
            "july" -> 7
            "august" -> 8
            "september" -> 9
            "october" -> 10
            "november" -> 11
            "december" -> 12
            else -> 1
        }

        return kotlinx.datetime.LocalDate(year, month, day)
    }

    private fun loadCurrentUser() {

        val user = FirebaseManager.currentUser

        _uiState.update {

            it.copy(
                patientName = user?.displayName ?: "Patient",
                patientEmail = user?.email ?: "",
                patientPhotoUrl = user?.photoUrl ?: ""
            )
        }

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

    private fun getLatestGlucoseValueFromRecord(record: GlucoseRecord?): Int {
        if (record == null) return 0
        return record.bedtime ?: record.afterDinner ?: record.beforeDinner ?: record.afterLunch
        ?: record.beforeLunch ?: record.afterBreakfast ?: record.beforeBreakfast ?: 0
    }

    private fun getLatestMealTypeFromRecord(record: GlucoseRecord?): String {
        if (record == null) return ""
        return when {
            record.bedtime != null -> "BEDTIME"
            record.afterDinner != null -> "AFTER_DINNER"
            record.beforeDinner != null -> "BEFORE_DINNER"
            record.afterLunch != null -> "AFTER_LUNCH"
            record.beforeLunch != null -> "BEFORE_LUNCH"
            record.afterBreakfast != null -> "AFTER_BREAKFAST"
            record.beforeBreakfast != null -> "BEFORE_BREAKFAST"
            else -> ""
        }
    }

    private fun formatMealType(type: String): String {
        return when (type) {
            "BBF", "BEFORE_BREAKFAST" -> "Before Breakfast"
            "ABF", "AFTER_BREAKFAST" -> "After Breakfast"
            "BL", "BEFORE_LUNCH" -> "Before Lunch"
            "AL", "AFTER_LUNCH" -> "After Lunch"
            "BD", "BEFORE_DINNER" -> "Before Dinner"
            "AD", "AFTER_DINNER" -> "After Dinner"
            "BT", "BEDTIME", "NGT" -> "Bedtime"
            else -> type
        }
    }
}

