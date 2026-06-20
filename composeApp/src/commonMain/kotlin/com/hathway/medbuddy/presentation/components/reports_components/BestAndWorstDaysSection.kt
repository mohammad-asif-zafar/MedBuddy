package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.best_and_worst_days_title
import medbuddy.composeapp.generated.resources.ic_calendar_check
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
    modifier: Modifier = Modifier
) {
    var showTooltip by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
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
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(start = 2.dp)
                )

                Box {
                    IconButton(onClick = { showTooltip = !showTooltip }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(Res.string.info_button_desc),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    if (showTooltip) {
                        Popup(
                            alignment = Alignment.BottomEnd,
                            offset = IntOffset(x = 0, y = 110),
                            onDismissRequest = { showTooltip = false },
                            properties = PopupProperties(focusable = true)
                        ) {
                            Column(
                                modifier = Modifier.widthIn(max = 280.dp)
                                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(12.dp))
                                    .background(
                                        color = MaterialTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(12.dp)
                                    ).padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = stringResource(Res.string.tooltip_dialog_best_desc),
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = stringResource(Res.string.tooltip_dialog_worst_desc),
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
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
                    icon = Res.drawable.ic_calendar_check,
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    accentColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    subtextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )

                DaySummaryItem(
                    title = stringResource(Res.string.label_worst_day),
                    date = worstDate,
                    value = worstAvg,
                    icon = Res.drawable.ic_calendar_check,
                    modifier = Modifier.weight(1f),
                    containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f),
                    accentColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    subtextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
