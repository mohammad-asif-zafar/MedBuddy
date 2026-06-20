package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.HomeScreen
import com.hathway.medbuddy.presentation.viewmodel.HomeViewModel
import com.hathway.medbuddy.domain.repository.IGlucoseRepository

@Composable
fun HomeContent(repository: IGlucoseRepository,
                onOpenNotifications: () -> Unit) {
    HomeScreen(viewModel = HomeViewModel(repository),onOpenNotifications)
}