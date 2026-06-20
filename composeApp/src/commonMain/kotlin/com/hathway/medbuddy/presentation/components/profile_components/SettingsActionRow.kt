package com.hathway.medbuddy.presentation.components.profile_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


import com.hathway.medbuddy.presentation.theme.BrandGreen
import com.hathway.medbuddy.presentation.theme.Destructive
import com.hathway.medbuddy.presentation.theme.TextPrimary

@Composable
fun SettingsActionRow(
    icon: ImageVector, label: String, onClick: () -> Unit, isDestructive: Boolean = false
) {
    // Exact colors referenced from your user design guidelines
    val iconTint = if (isDestructive) Destructive else BrandGreen
    val textStyleColor = if (isDestructive) Destructive else TextPrimary

    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }
        .padding(horizontal = 20.dp, vertical = 20.dp), // Balanced cell click surface areas
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = iconTint
            )
            Text(
                text = label,
                color = textStyleColor,
                fontSize = 16.sp,
                fontWeight = if (isDestructive) FontWeight.Bold else FontWeight.Medium
            )
        }

        // Render right-chevron indicator strictly on non-destructive navigation links
        if (!isDestructive) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Gray.copy(alpha = 0.7f)
            )
        }
    }
}

