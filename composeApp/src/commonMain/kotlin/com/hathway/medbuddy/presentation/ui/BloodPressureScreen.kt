@file:Suppress("DEPRECATION")

package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.icons.KmpComposeIcons
import com.hathway.medbuddy.presentation.components.blood_pressure_components.NavigationOptionCard
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import com.hathway.medbuddy.presentation.viewmodel.BloodPressureHomeViewModel
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.title_blood_pressure
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun BloodPressureHomeScreen(
    onBack: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToInsights: () -> Unit,
    onNavigateToAddReading: () -> Unit,
    viewModel: BloodPressureHomeViewModel = viewModel { BloodPressureHomeViewModel() }
) {
    val state = viewModel.uiState.collectAsState().value
    BackHandler(enabled = true) {
        onBack()
    }
    // FIX: Removed "paddingValues ->" since DetailedReportWrapper only exposes ColumnScope
    DetailedReportWrapper(stringResource(Res.string.title_blood_pressure), onBack) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Scrollable view payload
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState())
                    .padding(horizontal = 0.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Icon(
                    imageVector = KmpComposeIcons.HeartPulseGauge,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Track. Understand. Improve.",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Keep track of your blood pressure and take charge of your heart health.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(36.dp))

                NavigationOptionCard(
                    title = "Dashboard",
                    description = "View your BP overview and latest reading",
                    icon = KmpComposeIcons.TrendChart,
                    onClick = onNavigateToDashboard
                )

                Spacer(modifier = Modifier.height(12.dp))

                NavigationOptionCard(
                    title = "History",
                    description = "View all your past readings",
                   // icon = Icons.Default.DateRange,
                    icon = KmpComposeIcons.CalendarCheck,
                    onClick = onNavigateToHistory
                )

                Spacer(modifier = Modifier.height(12.dp))

                NavigationOptionCard(
                    title = "Insights",
                    description = "Trends, stats and personalized insights",
                    icon = KmpComposeIcons.RadiantLightbulb,
                    onClick = onNavigateToInsights
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Bottom Fixed Action Callout Button
            Button(
                onClick = onNavigateToAddReading,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp).height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add BP Reading", style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}


@Preview(name = "Light Mode", showBackground = true)
@Composable
fun BloodPressureHomeScreenLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        BloodPressureHomeScreen(
            onBack = {},
            onNavigateToDashboard = {},
            onNavigateToHistory = {},
            onNavigateToInsights = {},
            onNavigateToAddReading = {})
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun BloodPressureHomeScreenDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        BloodPressureHomeScreen(
            onBack = {},
            onNavigateToDashboard = {},
            onNavigateToHistory = {},
            onNavigateToInsights = {},
            onNavigateToAddReading = {})
    }
}
