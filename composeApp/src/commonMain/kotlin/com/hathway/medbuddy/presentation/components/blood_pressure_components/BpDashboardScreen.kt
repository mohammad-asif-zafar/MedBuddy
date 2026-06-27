package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.icons.KmpComposeIcons
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.SuccessContainer
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import com.hathway.medbuddy.presentation.viewmodel.BpDashboardViewModel

@Composable
fun BpDashboardScreen(
    onBack: () -> Unit,
    onViewHistoryClick: () -> Unit,
    navigationToCalendar: () -> Unit,
    navigationToAddReading: () -> Unit,
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
            Spacer(modifier = Modifier.height(24.dp))

            // NEW: Quick Actions Section Header
            Text(
                text = "Quick Actions", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
                ), modifier = Modifier.padding(bottom = 16.dp)
            )

            // NEW: Quick Actions Grid Row
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionButton(
                    label = "Add Reading",
                    icon = Icons.Default.Add,
                    containerColor = Color(0xFFE8F0FE), // Light Blue
                    iconColor = Color(0xFF1A73E8),
                    onClick = { navigationToAddReading()})
                QuickActionButton(
                    label = "Reminders",
                    icon = Icons.Default.Notifications,
                    containerColor = Color(0xFFF1EEFD), // Light Purple
                    iconColor = Color(0xFF7A56F5),
                    onClick = { /* Handle navigation */ })
                QuickActionButton(
                    label = "Reports",
                    icon = Icons.Default.Description,
                    containerColor = Color(0xEFEFFBF0), // Light Green
                    iconColor = Color(0xFF34A853),
                    onClick = { /* Handle navigation */ })
                QuickActionButton(
                    label = "Share",
                    icon = Icons.Default.Share,
                    containerColor = Color(0xFFFCEFEA), // Light Orange
                    iconColor = Color(0xFFE94235),
                    onClick = { /* Handle navigation */ })
            }

            Spacer(modifier = Modifier.height(16.dp))
        }


        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview(name = "Dashboard Light Mode", showBackground = true)
@Composable
fun BpDashboardLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        BpDashboardScreen(onBack = {}, onViewHistoryClick = {}, navigationToCalendar = {}, navigationToAddReading = {})
    }
}

@Preview(name = "Dashboard Dark Mode", showBackground = true)
@Composable
fun BpDashboardDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        BpDashboardScreen(onBack = {}, onViewHistoryClick = {}, navigationToCalendar = {}, navigationToAddReading = {})
    }
}
