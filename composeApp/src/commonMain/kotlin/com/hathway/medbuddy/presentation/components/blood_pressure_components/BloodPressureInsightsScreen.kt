package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.icons.KmpComposeIcons
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.title_blood_pressure_history
import org.jetbrains.compose.resources.stringResource

@Composable
fun BloodPressureInsightsScreen(back: () -> Unit, navigateToCalendar: () -> Unit) {
    DetailedReportWrapper(
        rightIcon = KmpComposeIcons.CalendarCheck,
        showRightAction = true,
        onRightActionClick = { navigateToCalendar() },
        title = stringResource(Res.string.title_blood_pressure_history),
        onBack = { back() }) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // 1. Overall Summary Card
            item {
                OverallSummaryCard()
            }

            // 2. BP Trend Section
            item {
                BpTrendCard()
            }

            // 3. Recommendations Section
            item {
                RecommendationsCard()
            }
        }
    }
}

