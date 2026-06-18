package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.components.reports_components.AverageGlucoseByTimeOfDay
import com.hathway.medbuddy.presentation.components.reports_components.BestAndWorstDaysSection
import com.hathway.medbuddy.presentation.components.reports_components.GlucoseTrendCard
import com.hathway.medbuddy.presentation.components.reports_components.InsightsAndActionsFooter
import com.hathway.medbuddy.presentation.components.reports_components.ReportsTopBarAndFilter
import com.hathway.medbuddy.presentation.components.reports_components.SummaryMetricsSection
import com.hathway.medbuddy.presentation.components.reports_components.TimeInRangeCard
import com.hathway.medbuddy.presentation.components.reports_components.TimeInRangeData

@Composable
fun ReportsScreen(
    onMenuClick: () -> Unit,
    onCalendarClick: () -> Unit,
    onExportPdf: () -> Unit,
    onShareReport: () -> Unit
) {
    // Shared brand palette config
    val backgroundColor = Color(0xFFF7F7EE)

    // Dynamic Filter state tracking holder
    var currentFilterRange by remember { mutableStateOf("30 Days") }

    // Hardcoded static dataset values matching your reference visual breakdown profile
    val simulatedTimeInRangeData = TimeInRangeData(inRangePct = 78f, highPct = 15f, lowPct = 7f)

    // Insights text mapping dataset matching reports footer block
    val reportInsightsList = remember {
        listOf(
            Icons.Default.CheckCircle to "Most of your readings are within the target range. Great job!",
            Icons.Default.ArrowCircleUp to "After lunch readings tend to be higher than other times.",
            Icons.Default.TrendingUp to "Average glucose improved by 8% compared to last 30 days.",
            Icons.Default.CheckCircle to "No low glucose episodes in the last 7 days."
        )
    }

    Scaffold(
        topBar = {
            ReportsTopBarAndFilter(
                selectedFilter = currentFilterRange,
                onFilterSelected = { currentFilterRange = it },
                onMenuClick = onMenuClick,
                onCalendarClick = onCalendarClick
            )
        }, containerColor = backgroundColor
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp) // Handled via inner modular sub-paddings
        ) {

            // 1. High-Level Performance Matrix Grid
            item {
                SummaryMetricsSection(
                    avgGlucose = 124, hba1c = 5.9, timeInRange = 78, totalReadings = 142
                )
            }

            // 2. Custom Canvas Donut Time In Range Breakdown Card
            item {
                TimeInRangeCard(data = simulatedTimeInRangeData)
            }

            // 3. Optional Graph Section Placeholder
            // item { GlucoseTrendGraphCard() }

            // 4. Meal Segment Time of Day Analysis Matrix Block
            item {
                AverageGlucoseByTimeOfDay(
                    beforeBreakfast = 95,
                    afterBreakfast = 132,
                    beforeLunch = 102,
                    afterLunch = 148,
                    beforeDinner = 110,
                    bedtime = 120,
                    afterDinner = 120
                )
            }
            item {
                GlucoseTrendCard()
            }

            // 5. High & Low Extremes Performance Highlights Block
            item {
                BestAndWorstDaysSection(
                    bestDate = "12 June 2026",
                    bestAvg = 89,
                    worstDate = "5 June 2026",
                    worstAvg = 242
                )
            }

            // 6. Automated Insight List & Double Action Footer
            item {
                InsightsAndActionsFooter(
                    insights = reportInsightsList,
                    onExportPdf = onExportPdf,
                    onShareReport = onShareReport
                )
            }

            // Layout buffer space anchor at the bottom of the column screen track
            item {
                Spacer(modifier = Modifier.navigationBarsPadding().height(16.dp))
            }
        }
    }
}

