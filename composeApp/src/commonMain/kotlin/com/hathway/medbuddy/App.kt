package com.hathway.medbuddy

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.hathway.medbuddy.navigation.*
import com.hathway.medbuddy.navigation_content.HomeContent
import com.hathway.medbuddy.navigation_content.ProfileContent
import com.hathway.medbuddy.repository.IGlucoseRepository
import com.hathway.medbuddy.screens.*
import com.hathway.medbuddy.ui.MedBuddyTheme

@Composable
fun App(
    repository: IGlucoseRepository? = null
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
                    // NavigationDestination.HOME -> HomeScreen()
                    NavigationDestination.HOME -> {
                        repository?.let { HomeContent(it) }
                    }

                    NavigationDestination.ADD -> AddScreen(repository = repository)
                    NavigationDestination.PROFILE -> ProfileContent()
                }
            }
        }
    }
}