package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.components.home_components.TrendChartCard
import com.hathway.medbuddy.presentation.components.reports_components.*
import com.hathway.medbuddy.presentation.navigation.NavigationDestination
import com.hathway.medbuddy.presentation.viewmodel.ReportsViewModel
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.ic_circle_arrow_up
import medbuddy.composeapp.generated.resources.ic_circle_check
import medbuddy.composeapp.generated.resources.ic_shield_check
import medbuddy.composeapp.generated.resources.ic_target_range
import medbuddy.composeapp.generated.resources.insight_glucose_improved
import medbuddy.composeapp.generated.resources.insight_lunch_spike
import medbuddy.composeapp.generated.resources.insight_no_lows
import medbuddy.composeapp.generated.resources.insight_within_range

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel,
    onMenuClick: () -> Unit,
    onMoreOptionClick: (String) -> Unit = {},
    onFeatureClick: (NavigationDestination) -> Unit = {},
    onExportPdf: () -> Unit = {},
    onShareReport: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    // Insights data mapping matrix cleanly decoupled into string resource packages
    val reportInsightsList = remember {
        listOf(
            Res.drawable.ic_circle_check to Res.string.insight_within_range,
            Res.drawable.ic_circle_arrow_up to Res.string.insight_lunch_spike,
            Res.drawable.ic_target_range to Res.string.insight_glucose_improved,
            Res.drawable.ic_shield_check to Res.string.insight_no_lows,
        )
    }

    Scaffold(
        topBar = {
            ReportsTopBarAndFilter(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = { viewModel.onFilterSelected(it) },
                onMenuClick = {},
                onMoreOptionClick = {},
                enableMenu = false
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            uiState.reportsData?.let { data ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp) // Handled via inner modular sub-paddings
                ) {

                    // 1. High-Level Performance Matrix Grid
                    item {
                        SummaryMetricsSection(
                            avgGlucose = data.avgGlucose,
                            hba1c = data.hba1c,
                            timeInRange = data.timeInRange,
                            totalReadings = data.totalReadings,
                            selectedFilterDays = uiState.selectedFilter,
                        )
                    }

                    // 2. Custom Canvas Donut Time In Range Breakdown Card
                    item {
                        TimeInRangeCard(data = data.timeInRangeData)
                    }

                    // 3. Meal Segment Time of Day Analysis Matrix Block
                    item {
                        AverageGlucoseByTimeOfDay(
                            beforeBreakfast = data.avgByTimeOfDay.beforeBreakfast,
                            afterBreakfast = data.avgByTimeOfDay.afterBreakfast,
                            beforeLunch = data.avgByTimeOfDay.beforeLunch,
                            afterLunch = data.avgByTimeOfDay.afterLunch,
                            beforeDinner = data.avgByTimeOfDay.beforeDinner,
                            bedtime = data.avgByTimeOfDay.bedtime,
                            afterDinner = data.avgByTimeOfDay.afterDinner
                        )
                    }

                    // 3. Chart Segment TrendChartCard
                    item {
                        TrendChartCard(readings = data.trendChartReadings)
                    }

                    // 5. High & Low Extremes Performance Highlights Block
                    item {
                        BestAndWorstDaysSection(
                            bestDate = data.bestDate,
                            bestAvg = data.bestAvg,
                            worstDate = data.worstDate,
                            worstAvg = data.worstAvg
                        )
                    }

                    // 6. Automated Insight List & Double Action Footer
                    item {
                        InsightsAndActionsFooter(
                            insights = reportInsightsList
                        )
                    }

                    // 7. Feature Cards Grid
                    item {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(0.dp),
                            maxItemsInEachRow = 2
                        ) {
                            val cardModifier = Modifier.fillMaxWidth(0.5f)
                            
                            Box(modifier = cardModifier) { AIInsightsCard(onClick = { onFeatureClick(NavigationDestination.AI_INSIGHTS) }) }
                            Box(modifier = cardModifier) { MealTrackingCard(onClick = { onFeatureClick(NavigationDestination.MEAL_TRACKING) }) }
                            Box(modifier = cardModifier) { MedicationAdherenceCard(onClick = { onFeatureClick(NavigationDestination.MEDICATION_ADHERENCE) }) }
                            Box(modifier = cardModifier) { HealthReportsCard(onClick = { onFeatureClick(NavigationDestination.HEALTH_REPORTS_DETAIL) }) }
                            Box(modifier = cardModifier) { FamilyCareCard(onClick = { onFeatureClick(NavigationDestination.FAMILY_CARE) }) }
                            Box(modifier = cardModifier) { EmergencyAlertsCard(onClick = { onFeatureClick(NavigationDestination.EMERGENCY_ALERTS) }) }
                            Box(modifier = cardModifier) { ExerciseTrackingCard(onClick = { onFeatureClick(NavigationDestination.EXERCISE_TRACKING) }) }
                            Box(modifier = cardModifier) { WeightBMICard(onClick = { onFeatureClick(NavigationDestination.WEIGHT_BMI) }) }
                            Box(modifier = cardModifier) { BloodPressureTrackingCard(onClick = { onFeatureClick(NavigationDestination.BLOOD_PRESSURE) }) }
                            Box(modifier = cardModifier) { DoctorAppointmentsCard(onClick = { onFeatureClick(NavigationDestination.DOCTOR_APPOINTMENTS_DETAIL) }) }
                            Box(modifier = cardModifier) { DarkModeFeatureCard(onClick = { /* Already handled elsewhere? or profile */ }) }
                        }
                    }

                    // Layout buffer space anchor at the bottom of the column screen track
                    item {
                        Spacer(modifier = Modifier.navigationBarsPadding().height(4.dp))
                    }
                }
            }
        }
    }
}

