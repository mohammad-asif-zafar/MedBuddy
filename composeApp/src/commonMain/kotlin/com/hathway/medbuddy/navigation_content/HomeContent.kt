package com.hathway.medbuddy.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.dashboard_home.HomeScreen
import com.hathway.medbuddy.dashboard_home.HomeViewModel
import com.hathway.medbuddy.repository.IGlucoseRepository

@Composable
fun HomeContent(repository: IGlucoseRepository) {
    HomeScreen(viewModel = HomeViewModel(repository))
}