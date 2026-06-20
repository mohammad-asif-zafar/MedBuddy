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
import com.hathway.medbuddy.presentation.navigation_content.NotificationContent
import com.hathway.medbuddy.presentation.navigation_content.ProfileContent
import com.hathway.medbuddy.presentation.navigation_content.ReportsContent
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme

@Composable
fun App(
    repository: IGlucoseRepository? = null, doctorRepository: IDoctorRepository? = null
) {
    val currentDestination = remember { mutableStateOf(NavigationDestination.HOME) }

    MedBuddyTheme {
        Scaffold(
            bottomBar = {
                // ✅ Hide bottom bar when viewing full notification screens
                if (currentDestination.value != NavigationDestination.NOTIFICATIONS) {
                    SimpleBottomNavigationBar(
                        currentDestination = currentDestination.value,
                        onDestinationSelected = { currentDestination.value = it })
                }
            }) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                when (currentDestination.value) {
                    NavigationDestination.HOME -> {
                        repository?.let { initRepository ->
                            HomeContent(
                                repository = initRepository,
                                // ✅ Forward top bar notifications click up into state manager
                                onOpenNotifications = {
                                    currentDestination.value = NavigationDestination.NOTIFICATIONS
                                })
                        }
                    }

                    NavigationDestination.HISTORY -> HistoryContent(repository)
                    NavigationDestination.ADD -> AddContent(repository = repository)
                    NavigationDestination.REPORTS -> ReportsContent(repository)
                    NavigationDestination.PROFILE -> ProfileContent(doctorRepository)

                    // Map screen entry cleanly into state framework
                    NavigationDestination.NOTIFICATIONS -> {
                        NotificationContent(
                            repository = repository,
                            doctorRepository = doctorRepository,
                            onBack = {
                                // Return to previous standard screen route
                                currentDestination.value = NavigationDestination.HOME
                            }
                        )
                    }
                }
            }
        }
    }
}
