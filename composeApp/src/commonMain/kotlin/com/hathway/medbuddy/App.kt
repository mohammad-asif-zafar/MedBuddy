package com.hathway.medbuddy

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.hathway.medbuddy.domain.repository.IDoctorRepository
import com.hathway.medbuddy.domain.repository.IGlucoseRepository
import com.hathway.medbuddy.presentation.navigation.NavigationDestination
import com.hathway.medbuddy.presentation.navigation.SimpleBottomNavigationBar
import com.hathway.medbuddy.presentation.navigation_content.AddContent
import com.hathway.medbuddy.presentation.navigation_content.HistoryContent
import com.hathway.medbuddy.presentation.navigation_content.HomeContent
import com.hathway.medbuddy.presentation.navigation_content.ProfileContent
import com.hathway.medbuddy.presentation.navigation_content.ReportsContent
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme

@Composable
fun App(
    repository: IGlucoseRepository? = null,
    doctorRepository: IDoctorRepository? = null
) {
    // For now, we'll use a simple state instead of viewModel
    val currentDestination = remember { mutableStateOf(NavigationDestination.HOME) }

    MedBuddyTheme {
        Scaffold(
            bottomBar = {
                SimpleBottomNavigationBar(
                    currentDestination = currentDestination.value,
                    onDestinationSelected = { currentDestination.value = it })
            }) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                when (currentDestination.value) {
                    NavigationDestination.HOME -> {
                        repository?.let { HomeContent(it) }
                    }
                    NavigationDestination.HISTORY -> HistoryContent(repository)
                    NavigationDestination.ADD -> AddContent(repository = repository)
                    NavigationDestination.REPORTS -> ReportsContent(repository)
                    NavigationDestination.PROFILE -> ProfileContent(doctorRepository)
                }
            }
        }
    }
}
