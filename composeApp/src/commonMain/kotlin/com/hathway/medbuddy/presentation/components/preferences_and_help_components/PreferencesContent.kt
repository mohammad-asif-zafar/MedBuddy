package com.hathway.medbuddy.presentation.components.preferences_and_help_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.icons.KmpComposeIcons
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.preferences_about_medbuddy_section
import medbuddy.composeapp.generated.resources.preferences_analytics_desc
import medbuddy.composeapp.generated.resources.preferences_analytics_title
import medbuddy.composeapp.generated.resources.preferences_app_info_section
import medbuddy.composeapp.generated.resources.preferences_banner_desc
import medbuddy.composeapp.generated.resources.preferences_banner_title
import medbuddy.composeapp.generated.resources.preferences_cloud_sync_desc
import medbuddy.composeapp.generated.resources.preferences_cloud_sync_title
import medbuddy.composeapp.generated.resources.preferences_contact_support_desc
import medbuddy.composeapp.generated.resources.preferences_contact_support_title
import medbuddy.composeapp.generated.resources.preferences_dashboard_desc
import medbuddy.composeapp.generated.resources.preferences_dashboard_title
import medbuddy.composeapp.generated.resources.preferences_data_api_desc
import medbuddy.composeapp.generated.resources.preferences_data_api_title
import medbuddy.composeapp.generated.resources.preferences_health_insights_desc
import medbuddy.composeapp.generated.resources.preferences_health_insights_title
import medbuddy.composeapp.generated.resources.preferences_help_support_section
import medbuddy.composeapp.generated.resources.preferences_kmp_desc
import medbuddy.composeapp.generated.resources.preferences_kmp_title
import medbuddy.composeapp.generated.resources.preferences_meal_segments_desc
import medbuddy.composeapp.generated.resources.preferences_meal_segments_title
import medbuddy.composeapp.generated.resources.preferences_open_website_desc
import medbuddy.composeapp.generated.resources.preferences_open_website_title
import medbuddy.composeapp.generated.resources.preferences_privacy_policy_desc
import medbuddy.composeapp.generated.resources.preferences_privacy_policy_title
import medbuddy.composeapp.generated.resources.preferences_smart_glucose_care_desc
import medbuddy.composeapp.generated.resources.preferences_smart_glucose_care_title
import medbuddy.composeapp.generated.resources.preferences_version_desc
import medbuddy.composeapp.generated.resources.preferences_version_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun PreferencesContent(
    onOpenWebsite: () -> Unit,
    onOpenPrivacyPolicy: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SectionWrapper(title = stringResource(Res.string.preferences_about_medbuddy_section)) {
            SettingsRowItem(
                icon = KmpComposeIcons.Box3D,
                title = stringResource(Res.string.preferences_smart_glucose_care_title),
                subtitle = stringResource(Res.string.preferences_smart_glucose_care_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.Spreadsheet,
                title = stringResource(Res.string.preferences_dashboard_title),
                subtitle = stringResource(Res.string.preferences_dashboard_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.Calendar,
                title = stringResource(Res.string.preferences_analytics_title),
                subtitle = stringResource(Res.string.preferences_analytics_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.ShadedShield,
                title = stringResource(Res.string.preferences_cloud_sync_title),
                subtitle = stringResource(Res.string.preferences_cloud_sync_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.OpenBook,
                title = stringResource(Res.string.preferences_open_website_title),
                subtitle = stringResource(Res.string.preferences_open_website_desc),
                showArrow = true,
                onClick = onOpenWebsite
            )
        }

        SectionWrapper(title = stringResource(Res.string.preferences_help_support_section)) {
            SettingsRowItem(
                icon =  KmpComposeIcons.Headset,
                title = stringResource(Res.string.preferences_contact_support_title),
                subtitle = stringResource(Res.string.preferences_contact_support_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.ChatBubble,
                title = stringResource(Res.string.preferences_health_insights_title),
                subtitle = stringResource(Res.string.preferences_health_insights_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.OpenBook,
                title = stringResource(Res.string.preferences_meal_segments_title),
                subtitle = stringResource(Res.string.preferences_meal_segments_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.Shield,
                title = stringResource(Res.string.preferences_privacy_policy_title),
                subtitle = stringResource(Res.string.preferences_privacy_policy_desc),
                showArrow = true,
                onClick = onOpenPrivacyPolicy
            )
        }

        SectionWrapper(title = stringResource(Res.string.preferences_app_info_section)) {
            SettingsRowItem(
                icon = KmpComposeIcons.CodeTags,
                title = stringResource(Res.string.preferences_kmp_title),
                subtitle = stringResource(Res.string.preferences_kmp_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.Star,
                title = stringResource(Res.string.preferences_version_title),
                subtitle = stringResource(Res.string.preferences_version_desc),
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.Database,
                title = stringResource(Res.string.preferences_data_api_title),
                subtitle = stringResource(Res.string.preferences_data_api_desc),
                showArrow = false
            )
        }

        InfoBanner(
            title = stringResource(Res.string.preferences_banner_title),
            subtitle = stringResource(Res.string.preferences_banner_desc),
            icon = KmpComposeIcons.ShadedPadlock,
            iconBackgroundColor = Color(0xFF00A896),
            bannerColor = MaterialTheme.colorScheme.surface
        )
    }
}