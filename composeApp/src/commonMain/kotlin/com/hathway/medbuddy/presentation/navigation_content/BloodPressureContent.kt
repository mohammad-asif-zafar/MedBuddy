package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.presentation.navigation.NavigationDestination
import com.hathway.medbuddy.presentation.ui.BloodPressureHomeScreen
import com.hathway.medbuddy.presentation.viewmodel.BloodPressureHomeViewModel

@Composable
fun BloodPressureContent(currentDestination:MutableState<NavigationDestination>){

    BloodPressureHomeScreen(onBack = {
        currentDestination.value = NavigationDestination.HOME
    }, onNavigateToDashboard = {
        currentDestination.value =
            NavigationDestination.BLOOD_PRESSURE_DASHBOARD_SCREEN
    }, onNavigateToHistory = {
        currentDestination.value =
            NavigationDestination.BLOOD_PRESSURE_HISTORY_SCREEN
    }, onNavigateToInsights = {
        currentDestination.value =
            NavigationDestination.BLOOD_PRESSURE_INSIGHTS_SCREEN
    }, onNavigateToAddReading = {
        currentDestination.value =
            NavigationDestination.BLOOD_PRESSURE_ADD_READING_SCREEN
    })
}
