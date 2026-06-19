package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.average
import medbuddy.composeapp.generated.resources.glucose_unit_mg_dl
import medbuddy.composeapp.generated.resources.glucose_value_format
import org.jetbrains.compose.resources.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DaySummaryItem(
    title: String,
    date: String,
    value: Int,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    subtextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Surface(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        color = containerColor,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(28.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxHeight()
            ) {
                Column {
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = accentColor
                    )
                    Text(
                        text = date,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = contentColor
                    )
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = stringResource(Res.string.average),
                        fontSize = 10.sp,
                        color = subtextColor
                    )
                    Text(
                        text = stringResource(Res.string.glucose_value_format, value),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = contentColor
                    )
                    Text(
                        text = stringResource(Res.string.glucose_unit_mg_dl),
                        fontSize = 10.sp,
                        color = subtextColor
                    )
                }
            }
        }
    }
}

// Custom dynamic local theme simulator for previews
@Composable
private fun PreviewTheme(
    isDark: Boolean = false, isCream: Boolean = false, content: @Composable () -> Unit
) {
    val colors = when {
        isCream -> lightColorScheme(
            surfaceVariant = Color(0xFFFDFBF7), // Warm cream background
            primary = Color(0xFF8C6239),        // Warm brown accent
            onSurface = Color(0xFF2C2520),      // Dark brown body text
            onSurfaceVariant = Color(0xFF70655B) // Muted brown subtext
        )

        isDark -> darkColorScheme(
            surfaceVariant = Color(0xFF1E1E1E),
            primary = Color(0xFF81C784),
            onSurface = Color(0xFFE3E3E3),
            onSurfaceVariant = Color(0xFF9E9E9E)
        )

        else -> lightColorScheme(
            surfaceVariant = Color(0xFFF5F5F5),
            primary = Color(0xFF2E7D32),
            onSurface = Color(0xFF212121),
            onSurfaceVariant = Color(0xFF616161)
        )
    }
    MaterialTheme(colorScheme = colors, content = content)
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun DaySummaryItemLightPreview() {
    PreviewTheme(isDark = false) {
        DaySummaryItem(
            title = "Blood Glucose",
            date = "Today, 19 Jun",
            value = 142,
            icon = Icons.Outlined.WaterDrop,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun DaySummaryItemDarkPreview() {
    PreviewTheme(isDark = true) {
        DaySummaryItem(
            title = "Blood Glucose",
            date = "Today, 19 Jun",
            value = 142,
            icon = Icons.Outlined.WaterDrop,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Cream Theme", showBackground = true)
@Composable
fun DaySummaryItemCreamPreview() {
    PreviewTheme(isCream = true) {
        DaySummaryItem(
            title = "Blood Glucose",
            date = "Today, 19 Jun",
            value = 142,
            icon = Icons.Outlined.WaterDrop,
            modifier = Modifier.padding(16.dp)
        )
    }
}

