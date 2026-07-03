package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.navigation.NavigationDestination
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavigationDrawerContent(
    currentDestination: NavigationDestination,
    onDestinationSelected: (NavigationDestination) -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.width(320.dp),
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
        drawerContainerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Header
            Spacer(modifier = Modifier.height(24.dp))
            DrawerHeader()
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            
            // Scrollable Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            ) {
                // Section: Main Navigation
                DrawerSectionLabel(text = "Dashboard")
                DrawerMenuItem(
                    label = stringResource(Res.string.nav_home),
                    icon = Icons.Outlined.Home,
                    isSelected = currentDestination == NavigationDestination.HOME,
                    onClick = { onDestinationSelected(NavigationDestination.HOME) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.nav_history),
                    icon = Icons.Outlined.History,
                    isSelected = currentDestination == NavigationDestination.HISTORY,
                    onClick = { onDestinationSelected(NavigationDestination.HISTORY) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.nav_reports),
                    icon = Icons.Outlined.BarChart,
                    isSelected = currentDestination == NavigationDestination.REPORTS,
                    onClick = { onDestinationSelected(NavigationDestination.REPORTS) }
                )

                Spacer(modifier = Modifier.height(24.dp))
                
                // Section: Health Tracking
              //  DrawerSectionLabel(text = "Health tracking")
                /*DrawerMenuItem(
                    label = stringResource(Res.string.title_ai_insights),
                    icon = Icons.Outlined.Psychology,
                    isSelected = currentDestination == NavigationDestination.AI_INSIGHTS,
                    onClick = { onDestinationSelected(NavigationDestination.AI_INSIGHTS) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_meal_tracking),
                    icon = Icons.Outlined.Restaurant,
                    isSelected = currentDestination == NavigationDestination.MEAL_TRACKING,
                    onClick = { onDestinationSelected(NavigationDestination.MEAL_TRACKING) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_medication),
                    icon = Icons.Outlined.Medication,
                    isSelected = currentDestination == NavigationDestination.MEDICATION_ADHERENCE,
                    onClick = { onDestinationSelected(NavigationDestination.MEDICATION_ADHERENCE) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_exercise),
                    icon = Icons.AutoMirrored.Outlined.DirectionsRun,
                    isSelected = currentDestination == NavigationDestination.EXERCISE_TRACKING,
                    onClick = { onDestinationSelected(NavigationDestination.EXERCISE_TRACKING) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_weight_bmi),
                    icon = Icons.Outlined.Scale,
                    isSelected = currentDestination == NavigationDestination.WEIGHT_BMI,
                    onClick = { onDestinationSelected(NavigationDestination.WEIGHT_BMI) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_blood_pressure),
                    icon = Icons.Outlined.FavoriteBorder,
                    isSelected = currentDestination == NavigationDestination.BLOOD_PRESSURE,
                    onClick = { onDestinationSelected(NavigationDestination.BLOOD_PRESSURE) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_health_reports),
                    icon = Icons.Outlined.Description,
                    isSelected = currentDestination == NavigationDestination.HEALTH_REPORTS_DETAIL,
                    onClick = { onDestinationSelected(NavigationDestination.HEALTH_REPORTS_DETAIL) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_lab_results),
                    icon = Icons.Outlined.Science,
                    isSelected = currentDestination == NavigationDestination.LAB_RESULTS,
                    onClick = { onDestinationSelected(NavigationDestination.LAB_RESULTS) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_doctor_appointments),
                    icon = Icons.Outlined.CalendarToday,
                    isSelected = currentDestination == NavigationDestination.DOCTOR_APPOINTMENTS_DETAIL,
                    onClick = { onDestinationSelected(NavigationDestination.DOCTOR_APPOINTMENTS_DETAIL) }
                )
*/
               // Spacer(modifier = Modifier.height(24.dp))

                // Section: Account
              //  DrawerSectionLabel(text = "Account")
                DrawerMenuItem(
                    label = stringResource(Res.string.nav_profile),
                    icon = Icons.Outlined.Person,
                    isSelected = currentDestination == NavigationDestination.PROFILE,
                    onClick = { onDestinationSelected(NavigationDestination.PROFILE) }
                )
              /*  DrawerMenuItem(
                    label = stringResource(Res.string.title_family_care),
                    icon = Icons.Outlined.Groups,
                    isSelected = currentDestination == NavigationDestination.FAMILY_CARE,
                    onClick = { onDestinationSelected(NavigationDestination.FAMILY_CARE) }
                )
                DrawerMenuItem(
                    label = stringResource(Res.string.title_emergency_alerts),
                    icon = Icons.Outlined.NotificationsActive,
                    isSelected = currentDestination == NavigationDestination.EMERGENCY_ALERTS,
                    onClick = { onDestinationSelected(NavigationDestination.EMERGENCY_ALERTS) }
                )*/
            }

            // Bottom Section (Logout)
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            Spacer(modifier = Modifier.height(8.dp))
            
            Box(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                DrawerMenuItem(
                    label = stringResource(Res.string.logout),
                    icon = Icons.AutoMirrored.Outlined.Logout,
                    isSelected = false,
                    onClick = onLogout,
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DrawerSectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
        letterSpacing = 1.sp
    )
}

@Composable
fun DrawerHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
    ) {
        Surface(
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "A",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "MedBuddy",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Smart Glucose Care",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DrawerMenuItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    color: Color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = label,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 15.sp
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(22.dp)
            )
        },
        selected = isSelected,
        onClick = onClick,
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            selectedIconColor = MaterialTheme.colorScheme.primary,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            unselectedIconColor = color,
            unselectedTextColor = color
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.height(50.dp)
    )
}

@Preview
@Composable
fun NavigationDrawerContentPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        NavigationDrawerContent(
            currentDestination = NavigationDestination.HOME,
            onDestinationSelected = {},
            onLogout = {}
        )
    }
}


@Preview
@Composable
fun NavigationDrawerContentDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        NavigationDrawerContent(
            currentDestination = NavigationDestination.HOME,
            onDestinationSelected = {},
            onLogout = {}
        )
    }
}
