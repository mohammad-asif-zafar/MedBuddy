package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.*
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

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
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(Res.string.avg_glucose_time_of_day_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
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
                    SunIconDeep
                )
                TimeOfDayItem(
                    Modifier.weight(1f),
                    stringResource(Res.string.tag_after_breakfast),
                    afterBreakfast,
                    Icons.Default.WbTwilight,
                    SunIconDeep
                )
                TimeOfDayItem(
                    Modifier.weight(1f),
                    stringResource(Res.string.tag_before_lunch),
                    beforeLunch,
                    Icons.Default.LightMode,
                    LunchIconDeep
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
                    iconColor = TwilightIconDeep
                )
                TimeOfDayItem(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.tag_after_dinner),
                    value = afterDinner,
                    icon = Icons.Default.WbTwilight,
                    iconColor = TwilightIconDeep
                )
                TimeOfDayItem(
                    modifier = Modifier.weight(1f),
                    label = stringResource(Res.string.tag_bedtime),
                    value = bedtime,
                    icon = Icons.Default.NightsStay,
                    iconColor = NightIconDeep
                )
            }
        }
    }
}
