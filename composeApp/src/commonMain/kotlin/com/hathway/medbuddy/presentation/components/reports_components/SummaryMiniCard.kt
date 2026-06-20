package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Bloodtype
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.Success
import com.hathway.medbuddy.presentation.theme.Danger

@Composable
fun SummaryMiniCard(
    title: String,
    value: String,
    subValue: String,
    valueColor: Color,
    icon: Any, // Accepts either ImageVector or Painter natively across targets
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(108.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 10.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon or ImageVector
            when (icon) {
                is ImageVector -> {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = valueColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                is Painter -> {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = valueColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Text(
                    text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = valueColor
                )
                Text(
                    text = subValue,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    maxLines = 1
                )
            }

            Text(
                text = title,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
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
            valueColor = Success,
            icon = Icons.Outlined.Bloodtype
        )

        SummaryMiniCard(
            modifier = Modifier.weight(1f),
            title = "Heart Rate",
            value = "72",
            subValue = "bpm",
            valueColor = Danger,
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
