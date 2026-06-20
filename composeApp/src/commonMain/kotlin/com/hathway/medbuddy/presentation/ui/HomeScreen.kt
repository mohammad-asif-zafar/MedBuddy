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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.components.home_components.HealthSummaryGrid
import com.hathway.medbuddy.presentation.components.home_components.IntegratedGlucoseCardSection
import com.hathway.medbuddy.presentation.components.home_components.MedBuddyTopBar
import com.hathway.medbuddy.presentation.components.home_components.RecentRecordsCard
import com.hathway.medbuddy.presentation.components.home_components.TrendChartCard
import com.hathway.medbuddy.presentation.viewmodel.HomeViewModel
import com.hathway.medbuddy.util.calculateGlucoseTargets
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.medbuddy
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onOpenNotifications: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().background(
            MaterialTheme.colorScheme.background
        )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(start = 12.dp, end = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Home Tool Bar
            item {
                MedBuddyTopBar(
                    title = stringResource(Res.string.medbuddy),
                    leftIcon = Icons.Default.Menu,
                    rightIcon = Icons.Outlined.Notifications,
                    onLeftClick = { },
                    onRightClick = { onOpenNotifications() },
                    titleColor = MaterialTheme.colorScheme.primary,
                    showBadge = uiState.hasUnreadNotifications
                )
            }
            // Section 1: Greeting with patient info
            // Section 2:  Today's Glucose
            item {
                // Determine the correct meal period enum safely
                val currentPeriod = uiState.lastMealPeriod
                val targetData = calculateGlucoseTargets(
                    valueMgMl = uiState.lastReading.toDouble() / 100.0,
                    mealType = currentPeriod,
                    hasDiabetes = true
                )
                IntegratedGlucoseCardSection(
                    greeting = uiState.greeting,
                    patientName = uiState.patientName,
                    glucoseValue = uiState.lastReading,
                    mealType = uiState.lastMealType,
                    status = uiState.glucoseStatusText,
                    minTarget = (targetData.minTarget * 100).toInt().toString(),
                    maxTarget = (targetData.maxTarget * 100).toInt().toString(),
                    isToday = uiState.isToday
                )
            }
            //  Section 3: Doctor Information
            item {
                TrendChartCard(
                    readings = uiState.dailyAverageReadings
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
                    recentRecords = uiState.last7Readings
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

