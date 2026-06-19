package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SummaryMiniCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    subValue: String,
    valueColor: Color,
    icon: ImageVector
) {
    Surface(
        modifier = modifier.height(110.dp), shape = RoundedCornerShape(16.dp),
        // ✅ Design System Fix: Uses dynamic surface color token instead of hardcoded white
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = valueColor.copy(alpha = 0.7f),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                value, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = valueColor
            )
            if (subValue.isNotEmpty()) {
                Text(
                    subValue, fontSize = 11.sp,
                    // ✅ Design System Fix: Uses semantic theme color for accessibility
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                title,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

// ==================== DUAL PREVIEW ENGINE ====================


@Composable
private fun PreviewRowLayout() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryMiniCard(
            modifier = Modifier.weight(1f),
            title = "Avg Glucose",
            value = "124",
            subValue = "mg/dL",
            valueColor = Color(0xFF4CAF50),
            icon = Icons.Outlined.Bloodtype
        )

        SummaryMiniCard(
            modifier = Modifier.weight(1f),
            title = "Heart Rate",
            value = "72",
            subValue = "bpm",
            valueColor = Color(0xFFE91E63),
            icon = Icons.Filled.Favorite
        )
    }
}

/**
 * Mock theme shell wrapper to provide preview context.
 * Replace this wrapper with your project's actual theme configuration if different.
 */
@Composable
fun MedBuddyTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            surface = Color(0xFF1E1E1E), onSurfaceVariant = Color(0xB3FFFFFF)
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            surface = Color.White, onSurfaceVariant = Color.Gray
        )
    }
    MaterialTheme(colorScheme = colors, content = content)
}


// 2. USE CLEAN TARGET SEPARATION FOR YOUR PREVIEWS:

@Preview(name = "Light Mode - Warm Cream Canvas")
@Composable
fun SummaryMiniCardLightPreview() {
    MedBuddyTheme(darkTheme = false) {
        PreviewRowLayout()
    }
}

@Preview(name = "Dark Mode - Contrast Check") // Removed the uiMode parameter
@Composable
fun SummaryMiniCardDarkPreview() {
    MedBuddyTheme(darkTheme = true) {
        PreviewRowLayout()
    }
}
