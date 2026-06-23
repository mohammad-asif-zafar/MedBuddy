package com.hathway.medbuddy.presentation.components.preferences_and_help_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.icons.KmpComposeIcons
import com.hathway.medbuddy.presentation.theme.Primary
import com.hathway.medbuddy.presentation.theme.Warning

@Composable
fun PermissionsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "PERMISSIONS",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
            Text(
                text = "MedBuddy may request the following permissions to provide you better service.",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        ) {
            Column {
                PermissionRowItem(Icons.Filled.Notifications, "Notifications", "To send medicine reminders and important alerts", "Allowed", true)
                HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            }
        }

        InfoBanner(
            title = "Your Privacy Matters",
            subtitle = "We only use the permissions to provide core features. You can change permissions anytime in your device settings.",
            icon = KmpComposeIcons.ShadedShield,
            iconBackgroundColor = Primary,
            bannerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        )

        InfoBanner(
            title = "Your Data is Stored on Firebase",
            subtitle = "All your data is securely stored on Firebase cloud infrastructure with advanced security and encryption.",
            icon = KmpComposeIcons.Firebase,
            iconBackgroundColor = Warning,
            bannerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
            showRightArrow = true
        )
    }
}
