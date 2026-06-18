package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsSection(
    onPreferencesClick: () -> Unit,
    onHelpClick: () -> Unit, // Added missing handler to support layout row
    onLogoutClick: () -> Unit
) {
    val brandCream = Color(0xFFF7F7EE)

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Spacing separating your main cards
    ) {
        // Group 1: Navigation Preferences & Help Container Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = brandCream),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                SettingsActionRow(
                    icon = Icons.Outlined.Settings,
                    label = "Preferences",
                    onClick = onPreferencesClick
                )

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Black.copy(alpha = 0.06f),
                    thickness = 1.dp
                )

                SettingsActionRow(
                    icon = Icons.Outlined.HelpOutline,
                    label = "Help & Support",
                    onClick = onHelpClick
                )
            }
        }

        // Group 2: Isolated Standalone Logout Container Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = brandCream),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            SettingsActionRow(
                icon = Icons.AutoMirrored.Filled.Logout,
                label = "Logout",
                onClick = onLogoutClick,
                isDestructive = true
            )
        }
    }
}