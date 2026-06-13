package com.hathway.medbuddy.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.HomeScreen
import com.hathway.medbuddy.presentation.viewmodel.HomeViewModel
import com.hathway.medbuddy.repository.IGlucoseRepository

@Composable
fun HomeContent(repository: IGlucoseRepository) {
    HomeScreen(viewModel = HomeViewModel(repository))
}