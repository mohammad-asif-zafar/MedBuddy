package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.hathway.medbuddy.presentation.theme.Error
import com.hathway.medbuddy.presentation.theme.OnPrimaryContainer
import com.hathway.medbuddy.presentation.theme.Primary
import com.hathway.medbuddy.presentation.theme.PrimaryContainer
import com.hathway.medbuddy.presentation.theme.Surface
import com.hathway.medbuddy.presentation.theme.SurfaceVariant
import io.ktor.serialization.Configuration
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.best_and_worst_days_title
import medbuddy.composeapp.generated.resources.info_button_desc
import medbuddy.composeapp.generated.resources.label_best_day
import medbuddy.composeapp.generated.resources.label_worst_day
import medbuddy.composeapp.generated.resources.tooltip_dialog_best_desc
import medbuddy.composeapp.generated.resources.tooltip_dialog_worst_desc
import org.jetbrains.compose.resources.stringResource

@Composable
fun BestAndWorstDaysSection(
    bestDate: String,
    bestAvg: Int,
    worstDate: String,
    worstAvg: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = SurfaceVariant,
    titleColor: Color = OnPrimaryContainer,
    bestContainerColor: Color = PrimaryContainer,
    bestAccentColor: Color = Primary,
    worstContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
    worstAccentColor: Color = Error
) {
    var showTooltip by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.best_and_worst_days_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = titleColor,
                    modifier = Modifier.padding(start = 2.dp)
                )

                // Anchoring Box frame container holding both toggle reference points
                Box {
                    IconButton(onClick = { showTooltip = !showTooltip }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(Res.string.info_button_desc),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Native popup module anchored dynamically relative to the target item frame
                    if (showTooltip) {
                        Popup(
                            alignment = Alignment.BottomEnd,
                            offset = IntOffset(
                                x = 0, y = 110
                            ), // Shifts bubble directly below button
                            onDismissRequest = { showTooltip = false },
                            properties = PopupProperties(focusable = true) // Closes window when clicking outside
                        ) {
                            Column(
                                modifier = Modifier.widthIn(max = 280.dp)
                                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
                                    .background(
                                        color = MaterialTheme.colorScheme.surface,
                                        shape = RoundedCornerShape(12.dp)
                                    ).padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = stringResource(Res.string.tooltip_dialog_best_desc),
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    color = Primary
                                )
                                Text(
                                    text = stringResource(Res.string.tooltip_dialog_worst_desc),
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    color = Primary
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DaySummaryItem(
                    title = stringResource(Res.string.label_best_day),
                    date = bestDate,
                    value = bestAvg,
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f),
                    containerColor = bestContainerColor,
                    accentColor = bestAccentColor,
                    contentColor = titleColor,
                    subtextColor = Primary
                )

                DaySummaryItem(
                    title = stringResource(Res.string.label_worst_day),
                    date = worstDate,
                    value = worstAvg,
                    icon = Icons.Default.Cancel,
                    modifier = Modifier.weight(1f),
                    containerColor = worstContainerColor,
                    accentColor = worstAccentColor,
                    contentColor = titleColor,
                    subtextColor = Primary
                )
            }
        }
    }
}

@Composable
private fun BestWorstPreviewTheme(
    isDark: Boolean = true, isCream: Boolean = false, content: @Composable () -> Unit
) {
    val colors = when {
        isCream -> lightColorScheme(
            surfaceVariant = Color(0xFFF7F7EE),      // Main light cream layout bg
            surface = Color(0xFFFCFCF9),             // Tooltip window bg
            onSurface = Color(0xFF1C1B12),           // Deep brown text
            onSurfaceVariant = Color(0xFF5A5950),    // Muted dark grey text
            primaryContainer = Color(0xFFE8F5E9),    // Light green soft panel
            primary = Color(0xFF2E7D32),             // Forest green accent
            errorContainer = Color(0xFFFFEBEE),      // Light red soft panel
            error = Color(0xFFC62828)                // Vibrant red error accent
        )

        isDark -> darkColorScheme(
            surfaceVariant = Color(0xFF1E1E1C),      // Dark gray background
            surface = Color(0xFF2B2B28),             // Dark tooltip window bg
            onSurface = Color(0xFFE6E6E1),           // Near white text
            onSurfaceVariant = Color(0xFFB0B0AA),    // Dim gray text
            primaryContainer = Color(0xFF1B3D20),    // Dark forest tint container
            primary = Color(0xFF81C784),             // Light pastel green accent
            errorContainer = Color(0xFF4C1C1C),      // Deep wine container tint
            error = Color(0xFFE57373)                // Light pastel red error accent
        )

        else -> lightColorScheme(
            surfaceVariant = Color(0xFFF5F5F5),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF212121),
            onSurfaceVariant = Color(0xFF757575),
            primaryContainer = Color(0xFFE8F5E9),
            primary = Color(0xFF2E7D32),
            errorContainer = Color(0xFFFFEBEE),
            error = Color(0xFFC62828)
        )
    }
    MaterialTheme(colorScheme = colors, content = content)
}

@Preview(name = "Cream Theme Popup Layout", showBackground = true)
@Composable
fun BestAndWorstDaysCreamPreview() {
    BestWorstPreviewTheme(isCream = true) {
        BestAndWorstDaysSection(
            bestDate = "12 Jun",
            bestAvg = 98,
            worstDate = "15 Jun",
            worstAvg = 185,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// ✅ Clean Multiplatform Dark Preview (No uiMode parameter)
@Preview
@Composable
fun BestAndWorstDaysDarkPreview() {
    BestWorstPreviewTheme(isDark = true) {
        BestAndWorstDaysSection(
            bestDate = "12 Jun",
            bestAvg = 98,
            worstDate = "15 Jun",
            worstAvg = 185,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Standard Light Theme Layout", showBackground = true)
@Composable
fun BestAndWorstDaysLightPreview() {
    BestWorstPreviewTheme(isDark = true) {
        BestAndWorstDaysSection(
            bestDate = "12 Jun",
            bestAvg = 98,
            worstDate = "15 Jun",
            worstAvg = 185,
            modifier = Modifier.padding(16.dp)
        )
    }
}

