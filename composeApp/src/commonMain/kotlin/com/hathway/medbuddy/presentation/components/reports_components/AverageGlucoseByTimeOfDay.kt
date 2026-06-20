package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
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
import com.hathway.medbuddy.presentation.theme.*
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.*

@Composable
fun AverageGlucoseByTimeOfDay(
    modifier: Modifier = Modifier,
    beforeBreakfast: Int,
    afterBreakfast: Int,
    beforeLunch: Int,
    afterLunch: Int,
    beforeDinner: Int,
    afterDinner: Int,
    bedtime: Int
) {
    val sunIconColor = if (Primary == PreviewSurfaceVariantDark) SunIconLight else SunIconDeep
    val lunchIconColor = if (Primary == PreviewSurfaceVariantDark) LunchIconLight else LunchIconDeep
    val twilightIconColor = if (Primary == PreviewSurfaceVariantDark) TwilightIconLight else TwilightIconDeep
    val nightIconColor = if (Primary == PreviewSurfaceVariantDark) NightIconLight else NightIconDeep

    Card(
        modifier = modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MiniCardBackground
        ),
        border = BorderStroke(
            width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
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
            afterBreakfast = 152,
            beforeLunch = 120,
            afterLunch = 138,
            beforeDinner = 118,
            afterDinner = 164,
            bedtime = 142
        )
    }
}

@Preview(name = "Dark Mode - High Contrast Check")
@Composable
fun AverageGlucoseByTimeOfDayDarkPreview() {
    TimeOfDaySectionMockTheme(darkTheme = true) {
        AverageGlucoseByTimeOfDay(
            beforeBreakfast = 115,
            afterBreakfast = 152,
            beforeLunch = 120,
            afterLunch = 138,
            beforeDinner = 118,
            afterDinner = 164,
            bedtime = 142
        )
    }
}

@Composable
private fun TimeOfDaySectionMockTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val systemColorScheme = if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            surface = PreviewSurfaceDark,
            surfaceVariant = PreviewSurfaceVariantDark,
            onSurfaceVariant = PreviewOnSurfaceDark,
            primary = Primary,
            error = Error
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            surface = LightBackground,
            surfaceVariant = PreviewSurfaceVariantCream,
            onSurfaceVariant = PreviewOnSurfaceVariantCream,
            primary = DeepGreen,
            error = Destructive
        )
    }

    MaterialTheme(colorScheme = systemColorScheme, content = content)
}
