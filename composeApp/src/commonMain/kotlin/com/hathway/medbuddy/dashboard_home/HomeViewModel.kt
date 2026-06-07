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
    val insightEmoji: String = ""
)

enum class GlucoseStatus {
    Low, Normal, AboveTarget, High
}

data class RecentRecord(
    val date: String, val timePeriod: String, val value: Int
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

                val todayRecord = records.firstOrNull { it.date == todayDateString }
                val todayGlucose = todayRecord?.beforeBreakfast

                val glucoseStatus = when {
                    todayGlucose == null -> GlucoseStatus.Normal
                    todayGlucose < 70 -> GlucoseStatus.Low
                    todayGlucose <= 100 -> GlucoseStatus.Normal
                    todayGlucose <= 140 -> GlucoseStatus.AboveTarget
                    else -> GlucoseStatus.High
                }

                val sevenDayAverage = calculateSevenDayAverage(records, today)
                val hbA1cEstimate = calculateHbA1cEstimate(sevenDayAverage)
                val greeting = getGreeting()
                val recordedTime = "7:45 AM"

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
                val highestGlucose = allReadings.maxOrNull() ?: 0
                val lowestGlucose = allReadings.minOrNull() ?: 0
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
                        recordedTime = recordedTime,
                        sevenDayAverage = sevenDayAverage,
                        hbA1cEstimate = hbA1cEstimate,
                        greeting = greeting,
                        highestGlucose = highestGlucose,
                        lowestGlucose = lowestGlucose,
                        recentRecords = recentRecords,
                        medications = medications,
                        insight = insight,
                        insightEmoji = insightEmoji
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private fun getRecentRecords(records: List<GlucoseRecord>): List<RecentRecord> {
        return records.take(3).map { record ->
            val timePeriod = when {
                record.beforeBreakfast != null -> "BBF"
                record.afterBreakfast != null -> "ABF"
                record.beforeLunch != null -> "BL"
                record.afterLunch != null -> "AL"
                record.beforeDinner != null -> "BD"
                record.afterDinner != null -> "AD"
                record.bedtime != null -> "BT"
                else -> "N/A"
            }
            val value = when (timePeriod) {
                "BBF" -> record.beforeBreakfast
                "ABF" -> record.afterBreakfast
                "BL" -> record.beforeLunch
                "AL" -> record.afterLunch
                "BD" -> record.beforeDinner
                "AD" -> record.afterDinner
                "BT" -> record.bedtime
                else -> null
            }
            RecentRecord(
                date = record.date, timePeriod = timePeriod, value = value ?: 0
            )
        }
    }

    private fun calculateSevenDayAverage(
        records: List<GlucoseRecord>, today: kotlinx.datetime.LocalDate
    ): Int {
        val sevenDaysAgo = today.minus(7, kotlinx.datetime.DateTimeUnit.DAY)
        val recentRecords = records.filter {
            try {
                val recordDate = parseDisplayDate(it.date)
                recordDate >= sevenDaysAgo && recordDate <= today
            } catch (e: Exception) {
                false
            }
        }

        val allReadings = recentRecords.flatMap {
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

        return if (allReadings.isNotEmpty()) {
            allReadings.average().toInt()
        } else {
            0
        }
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
}

