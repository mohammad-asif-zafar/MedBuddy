package com.hathway.medbuddy.presentation.components.app_theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.ThemeOptionData
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme

import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.action_close
import medbuddy.composeapp.generated.resources.theme_dark_desc
import medbuddy.composeapp.generated.resources.theme_dark_title
import medbuddy.composeapp.generated.resources.theme_dialog_subtitle
import medbuddy.composeapp.generated.resources.theme_dialog_title
import medbuddy.composeapp.generated.resources.theme_light_desc
import medbuddy.composeapp.generated.resources.theme_light_title
import medbuddy.composeapp.generated.resources.theme_system_desc
import medbuddy.composeapp.generated.resources.theme_system_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ThemeSelectionDialog(
    currentMode: ThemeMode, onDismiss: () -> Unit, onSelect: (ThemeMode) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(0.95f).padding(16.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.theme_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(Res.string.theme_dialog_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                // OPTIMIZATION: Remember the list to prevent re-allocation on recompositions
                val lightTitle = stringResource(Res.string.theme_light_title)
                val lightDesc = stringResource(Res.string.theme_light_desc)
                val darkTitle = stringResource(Res.string.theme_dark_title)
                val darkDesc = stringResource(Res.string.theme_dark_desc)
                val systemTitle = stringResource(Res.string.theme_system_title)
                val systemDesc = stringResource(Res.string.theme_system_desc)

                val themeOptions =
                    remember(lightTitle, lightDesc, darkTitle, darkDesc, systemTitle, systemDesc) {
                        listOf(
                            ThemeOptionData(
                                mode = ThemeMode.LIGHT,
                                title = lightTitle,
                                description = lightDesc,
                                icon = Icons.Outlined.LightMode
                            ), ThemeOptionData(
                                mode = ThemeMode.DARK,
                                title = darkTitle,
                                description = darkDesc,
                                icon = Icons.Outlined.Bedtime
                            ), ThemeOptionData(
                                mode = ThemeMode.SYSTEM,
                                title = systemTitle,
                                description = systemDesc,
                                icon = Icons.Outlined.Settings
                            )
                        )
                    }

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // OPTIMIZATION: Added a key for better list item recycling
                    items(
                        items = themeOptions, key = { it.mode.name }) { option ->
                        ThemeOptionCard(
                            option = option,
                            isSelected = currentMode == option.mode,
                            onClick = { onSelect(option.mode) })
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.End)
                ) {
                    Text(
                        text = stringResource(Res.string.action_close),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ThemeSelectionDialogLightPreview() {
    ThemeSelectionPreviewContainer(initialMode = ThemeMode.LIGHT)
}

@Preview
@Composable
fun ThemeSelectionDialogDarkPreview() {
    ThemeSelectionPreviewContainer(initialMode = ThemeMode.DARK)
}

@Composable
private fun ThemeSelectionPreviewContainer(initialMode: ThemeMode = ThemeMode.LIGHT) {
    var selectedMode by remember { mutableStateOf(initialMode) }
    var isVisible by remember { mutableStateOf(true) }

    MedBuddyTheme {
        Surface {
            if (isVisible) {
                ThemeSelectionDialog(
                    currentMode = selectedMode,
                    onDismiss = { isVisible = false },
                    onSelect = { selectedMode = it })
            }
        }
    }
}

