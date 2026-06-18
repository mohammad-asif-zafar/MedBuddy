package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.components.home_components.BloodGlucoseCard
import com.hathway.medbuddy.presentation.components.home_components.HealthSummaryGrid
import com.hathway.medbuddy.presentation.components.home_components.HomeTopBar
import com.hathway.medbuddy.presentation.components.home_components.PatientGreetingCard
import com.hathway.medbuddy.presentation.components.home_components.RecentRecordsCard
import com.hathway.medbuddy.presentation.components.home_components.TrendChartCard
import com.hathway.medbuddy.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().background(
            MaterialTheme.colorScheme.background
        )
    ) {

        // Header background
        Box(
            modifier = Modifier.fillMaxWidth().background(
                Color(0xFFF8F4EC)
            )
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Home Tool Bar
            item {
                HomeTopBar()
            }
            // Section 1: Greeting with patient info
            item {
                PatientGreetingCard(
                    greeting = uiState.greeting,
                    patientName = uiState.patientName
                )
            }
            //  Section 2:  Today's Glucose
            item {
                BloodGlucoseCard(
                    glucoseValue = uiState.lastReading,
                    mealType = uiState.lastMealType,
                    status =  uiState.glucoseStatusText,
                    targetRange = "70-100 mg/dL"
                )

            }
            //  Section 3: Doctor Information

            //1. Android only OR Compose Multiplatform?
            //2. Can I use Vico library? (Yes/No)
            item {
                println("si"+uiState.chartReadings)
                TrendChartCard(
                    readings = uiState.chartReadings,
                    average = uiState.sevenDayAverage.toDouble(),
                    highest = uiState.highestGlucose.toDouble(),
                    lowest = uiState.lowestGlucose.toDouble(),
                    recentRecords = uiState.recentRecords

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
                println("size:"+uiState.recentRecords)
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
}

