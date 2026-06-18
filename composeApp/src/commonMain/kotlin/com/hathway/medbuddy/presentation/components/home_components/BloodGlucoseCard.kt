package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.Danger
import com.hathway.medbuddy.presentation.theme.Secondary
import com.hathway.medbuddy.presentation.theme.Success
import com.hathway.medbuddy.presentation.theme.Warning
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.current_glucose
import medbuddy.composeapp.generated.resources.glucose_unit
import medbuddy.composeapp.generated.resources.last_reading
import medbuddy.composeapp.generated.resources.reading_details
import medbuddy.composeapp.generated.resources.reading_value
import medbuddy.composeapp.generated.resources.status_high
import medbuddy.composeapp.generated.resources.status_in_range
import medbuddy.composeapp.generated.resources.status_low
import medbuddy.composeapp.generated.resources.trend_up
import org.jetbrains.compose.resources.stringResource
import kotlin.math.abs

@Composable
fun BloodGlucoseCard(
    glucoseValue: Int, mealType: String, status: String, targetRange: String
) {
    println(status)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            1.dp, Color(0xFFE9E1D3)
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Today's Glucose",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = mealType,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = glucoseValue.toString(),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = "mg/dL",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(12.dp))

                StatusChip(status)

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Target: $targetRange",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            GlucoseMeter(
                value = glucoseValue, modifier = Modifier.padding(start = 16.dp)
            )
        }


    }


}


@Preview(showBackground = true)
@Composable
fun BloodGlucoseCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            /* BloodGlucoseCard(
                 average = 7.4,
                 trend = 0.3,
                 status = "In Range",
                 lastReading = 8.2,
                 lastReadingTime = "4:59 PM",
                 lastMealType = "BFF"
             )*/
        }
    }
}