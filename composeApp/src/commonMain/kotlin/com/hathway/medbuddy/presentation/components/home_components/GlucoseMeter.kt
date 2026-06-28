package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.StatusLow
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.StatusHigh

@Composable
fun GlucoseMeter(
    value: Int, modifier: Modifier = Modifier
) {
    val minValue = 40f
    val maxValue = 200f
    val progress = ((value - minValue) / (maxValue - minValue)).coerceIn(0f, 1f)

    Row(
        modifier = modifier.height(140.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.width(32.dp).fillMaxHeight(), contentAlignment = Alignment.Center
        ) {

            // Background track
            Box(
                modifier = Modifier.width(18.dp).fillMaxHeight().clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            // Target range area (Green)
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp)
                    .width(12.dp).height(70.dp).clip(RoundedCornerShape(50))
                    .background(StatusInRange.copy(alpha = 0.2f))
            )

            // Indicator
            val indicatorColor = when {
                value < 70 -> StatusLow
                value > 140 -> StatusHigh
                else -> StatusInRange
            }

            Box(
                modifier = Modifier.offset(
                    y = ((1f - progress) * 95f - 47f).dp
                ).size(18.dp).clip(CircleShape).background(indicatorColor)
            )
        }

        Spacer(Modifier.width(8.dp))

        Column(
            modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(
                370, 310, 250, 190, 130, 70
            ).forEach {
                Text(
                    text = it.toString(), 
                    fontSize = 11.sp, 
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun GlucoseMeterPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        GlucoseMeter(value = 120)
    }
}
