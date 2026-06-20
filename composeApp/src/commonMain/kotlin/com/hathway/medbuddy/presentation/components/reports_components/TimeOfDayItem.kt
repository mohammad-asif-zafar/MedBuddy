package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.Error
import com.hathway.medbuddy.presentation.theme.MiniCardBackground
import com.hathway.medbuddy.presentation.theme.OnSurface
import com.hathway.medbuddy.presentation.theme.Primary
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.before_breakfast
import medbuddy.composeapp.generated.resources.glucose_unit_mg_dl
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimeOfDayItem(
    modifier: Modifier = Modifier, label: String, value: Int, icon: ImageVector, iconColor: Color
) {
    val healthyColor = Primary
    val warningColor = Error

    val isHigh = value > 140
    val valueDisplayColor = if (isHigh) warningColor else healthyColor

    Surface(
        modifier = modifier.height(115.dp).fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, top = 6.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        color = MiniCardBackground
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = label,
                fontSize = 10.sp,
                color = OnSurface,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                textAlign = TextAlign.Center
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$value",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = valueDisplayColor
                )
                Text(
                    text = stringResource(Res.string.glucose_unit_mg_dl),
                    fontSize = 9.sp,
                    color = OnSurface
                )
            }
        }
    }
}


// ==================== DOUBLE MULTIPLATFORM PREVIEW ENGINE ====================

@Preview(name = "Light Cream - Threshold Color Move")
@Composable
fun TimeOfDayItemCreamPreview() {
    TimeOfDayMockTheme(darkTheme = false) {
        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TimeOfDayItem(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.before_breakfast),
                value = 112, // Healthy value -> Will map to crisp deep green
                icon = Icons.Outlined.LightMode,
                iconColor = Color(0xFFFFA000)
            )
            TimeOfDayItem(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.before_breakfast),
                value = 158, // Warning value -> Will map to clear medical red
                icon = Icons.Outlined.LightMode,
                iconColor = Color(0xFFFFA000)
            )
        }
    }
}

@Preview(name = "Dark Theme - Threshold Color Move")
@Composable
fun TimeOfDayItemDarkPreview() {
    TimeOfDayMockTheme(darkTheme = true) {
        Row(
            modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            TimeOfDayItem(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.before_breakfast),
                value = 112, // Healthy value -> Will shift to neon-pastel green
                icon = Icons.Outlined.LightMode,
                iconColor = Color(0xFFFFB74D)
            )
            TimeOfDayItem(
                modifier = Modifier.weight(1f),
                label = stringResource(Res.string.before_breakfast),
                value = 158, // Warning value -> Will shift to glowing coral red
                icon = Icons.Outlined.LightMode,
                iconColor = Color(0xFFFFB74D)
            )
        }
    }
}

/**
 * Custom isolated workspace design theme provider
 */
@Composable
private fun TimeOfDayMockTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val systemColorScheme = if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            surface = Color(0xFF1E1E1C),
            surfaceVariant = Color(0xFF2B2B28), // Matches dark cream item backgrounds
            onSurfaceVariant = Color(0xFFE5E5DC),
            primary = Color(0xFF81C784),       // Soft dark-optimized neon green
            error = Color(0xFFFF8A80)          // Soft dark-optimized coral warning red
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            surface = Color(0xFFFEF9F0),         // Base warm cream background canvas
            surfaceVariant = Color(0xFFF7F7EE),  // Item warm cream card surface
            onSurfaceVariant = Color(0xFF5C5C56),
            primary = Color(0xFF1B5E20),       // Solid light mode green
            error = Color(0xFFD32F2F)          // Solid light mode red
        )
    }

    MaterialTheme(colorScheme = systemColorScheme, content = content)
}

