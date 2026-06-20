package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.filter_30_days
import medbuddy.composeapp.generated.resources.filter_7_days
import medbuddy.composeapp.generated.resources.filter_90_days
import medbuddy.composeapp.generated.resources.filter_custom
import medbuddy.composeapp.generated.resources.nav_menu_desc
import medbuddy.composeapp.generated.resources.reports_calendar_desc
import medbuddy.composeapp.generated.resources.reports_screen_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReportsTopBarAndFilter(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onMenuClick: () -> Unit,
    onCalendarClick: () -> Unit,
    modifier: Modifier = Modifier,
    enableMenu: Boolean,
) {
    val filterItems = listOf(
        stringResource(Res.string.filter_7_days),
        stringResource(Res.string.filter_30_days),
        stringResource(Res.string.filter_90_days)
    )

    Column(
        modifier = modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (enableMenu) {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(Res.string.nav_menu_desc),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Text(
                text = stringResource(Res.string.reports_screen_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = onCalendarClick) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = stringResource(Res.string.reports_calendar_desc),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Horizontal Selector Row
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            filterItems.forEach { filter ->
                val isSelected = filter == selectedFilter
                Surface(
                    modifier = Modifier.weight(1f).clickable { onFilterSelected(filter) },
                    shape = RoundedCornerShape(50),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    border = if (!isSelected) BorderStroke(
                        width = 1.dp, color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ) else null
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                        if (filter == stringResource(Res.string.filter_custom)) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                modifier = Modifier.size(13.dp),
                                tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
