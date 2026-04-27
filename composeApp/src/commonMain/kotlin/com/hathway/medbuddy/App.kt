package com.hathway.medbuddy

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hathway.medbuddy.navigation.*
import com.hathway.medbuddy.screens.*
import com.hathway.medbuddy.ui.MedBuddyTheme

@Composable
@Preview
fun App(
    repository: Any? = null
) {
    val navigationViewModel = viewModel<NavigationViewModel>()
    val currentDestination by navigationViewModel.currentDestination.collectAsState()

    MedBuddyTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    navigationViewModel = navigationViewModel
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (currentDestination) {
                    NavigationDestination.HOME -> HomeScreen()
                    NavigationDestination.SEARCH -> SearchScreen()
                    NavigationDestination.ADD -> AddScreen(repository = repository)
                    NavigationDestination.NOTIFICATIONS -> NotificationsScreen()
                    NavigationDestination.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}