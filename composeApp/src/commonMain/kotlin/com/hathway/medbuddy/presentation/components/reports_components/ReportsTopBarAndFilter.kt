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
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ReportsTopBarAndFilter(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onMenuClick: () -> Unit,
    onCalendarClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    brandAccentColor: Color = MaterialTheme.colorScheme.primary,
    unselectedPillColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    unselectedPillTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    // Storing standard tracking labels
    val filterItems = listOf(
        stringResource(Res.string.filter_7_days),
        stringResource(Res.string.filter_30_days),
        stringResource(Res.string.filter_90_days)
    )

    Column(
        modifier = modifier.fillMaxWidth().background(backgroundColor).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
           /* IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(Res.string.nav_menu_desc),
                    tint = contentColor
                )
            }*/
            Text(
                text = stringResource(Res.string.reports_screen_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            IconButton(onClick = onCalendarClick) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = stringResource(Res.string.reports_calendar_desc),
                    tint = brandAccentColor
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
                    color = if (isSelected) brandAccentColor else unselectedPillColor,
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
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else unselectedPillTextColor,
                            fontSize = 12.sp, // Downscaled to prevent layout wrapping bugs on 4 items
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                        if (filter == stringResource(Res.string.filter_custom)) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                modifier = Modifier.size(13.dp),
                                tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else unselectedPillTextColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBarPreviewTheme(
    isDark: Boolean = false, isCream: Boolean = false, content: @Composable () -> Unit
) {
    val colors = when {
        isCream -> lightColorScheme(
            surface = Color(0xFFFCFCF9),             // Main top bar base surface
            onSurface = Color(0xFF1C1B12),           // Dark typography
            surfaceVariant = Color(0xFFF7F7EE),      // Unselected pill color background
            onSurfaceVariant = Color(0xFF5A5950),    // Unselected text color
            primary = Color(0xFF1B5E20),             // Active brand green
            onPrimary = Color(0xFFFFFFFF), outline = Color(0xFFD4D4CA)
        )

        isDark -> darkColorScheme(
            surface = Color(0xFF121212),
            onSurface = Color(0xFFE6E6E1),
            surfaceVariant = Color(0xFF1E1E1C),
            onSurfaceVariant = Color(0xFFB0B0AA),
            primary = Color(0xFF81C784),
            onPrimary = Color(0xFF1B5E20),
            outline = Color(0xFF3C3C3E)
        )

        else -> lightColorScheme(
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF212121),
            surfaceVariant = Color(0xFFF5F5F5),
            onSurfaceVariant = Color(0xFF666666),
            primary = Color(0xFF1B5E20),
            onPrimary = Color(0xFFFFFFFF),
            outline = Color(0xFFE0E0E0)
        )
    }
    MaterialTheme(colorScheme = colors, content = content)
}

@Preview
@Composable
fun ReportsTopBarAndFilterCreamPreview() {
    TopBarPreviewTheme(isCream = true) {
        ReportsTopBarAndFilter(
            selectedFilter = "7 Days",
            onFilterSelected = {},
            onMenuClick = {},
            onCalendarClick = {})
    }
}

@Preview
@Composable
fun ReportsTopBarAndFilterDarkPreview() {
    TopBarPreviewTheme(isDark = true) {
        ReportsTopBarAndFilter(
            selectedFilter = "30 Days",
            onFilterSelected = {},
            onMenuClick = {},
            onCalendarClick = {})
    }
}
