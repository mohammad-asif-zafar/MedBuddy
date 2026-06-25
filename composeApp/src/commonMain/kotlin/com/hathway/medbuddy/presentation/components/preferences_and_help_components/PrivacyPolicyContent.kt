package com.hathway.medbuddy.presentation.components.preferences_and_help_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpCenter
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrivacyPolicyContent(
    onOpenPrivacyPolicy: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        InfoBanner(
            title = stringResource(Res.string.preferences_privacy_policy_title),
            subtitle = stringResource(Res.string.privacy_policy_banner_desc),
            icon = Icons.Outlined.Shield,
            iconBackgroundColor = MaterialTheme.colorScheme.primary,
            bannerColor = MaterialTheme.colorScheme.surface,
            showRightArrow = true,
            onClick = onOpenPrivacyPolicy
        )

        PolicySection(
            title = stringResource(Res.string.privacy_collect_title),
            body = listOf(
                stringResource(Res.string.privacy_collect_account),
                stringResource(Res.string.privacy_collect_health),
                stringResource(Res.string.privacy_collect_medical)
            )
        )

        PolicySection(
            title = stringResource(Res.string.privacy_use_title),
            body = listOf(
                stringResource(Res.string.privacy_use_services),
                stringResource(Res.string.privacy_use_trends),
                stringResource(Res.string.privacy_use_notifications),
                stringResource(Res.string.privacy_use_personalize)
            )
        )

        PolicySection(
            title = stringResource(Res.string.privacy_security_title),
            body = listOf(
                stringResource(Res.string.privacy_security_firebase),
                stringResource(Res.string.privacy_security_limits)
            )
        )

        PolicySection(
            title = stringResource(Res.string.privacy_third_party_title),
            body = listOf(
                stringResource(Res.string.privacy_third_party_services),
                stringResource(Res.string.privacy_third_party_collect)
            )
        )

        PolicySection(
            title = stringResource(Res.string.privacy_rights_title),
            body = listOf(
                stringResource(Res.string.privacy_rights_update),
                stringResource(Res.string.privacy_rights_sign_out)
            )
        )

        InfoBanner(
            title = stringResource(Res.string.privacy_contact_title),
            subtitle = stringResource(Res.string.privacy_contact_desc),
            icon = Icons.AutoMirrored.Outlined.HelpCenter,
            iconBackgroundColor = MaterialTheme.colorScheme.primary,
            bannerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        )
    }
}

@Composable
private fun PolicySection(
    title: String,
    body: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            body.forEach { item ->
                Text(
                    text = stringResource(Res.string.privacy_policy_bullet_format, item),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                )
            }
        }
    }
}
