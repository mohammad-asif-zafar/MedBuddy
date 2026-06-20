package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SoupKitchen
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.hathway.medbuddy.presentation.theme.StatusHigh
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.StatusLow

data class GlucoseReadingUiModel(
    val icon: ImageVector, val iconBgColor: Color, val iconTint: Color,

    val minTarget: Int, val maxTarget: Int,

    val statusText: String, val statusColor: Color
)

object GlucoseReadingMapper {

    @Composable
    fun map(
        label: String, value: Int
    ): GlucoseReadingUiModel {

        val isAfterMeal =
            label.contains("After", true) || label.contains("(AL)", true) || label.contains(
                "(ABF)", true
            )

        val isBedtime = label.contains("Bed", true) || label.contains("(BT)", true)

        val (minTarget, maxTarget) = if (isAfterMeal || isBedtime) {
            70 to 140
        } else {
            70 to 100
        }

        val (statusText, statusColor) = when {
            value < minTarget -> "Low" to StatusLow
            value > maxTarget -> "High" to StatusHigh
            else -> "In Range" to StatusInRange
        }

        val (icon, bgColor, iconTint) = when {
            label.contains("Breakfast", true) || label.contains("BBF", true) || label.contains("ABF", true) -> {
                if (isAfterMeal) {
                    Triple(
                        Icons.Outlined.Coffee, 
                        MaterialTheme.colorScheme.primaryContainer, 
                        MaterialTheme.colorScheme.primary
                    )
                } else {
                    Triple(
                        Icons.Outlined.LightMode, 
                        MaterialTheme.colorScheme.secondaryContainer, 
                        MaterialTheme.colorScheme.secondary
                    )
                }
            }

            label.contains("Lunch", true) || label.contains("BL", true) || label.contains("AL", true) -> {
                if (isAfterMeal) {
                    Triple(
                        Icons.Outlined.SoupKitchen, 
                        MaterialTheme.colorScheme.primaryContainer, 
                        MaterialTheme.colorScheme.primary
                    )
                } else {
                    Triple(
                        Icons.Outlined.WbSunny, 
                        MaterialTheme.colorScheme.secondaryContainer, 
                        MaterialTheme.colorScheme.secondary
                    )
                }
            }

            label.contains("Dinner", true) || label.contains("BD", true) || label.contains("AD", true) -> {
                Triple(
                    Icons.Outlined.SoupKitchen, 
                    MaterialTheme.colorScheme.tertiaryContainer, 
                    MaterialTheme.colorScheme.tertiary
                )
            }

            else -> {
                Triple(
                    Icons.Outlined.Bedtime, 
                    MaterialTheme.colorScheme.secondaryContainer, 
                    MaterialTheme.colorScheme.secondary
                )
            }
        }

        return GlucoseReadingUiModel(
            icon = icon,
            iconBgColor = bgColor.copy(alpha = 0.4f),
            iconTint = iconTint,
            minTarget = minTarget,
            maxTarget = maxTarget,
            statusText = statusText,
            statusColor = statusColor
        )
    }
}
