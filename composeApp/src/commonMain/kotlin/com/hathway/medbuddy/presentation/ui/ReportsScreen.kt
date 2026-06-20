package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.hathway.medbuddy.presentation.components.reports_components.AverageGlucoseByTimeOfDay
import com.hathway.medbuddy.presentation.components.reports_components.BestAndWorstDaysSection
import com.hathway.medbuddy.presentation.components.reports_components.InsightsAndActionsFooter
import com.hathway.medbuddy.presentation.components.reports_components.ReportsTopBarAndFilter
import com.hathway.medbuddy.presentation.components.reports_components.SummaryMetricsSection
import com.hathway.medbuddy.presentation.components.reports_components.TimeInRangeCard
import com.hathway.medbuddy.presentation.components.reports_components.TrendChartCard
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

@Composable
fun ReportsScreen(
    viewModel: ReportsViewModel,
    onMenuClick: () -> Unit,
    onCalendarClick: () -> Unit,
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
            Res.drawable.ic_shield_check to Res.string.insight_no_lows
        )
    }



    Scaffold(
        topBar = {
            ReportsTopBarAndFilter(
                selectedFilter = uiState.selectedFilter,
                onFilterSelected = { viewModel.onFilterSelected(it) },
                onMenuClick = onMenuClick,
                onCalendarClick = onCalendarClick,
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
                        TrendChartCard(readings = data.trendChartReadings, modifier = Modifier)
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

                    // Layout buffer space anchor at the bottom of the column screen track
                    item {
                        Spacer(modifier = Modifier.navigationBarsPadding().height(4.dp))
                    }
                }
            }
        }
    }
}

