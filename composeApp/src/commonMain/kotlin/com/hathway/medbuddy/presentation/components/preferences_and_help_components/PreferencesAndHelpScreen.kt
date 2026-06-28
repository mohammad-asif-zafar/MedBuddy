package com.hathway.medbuddy.presentation.components.preferences_and_help_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.components.home_components.MedBuddyTopBar
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.medbuddy_privacy_url
import medbuddy.composeapp.generated.resources.medbuddy_website_url
import medbuddy.composeapp.generated.resources.preferences_permissions_tab
import medbuddy.composeapp.generated.resources.preferences_privacy_tab
import medbuddy.composeapp.generated.resources.preferences_support_tab
import medbuddy.composeapp.generated.resources.preferences_support_title
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PreferencesAndHelpScreen(
    onBackClick: () -> Unit
) {
    val uriHandler = LocalUriHandler.current
    var selectedTab by remember { mutableStateOf(0) }
    val websiteUrl = stringResource(Res.string.medbuddy_website_url)
    val privacyUrl = stringResource(Res.string.medbuddy_privacy_url)
    val tabs = listOf(
        stringResource(Res.string.preferences_support_tab),
        stringResource(Res.string.preferences_permissions_tab),
        stringResource(Res.string.preferences_privacy_tab)
    )

    Scaffold(topBar = {
        MedBuddyTopBar(
            leftIcon = Icons.AutoMirrored.Outlined.ArrowBack,
            title = stringResource(Res.string.preferences_support_title),
            onLeftClick = { onBackClick() }
        )
    }, containerColor = MaterialTheme.colorScheme.background) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(text = title, fontWeight = FontWeight.Medium) })
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when (selectedTab) {
                    0 -> PreferencesContent(
                        onOpenWebsite = { uriHandler.openUri(websiteUrl) },
                        onOpenPrivacyPolicy = { uriHandler.openUri(privacyUrl) }
                    )
                    1 -> PermissionsContent()
                    else -> PrivacyPolicyContent(
                        onOpenPrivacyPolicy = { uriHandler.openUri(privacyUrl) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreferencesAndHelpScreenPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        PreferencesAndHelpScreen(onBackClick = {})
    }
}
