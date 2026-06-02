package com.hathway.medbuddy.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.dashboard_home.HomeScreen
import com.hathway.medbuddy.dashboard_home.HomeViewModel

@Composable
fun HomeContent() {
    HomeScreen(viewModel = HomeViewModel())
}