package com.hathway.medbuddy.dashboard_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.dashboard_home.components.BloodGlucoseCard
import com.hathway.medbuddy.dashboard_home.components.HealthSummaryGrid
import com.hathway.medbuddy.dashboard_home.components.PatientGreetingCard
import com.hathway.medbuddy.dashboard_home.components.RecentRecordsCard
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
                patientEmail = uiState.patientEmail,
                patientPhotoUrl = uiState.patientPhotoUrl,
                condition = uiState.condition
            )
        }

        //  Section 2:  Today's Glucose
        item {
            BloodGlucoseCard(
                average = uiState.averageGlucose,
                trend = uiState.glucoseTrend,
                status = uiState.glucoseStatusText,
                lastReading = uiState.lastReading.toDouble(),
                lastReadingTime = uiState.lastReadingTime,
                lastMealType = uiState.lastMealType
            )

        }

        //  Section 3: Doctor Information
        item {
            TrendChartCard(
                readings = listOf(
                    160f, 180f, 140f, 220f, 190f, 170f, 200f
                ), average = 180.0, highest = 220.0, lowest = 140.0
            )
        }

        // Section 4: Health Summary (4 cards in grid)
        item {
            HealthSummaryGrid(
                average = uiState.sevenDayAverage,
                hbA1c = uiState.hbA1cEstimate,
                highest = uiState.highestGlucose,
                lowest = uiState.lowestGlucose
            )
        }


        // Section 6: Last 3 Records
        item {
            RecentRecordsCard(
                recentRecords = uiState.recentRecords
            )
        }
        // Section 7: bottom space
        item {
            Spacer(
                modifier = Modifier.height(6.dp)
            )
        }

    }


}













