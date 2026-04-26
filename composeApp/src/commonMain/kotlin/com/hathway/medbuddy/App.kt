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
fun App() {
    MedBuddyTheme {
        val navigationViewModel: NavigationViewModel = viewModel()
        
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navigationViewModel = navigationViewModel)
            }
        ) { paddingValues ->
            val currentDestination by navigationViewModel.currentDestination.collectAsState()
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                when (currentDestination) {
                    NavigationDestination.HOME -> HomeScreen()
                    NavigationDestination.SEARCH -> SearchScreen()
                    NavigationDestination.ADD -> AddScreen()
                    NavigationDestination.NOTIFICATIONS -> NotificationsScreen()
                    NavigationDestination.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}