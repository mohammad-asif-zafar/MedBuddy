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
import com.hathway.medbuddy.presentation.theme.Danger
import com.hathway.medbuddy.presentation.theme.Success
import com.hathway.medbuddy.presentation.theme.Warning

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
                    .background(Color(0xFFF1F1F1))
            )

            // Green range area
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp)
                    .width(12.dp).height(70.dp).clip(RoundedCornerShape(50))
                    .background(Color(0xFFCFECCF))
            )

            // Indicator
            Box(
                modifier = Modifier.offset(
                    y = ((1f - progress) * 95f - 47f).dp
                ).size(18.dp).clip(CircleShape).background(Color(0xFF43A047))
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
                    text = it.toString(), fontSize = 11.sp, color = Color(0xFF6B7280)
                )
            }
        }
    }
}/*@Composable
fun GlucoseMeter(
    value: Int, modifier: Modifier = Modifier
) {

    val minValue = 70f
    val maxValue = 400f
    val meterHeight = 180f

    val indicatorOffset = valueToOffset(
        value = value.toFloat(), minValue = minValue, maxValue = maxValue, meterHeight = meterHeight
    )

    val indicatorColor = when {
        value < 70 -> Danger
        value > 140 -> Warning
        else -> Success
    }

    val labels = listOf(
        370, 310, 250, 190, 130, 70
    )

    Row(
        modifier = modifier.height(meterHeight.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        // Meter Area
        Box(
            modifier = Modifier.width(36.dp).fillMaxHeight(), contentAlignment = Alignment.Center
        ) {

            // Track
            Box(
                modifier = Modifier.width(20.dp).fillMaxHeight().clip(RoundedCornerShape(50))
                    .background(Color(0xFFF1F1F1))
            )

            // Target Range (70 - 100)
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp)
                    .width(14.dp).height(52.dp).clip(RoundedCornerShape(50))
                    .background(Color(0xFFC8E6C9))
            )

            // Indicator Dot
            Box(
                modifier = Modifier.align(Alignment.TopCenter).offset(y = (indicatorOffset - 9).dp)
                    .size(18.dp).clip(CircleShape).background(indicatorColor)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Labels
        Column(
            modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween
        ) {

            labels.forEach { label ->

                Text(
                    text = label.toString(),
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}*/

private fun valueToOffset(
    value: Float, minValue: Float, maxValue: Float, meterHeight: Float
): Float {

    val progress = ((value - minValue) / (maxValue - minValue)).coerceIn(0f, 1f)

    return (1f - progress) * meterHeight
}

