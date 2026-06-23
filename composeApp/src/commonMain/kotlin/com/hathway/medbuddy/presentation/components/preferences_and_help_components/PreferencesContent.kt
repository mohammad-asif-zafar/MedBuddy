package com.hathway.medbuddy.presentation.components.preferences_and_help_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.SettingsRowItem
import com.hathway.medbuddy.icons.KmpComposeIcons

@Composable
fun PreferencesContent() {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        SectionWrapper(title = "ABOUT APP") {
            SettingsRowItem(
                icon = Icons.Outlined.Info,
                title = "About MedBuddy",
                subtitle = "Learn more about the app",
                showArrow = false
            )
            SettingsRowItem(
                icon =  KmpComposeIcons.Box3D,
                title = "Version",
                subtitle = "Current version 1.0.0",
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.Shield,
                title = "Privacy Policy",
                subtitle = "Your privacy is important to us",
                showArrow = false
            )
            SettingsRowItem(
                icon =  KmpComposeIcons.OpenBook,
                title = "Terms & Conditions",
                subtitle = "Read our terms and conditions",
                showArrow = false
            )
        }

        SectionWrapper(title = "SUPPORT") {
            SettingsRowItem(
                icon =  KmpComposeIcons.Headset,
                title = "Help & Support",
                subtitle = "Get help and support",
                showArrow = false
            )
            SettingsRowItem(
                icon =  KmpComposeIcons.ChatBubble,
                title = "Feedback",
                subtitle = "We'd love to hear from you",
                showArrow = false
            )
            SettingsRowItem(
                icon =  KmpComposeIcons.Star, title = "Rate the App", subtitle = "Rate us on Play Store",
                showArrow = false
            )
        }

        SectionWrapper(title = "SPECIAL THANKS") {
            SettingsRowItem(
                icon = KmpComposeIcons.CodeTags,
                title = "Thanks to Developers",
                subtitle = "Special thanks to our developers",
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.Paintbrush,
                title = "Design & UI/UX",
                subtitle = "Thanks to our design team",
                showArrow = false
            )
            SettingsRowItem(
                icon = KmpComposeIcons.Database,
                title = "Data & API Use",
                subtitle = "We use reliable data & APIs",
                showArrow = false
            )
        }

        InfoBanner(
            title = "Your Data is Safe",
            subtitle = "Your data is securely stored on Firebase and protected with industry-standard security.",
            icon = KmpComposeIcons.ShadedPadlock,
            iconBackgroundColor = Color(0xFF00A896),
            bannerColor = MaterialTheme.colorScheme.surface
        )
    }
}