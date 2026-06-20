package com.hathway.medbuddy.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
    // ✅ Optimizes performance by pre-filtering non-bottom bar screens like NOTIFICATIONS
    val visibleDestinations = remember {
        NavigationDestination.entries.filter { it.isVisibleInBottomBar }
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        visibleDestinations.forEach { destination ->
            val label = when (destination) {
                NavigationDestination.HOME -> stringResource(Res.string.nav_home)
                NavigationDestination.HISTORY -> stringResource(Res.string.nav_history)
                NavigationDestination.ADD -> stringResource(Res.string.nav_add)
                NavigationDestination.REPORTS -> stringResource(Res.string.nav_reports)
                NavigationDestination.PROFILE -> stringResource(Res.string.nav_profile)
                // ✅ Keeps the compiler happy, though filtering prevents this block from executing
                NavigationDestination.NOTIFICATIONS -> ""
            }

            // Safe unpacking helper fallback for the nullable icon property hook
            val targetIcon = destination.icon ?: return@forEach

            if (destination.isFloatingActionButton) {
                FloatingActionButton(
                    contentColor = Color.White,
                    onClick = { onDestinationSelected(destination) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Icon(
                        imageVector = targetIcon,
                        contentDescription = label,
                        tint = Color.White
                    )
                }
            } else {
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = targetIcon,
                            contentDescription = label
                        )
                    },
                    label = { Text(label) },
                    selected = currentDestination == destination,
                    onClick = { onDestinationSelected(destination) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
    }
}
