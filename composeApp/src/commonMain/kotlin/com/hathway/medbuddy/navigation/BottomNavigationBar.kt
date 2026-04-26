package com.hathway.medbuddy.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun BottomNavigationBar(
    navigationViewModel: NavigationViewModel
) {
    val currentDestination by navigationViewModel.currentDestination.collectAsState()
    
    NavigationBar {
        NavigationDestination.values().forEach { destination ->
            NavigationBarItem(
                icon = { 
                    Text(
                        text = destination.iconSymbol,
                        style = MaterialTheme.typography.titleLarge,
                        color = if (currentDestination == destination) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    )
                },
                label = { Text(destination.title) },
                selected = currentDestination == destination,
                onClick = {
                    navigationViewModel.navigateTo(destination)
                }
            )
        }
    }
}
