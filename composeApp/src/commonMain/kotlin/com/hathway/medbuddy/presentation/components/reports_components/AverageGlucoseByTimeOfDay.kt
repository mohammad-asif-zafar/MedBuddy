package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.SoupKitchen
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.Primary
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.*

@Composable
fun AverageGlucoseByTimeOfDay(
    modifier: Modifier = Modifier, // ✅ Added default modifier value for better reusability
    beforeBreakfast: Int,
    afterBreakfast: Int,
    beforeLunch: Int,
    afterLunch: Int,
    beforeDinner: Int,
    afterDinner: Int,
    bedtime: Int
) {
    // ✅ Dynamic Icon Tones: Adaptive accents that automatically shift properties based on layout themes
    val sunIconColor = if (Primary == Color(0xFF81C784)) Color(0xFFFFB74D) else Color(
        0xFFFFA000
    )
    val lunchIconColor = if (Primary == Color(0xFF81C784)) Color(0xFF64B5F6) else Color(
        0xFF1976D2
    )
    val twilightIconColor = if (Primary == Color(0xFF81C784)) Color(0xFFBA68C8) else Color(
        0xFF7B1FA2
    )
    val nightIconColor = if (Primary == Color(0xFF81C784)) Color(0xFF9FA8DA) else Color(
        0xFF303F9F
    )

    Card(
        modifier = modifier.fillMaxWidth().padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            //  Dynamic Theme Color: Swapped brandCream literal with custom surface variant token
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.avg_glucose_time_of_day_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Row 1: Breakfast System
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimeOfDayItem(
                    Modifier.weight(1f),
                    stringResource(Res.string.tag_before_breakfast),
                    beforeBreakfast,
                    Icons.Default.WbSunny,
                    sunIconColor
                )
                TimeOfDayItem(
                    Modifier.weight(1f),
                    stringResource(Res.string.tag_after_breakfast),
                    afterBreakfast,
                    Icons.Default.WbTwilight,
                    sunIconColor
                )
                TimeOfDayItem(
                    Modifier.weight(1f),
                    stringResource(Res.string.tag_before_lunch),
                    beforeLunch,
                    Icons.Default.LightMode,
                    lunchIconColor
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimeOfDayItem(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.tag_before_dinner),
                    value = beforeDinner,
                    icon = Icons.Outlined.Restaurant,
                    iconColor = twilightIconColor
                )
                TimeOfDayItem(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.tag_after_dinner),
                    value = afterDinner,
                    icon = Icons.Default.WbTwilight,
                    iconColor = twilightIconColor
                )
                TimeOfDayItem(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.tag_bedtime),
                    value = bedtime,
                    icon = Icons.Default.NightsStay,
                    iconColor = nightIconColor
                )
            }
        }
    }
}

// ==================== DOUBLE MULTIPLATFORM PREVIEW ENGINE ====================

@Preview(name = "Light Mode - Custom Warm Cream")
@Composable
fun AverageGlucoseByTimeOfDayCreamPreview() {
    TimeOfDaySectionMockTheme(darkTheme = false) {
        AverageGlucoseByTimeOfDay(
            beforeBreakfast = 115,
            afterBreakfast = 152, // Warning: flips to crisp medical red
            beforeLunch = 120,
            afterLunch = 138,
            beforeDinner = 118,
            afterDinner = 164, // Warning: flips to crisp medical red
            bedtime = 142       // Warning: bedtime > 140 flips to crisp medical red
        )
    }
}

@Preview(name = "Dark Mode - High Contrast Check")
@Composable
fun AverageGlucoseByTimeOfDayDarkPreview() {
    TimeOfDaySectionMockTheme(darkTheme = true) {
        AverageGlucoseByTimeOfDay(
            beforeBreakfast = 115,
            afterBreakfast = 152, // Warning: flips to glowing neon-pastel red
            beforeLunch = 120,
            afterLunch = 138,
            beforeDinner = 118,
            afterDinner = 164, // Warning: flips to glowing neon-pastel red
            bedtime = 142       // Warning: bedtime > 140 flips to glowing neon-pastel red
        )
    }
}

/**
 * Custom isolated design theme provider
 */
@Composable
private fun TimeOfDaySectionMockTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val systemColorScheme = if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            surface = Color(0xFF1E1E1C),
            surfaceVariant = Color(0xFF2B2B28),
            onSurfaceVariant = Color(0xFFE5E5DC),
            primary = Color(0xFF81C784),       // Soft dark-optimized neon green
            error = Color(0xFFFF8A80)          // Soft dark-optimized coral warning red
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            surface = Color(0xFFFEF9F0),
            surfaceVariant = Color(0xFFF7F7EE),  // Matches warm cream card backgrounds
            onSurfaceVariant = Color(0xFF42423E),
            primary = Color(0xFF1B5E20),       // Solid light mode deep green
            error = Color(0xFFD32F2F)          // Solid light mode deep red
        )
    }

    MaterialTheme(colorScheme = systemColorScheme, content = content)
}
