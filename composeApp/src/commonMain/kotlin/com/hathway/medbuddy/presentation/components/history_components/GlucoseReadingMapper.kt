package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.Coffee
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SoupKitchen
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class GlucoseReadingUiModel(
    val icon: ImageVector, val iconBgColor: Color, val iconTint: Color,

    val minTarget: Int, val maxTarget: Int,

    val statusText: String, val statusColor: Color
)

object GlucoseReadingMapper {

    fun map(
        label: String, value: Int
    ): GlucoseReadingUiModel {

        val isAfterMeal =
            label.contains("After", true) || label.contains("(AL)", true) || label.contains(
                "(ABF)", true
            )

        val isBedtime = label.contains("Bed", true) || label.contains("(BT)", true)

        /**
         * Target range rules:
         *
         * Before meals:
         * 70 - 100 mg/dL
         *
         * After meals:
         * 70 - 140 mg/dL
         *
         * Bedtime:
         * 70 - 140 mg/dL
         */


        val (minTarget, maxTarget) = if (isAfterMeal || isBedtime) {
            70 to 140
        } else {
            70 to 100
        }

        /**
         * Determines glucose status.
         *
         * Below target → Low
         * Within target → In Range
         * Above target → High
         */
        /**
         * Maps meal type to:
         * - Icon
         * - Background color
         * - Icon tint
         * - Suggested time
         *
         * Examples:
         *
         * Before Breakfast → Sun icon
         * After Breakfast → Coffee icon
         * Lunch → Sunny icon
         * Dinner → Soup icon
         * Bedtime → Bed icon
         */

        val (statusText, statusColor) = when {
            value < minTarget -> "Low" to Color(0xFFE53935)

            value > maxTarget -> "High" to Color(0xFFFF9500)

            else -> "In Range" to Color(0xFF1B5E20)
        }

        val (icon, bgColor, iconTint) = when {

            label.contains("Breakfast", true) || label.contains(
                "BBF", true
            ) || label.contains("ABF", true) -> {

                if (isAfterMeal) {
                    Triple(
                        Icons.Outlined.Coffee, Color(0xFFE8F5E9), Color(0xFF2E7D32)
                    )
                } else {
                    Triple(
                        Icons.Outlined.LightMode, Color(0xFFFFF3E0), Color(0xFFEF6C00)
                    )
                }
            }

            label.contains("Lunch", true) || label.contains("BL", true) || label.contains(
                "AL", true
            ) -> {

                if (isAfterMeal) {
                    Triple(
                        Icons.Outlined.SoupKitchen, Color(0xFFFFF3E0), Color(0xFFD84315)
                    )
                } else {
                    Triple(
                        Icons.Outlined.WbSunny, Color(0xFFFFF8E1), Color(0xFFFBC02D)
                    )
                }
            }

            label.contains("Dinner", true) || label.contains("BD", true) -> {

                Triple(
                    Icons.Outlined.SoupKitchen, Color(0xFFE8EAF6), Color(0xFF283593)
                )
            }

            else -> {

                Triple(
                    Icons.Outlined.Bedtime, Color(0xFFEDE7F6), Color(0xFF4527A0)
                )
            }
        }

        return GlucoseReadingUiModel(
            icon = icon,
            iconBgColor = bgColor,
            iconTint = iconTint,
            minTarget = minTarget,
            maxTarget = maxTarget,
            statusText = statusText,
            statusColor = statusColor
        )
    }
}