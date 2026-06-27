package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import com.hathway.medbuddy.presentation.viewmodel.BpDashboardViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.icons.KmpComposeIcons
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.theme.Primary
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.SuccessContainer

@Composable
fun BpDashboardScreen(
    onBack: () -> Unit,
    onViewHistoryClick: () -> Unit,
    navigationToCalendar: () -> Unit,
    viewModel: BpDashboardViewModel = viewModel { BpDashboardViewModel() }
) {
    val state = viewModel.uiState.collectAsState().value
    val bp_state = viewModel.uiBpState.collectAsState().value

    DetailedReportWrapper(
        "BP Dashboard",
        onBack,
        showRightAction = true,
        rightIcon = KmpComposeIcons.CalendarCheck,
        onRightActionClick = navigationToCalendar
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                .padding(horizontal = 0.dp, vertical = 0.dp)
        ) {
            LatestReadingCard(
                date = state.date,
                systolic = state.systolic,
                diastolic = state.diastolic,
                pulse = state.pulse,
                statusLabel = state.statusLabel,
                statusColor = StatusInRange,
                statusContainerColor = SuccessContainer,
                modifier = Modifier.padding(1.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
            BpRangeSlider()

            Spacer(modifier = Modifier.height(12.dp))
            BpTrendCard(
                points = bp_state.trendData, onViewHistoryClick = onViewHistoryClick
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "View History", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    // FIX: Changed color to your custom BrandGreen token
                    color = com.hathway.medbuddy.presentation.theme.BrandGreen
                ), modifier = Modifier.clickable { onViewHistoryClick() }.padding(vertical = 4.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Preview(name = "Dashboard Light Mode", showBackground = true)
@Composable
fun BpDashboardLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        BpDashboardScreen(onBack = {}, onViewHistoryClick = {}, navigationToCalendar = {})
    }
}

@Preview(name = "Dashboard Dark Mode", showBackground = true)
@Composable
fun BpDashboardDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        BpDashboardScreen(onBack = {}, onViewHistoryClick = {}, navigationToCalendar = {})
    }
}
