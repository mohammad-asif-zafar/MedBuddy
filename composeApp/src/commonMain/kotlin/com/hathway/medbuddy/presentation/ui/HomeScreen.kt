package com.hathway.medbuddy.presentation.ui

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
import com.hathway.medbuddy.presentation.components.home_components.BloodGlucoseCard
import com.hathway.medbuddy.presentation.components.home_components.HealthSummaryGrid
import com.hathway.medbuddy.presentation.components.home_components.PatientGreetingCard
import com.hathway.medbuddy.presentation.components.home_components.RecentRecordsCard
import com.hathway.medbuddy.presentation.components.home_components.TrendChartCard
import com.hathway.medbuddy.presentation.viewmodel.HomeViewModel

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
                readings = uiState.chartReadings,
                average = uiState.sevenDayAverage.toDouble(),
                highest = uiState.highestGlucose.toDouble(),
                lowest = uiState.lowestGlucose.toDouble()
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

