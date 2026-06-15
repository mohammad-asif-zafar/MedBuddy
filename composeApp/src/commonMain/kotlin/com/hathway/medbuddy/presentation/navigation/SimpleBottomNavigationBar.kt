package com.hathway.medbuddy.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun SimpleBottomNavigationBar(
    currentDestination: NavigationDestination,
    onDestinationSelected: (NavigationDestination) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        NavigationDestination.values().forEach { destination ->
            val label = when (destination) {
                NavigationDestination.HOME -> stringResource(Res.string.nav_home)
                NavigationDestination.ADD -> stringResource(Res.string.nav_add)
                NavigationDestination.PROFILE -> stringResource(Res.string.nav_profile)
            }

            if (destination.isFloatingActionButton) {
                FloatingActionButton(
                    onClick = { onDestinationSelected(destination) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = destination.icon ?: "+",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            } else {
                NavigationBarItem(
                    icon = {
                        Text(
                            text = destination.icon ?: "",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    label = { Text(label) },
                    selected = currentDestination == destination,
                    onClick = {
                        onDestinationSelected(destination)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}
