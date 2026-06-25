package com.hathway.medbuddy.presentation.components.preferences_and_help_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

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
                text = stringResource(Res.string.permissions_title),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )
            Text(
                text = stringResource(Res.string.permissions_intro),
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
                PermissionRowItem(
                    Icons.Outlined.Notifications,
                    stringResource(Res.string.permissions_notifications_title),
                    stringResource(Res.string.permissions_notifications_desc),
                    stringResource(Res.string.permissions_status_device_setting),
                    true
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                PermissionRowItem(
                    Icons.Outlined.CloudSync,
                    stringResource(Res.string.permissions_internet_title),
                    stringResource(Res.string.permissions_internet_desc),
                    stringResource(Res.string.permissions_status_required),
                    true
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                PermissionRowItem(
                    Icons.Outlined.AccountCircle,
                    stringResource(Res.string.permissions_google_account_title),
                    stringResource(Res.string.permissions_google_account_desc),
                    stringResource(Res.string.permissions_status_on_sign_in),
                    true
                )
            }
        }

        InfoBanner(
            title = stringResource(Res.string.permissions_privacy_matters_title),
            subtitle = stringResource(Res.string.permissions_privacy_matters_desc),
            icon = Icons.Outlined.Security,
            iconBackgroundColor = MaterialTheme.colorScheme.primary,
            bannerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        )

        InfoBanner(
            title = stringResource(Res.string.permissions_firebase_title),
            subtitle = stringResource(Res.string.permissions_firebase_desc),
            icon = Icons.Outlined.Storage,
            iconBackgroundColor = MaterialTheme.colorScheme.secondary,
            bannerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        )
    }
}
