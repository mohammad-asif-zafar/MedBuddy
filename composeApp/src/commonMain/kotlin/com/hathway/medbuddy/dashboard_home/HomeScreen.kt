package com.hathway.medbuddy.dashboard_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.dashboard_home.components.BloodGlucoseCard
import com.hathway.medbuddy.dashboard_home.components.DoctorInfoCard
import com.hathway.medbuddy.dashboard_home.components.HealthSummaryGrid
import com.hathway.medbuddy.dashboard_home.components.MedicationReminderCard
import com.hathway.medbuddy.dashboard_home.components.PatientGreetingCard
import com.hathway.medbuddy.dashboard_home.components.QuickInsightsCard
import com.hathway.medbuddy.dashboard_home.components.RecentRecordsCard
import com.hathway.medbuddy.dashboard_home.components.TargetProgressCard
import com.hathway.medbuddy.dashboard_home.components.TrendChartCard

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section 1: Greeting with patient info
        item {
            PatientGreetingCard(
                greeting = uiState.greeting,
                patientName = uiState.patientName,
                condition = uiState.condition
            )
        }

        // Section 2: Today's Glucose
        item {
            BloodGlucoseCard(
                average = 7.4,
                trend = 0.3,
                status = "In Range",

                )

        }

        // Section 3: Doctor Information
        item {
            DoctorInfoCard(
                doctorName = uiState.doctorName,
                doctorSpecialty = uiState.doctorSpecialty,
                nextVisitDate = uiState.nextVisitDate,
                daysUntilVisit = uiState.daysUntilVisit
            )
        }

        // Section 4: Quick Insights
        item {
            QuickInsightsCard(
                insight = uiState.insight, insightEmoji = uiState.insightEmoji
            )
        }

        // Section 5: Medication Reminder
        item {
            MedicationReminderCard(
                medications = uiState.medications
            )
        }

        // Section 6: Target Progress
        item {
            TargetProgressCard(
                targetProgress = uiState.targetProgress,
                targetReadings = uiState.targetReadings,
                totalTargetReadings = uiState.totalTargetReadings
            )
        }

        // Section 7: Last 3 Records
        item {
            RecentRecordsCard(
                recentRecords = uiState.recentRecords
            )
        }

        // Section 8: Health Summary (4 cards in grid)
        item {
            HealthSummaryGrid(
                average = uiState.sevenDayAverage,
                hbA1c = uiState.hbA1cEstimate,
                highest = uiState.highestGlucose,
                lowest = uiState.lowestGlucose
            )
        }

        // Section 9: 7 Day Trend Chart (placeholder)
        item {
            TrendChartCard()
        }
    }
}













