package com.hathway.medbuddy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.GlucoseRecord
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.getString

data class HomeUiState(
    val isLoading: Boolean = true,

    val patientName: String = "",
    val patientEmail: String = "",
    val patientPhotoUrl: String = "",
    val greeting: String = "",

    val condition: String = "",
    val todayGlucose: Int? = null,
    val glucoseStatus: GlucoseStatus = GlucoseStatus.Normal,
    val glucoseStatusText: String = "",
    val recordedTime: String = "",
    val sevenDayAverage: Int = 0,
    val hbA1cEstimate: Double = 0.0,

    val doctorName: String = "",
    val doctorSpecialty: String = "",
    val nextVisitDate: String = "",
    val daysUntilVisit: Int = 0,
    val targetProgress: Int = 0,
    val targetReadings: Int = 0,
    val totalTargetReadings: Int = 0,
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
        observeRecords()
    }

    private fun observeRecords() {
        viewModelScope.launch {
            repository.recordsFlow.collectLatest { records ->
                refreshDashboard(records)
            }
        }
    }

    private fun refreshDashboard(records: List<GlucoseRecord>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = it.todayGlucose == null) } // Only show full loader on first load

            try {
                coroutineScope {
                    val dashboardDeferred = async { getGlucoseDashboardUseCase(records) }
                    val greetingDeferred = async { getGreeting() }
                    val notificationsDeferred = async { getNotificationsUseCase(records) }

                    val dashboard = dashboardDeferred.await()
                    val greeting = greetingDeferred.await()
                    val notifications = notificationsDeferred.await()

                    // Pre-fetch localized strings outside the update lambda
                    val conditionStr = getString(Res.string.condition_default)
                    val doctorNameStr = getString(Res.string.doctor_name_default)
                    val doctorSpecialtyStr = getString(Res.string.speciality_default)
                    val nextVisitDateStr = getString(Res.string.date_default)
                    val insightStr = getString(Res.string.insight_lower)

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            todayGlucose = dashboard.todayGlucose,
                            glucoseStatusText = dashboard.glucoseStatus,
                            recordedTime = dashboard.recordedTime,
                            sevenDayAverage = dashboard.sevenDayAverage,
                            hbA1cEstimate = dashboard.hbA1cEstimate,
                            greeting = greeting,
                            condition = conditionStr,
                            doctorName = doctorNameStr,
                            doctorSpecialty = doctorSpecialtyStr,
                            nextVisitDate = nextVisitDateStr,
                            highestGlucose = dashboard.highestGlucose,
                            lowestGlucose = dashboard.lowestGlucose,
                            chartReadings = dashboard.chartReadings,
                            recentRecords = dashboard.recentRecords.map { r ->
                                RecentRecord(r.date, r.timePeriod, r.value, r.time)
                            },
                            medications = emptyList(), // Remove hardcoded medications
                            insight = if (records.isNotEmpty()) insightStr else "",
                            insightEmoji = if (records.isNotEmpty()) "📈" else "",
                            averageGlucose = dashboard.sevenDayAverage.toDouble(),
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
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
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

        viewModelScope.launch {
            val defaultPatientName = getString(Res.string.patient)
            _uiState.update {
                it.copy(
                    patientName = user?.displayName ?: defaultPatientName,
                    patientEmail = user?.email ?: "",
                    patientPhotoUrl = user?.photoUrl ?: ""
                )
            }
        }
    }
}
