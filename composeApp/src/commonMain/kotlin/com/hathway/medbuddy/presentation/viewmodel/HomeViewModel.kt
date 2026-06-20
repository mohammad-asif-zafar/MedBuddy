package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.usecase.GetGlucoseDashboardUseCase
import com.hathway.medbuddy.domain.usecase.GetNotificationsUseCase
import com.hathway.medbuddy.FirebaseManager
import com.hathway.medbuddy.domain.usecase.DailyAverageReading
import com.hathway.medbuddy.domain.usecase.RecentReading
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString

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

    val chartReadings: List<Float> = emptyList(),

    val averageGlucose: Double = 0.0,
    val glucoseTrend: Double = 0.0,

    val lastReading: Int = 0,
    val lastReadingTime: String = "",
    val lastMealType: String = "",
    val isToday: Boolean = true,
    val lastMealPeriod: TimePeriod = TimePeriod.BEFORE_BREAKFAST,
    val dailyAverageReadings: List<DailyAverageReading> = emptyList(),
    val last7Readings: List<RecentReading> = emptyList(),
    val hasUnreadNotifications: Boolean = false
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
    private val repository: IGlucoseRepository,
    private val doctorRepository: IDoctorRepository
) : ViewModel() {

    private val getGlucoseDashboardUseCase = GetGlucoseDashboardUseCase(repository)
    private val getNotificationsUseCase = GetNotificationsUseCase(repository, doctorRepository)

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
                val dashboard = getGlucoseDashboardUseCase()
                val greeting = getGreeting()
                val notifications = getNotificationsUseCase()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        todayGlucose = dashboard.todayGlucose,
                        glucoseStatusText = dashboard.glucoseStatus,
                        recordedTime = dashboard.recordedTime,
                        sevenDayAverage = dashboard.sevenDayAverage,
                        hbA1cEstimate = dashboard.hbA1cEstimate,
                        greeting = greeting,
                        highestGlucose = dashboard.highestGlucose,
                        lowestGlucose = dashboard.lowestGlucose,
                        chartReadings = dashboard.chartReadings,
                        recentRecords = dashboard.recentRecords.map {
                            RecentRecord(it.date, it.timePeriod, it.value, it.time)
                        },
                        // Medications remains same for now
                        medications = listOf(
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
                        ),
                        insight = getString(Res.string.insight_lower),
                        insightEmoji = "📈",
                        averageGlucose = dashboard.sevenDayAverage.toDouble(), // Or actual average
                        lastReading = dashboard.todayGlucose ?: 0,
                        lastReadingTime = dashboard.recordedTime,
                        lastMealType = dashboard.mealType,
                        isToday = dashboard.isToday,
                        lastMealPeriod = dashboard.lastMealPeriod,
                        dailyAverageReadings = dashboard.dailyAverageReadings,
                        last7Readings = dashboard.last7Readings,
                        hasUnreadNotifications = notifications.isNotEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    private suspend fun getGreeting(): String {
        val hour = getNowLocalDateTime().hour
        return when {
            hour < 12 -> getString(Res.string.greeting_morning)
            hour < 17 -> getString(Res.string.greeting_afternoon)
            else -> getString(Res.string.greeting_evening)
        }
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

