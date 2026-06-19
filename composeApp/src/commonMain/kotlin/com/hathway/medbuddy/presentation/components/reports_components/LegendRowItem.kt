package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.*

@Composable
fun LegendRowItem(
    modifier: Modifier = Modifier, // ✅ Added default modifier value for high layout flexibility
    color: Color, label: String, percentage: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.width(110.dp)
    ) {
        // Circle color accent status indicators
        Box(
            modifier = Modifier.size(10.dp)
                // ✅ Using clean semantic CircleShape token
                .background(color = color, shape = CircleShape)
        )

        Text(
            text = label, fontSize = 13.sp,
            // ✅ Dynamic Theme Color: Swapped out hardcoded DarkGray for adaptive onSurface variant
            color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f)
        )

        Text(
            text = percentage, fontSize = 13.sp, fontWeight = FontWeight.Bold,
            // ✅ Dynamic Theme Color: Swapped out hardcoded Black for adaptive text color token
            color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.End
        )
    }
}

// ==================== DOUBLE MULTIPLATFORM PREVIEW ENGINE ====================

@Preview(name = "Light Mode - Custom Warm Cream")
@Composable
fun LegendRowItemCreamPreview() {
    LegendMockTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LegendRowItem(
                color = Color(0xFFE53935), // Red
                label = stringResource(Res.string.legend_high), percentage = "15%"
            )
            LegendRowItem(
                color = Color(0xFF4CAF50), // Green
                label = stringResource(Res.string.legend_normal), percentage = "75%"
            )
            LegendRowItem(
                color = Color(0xFF1E88E5), // Blue
                label = stringResource(Res.string.legend_low), percentage = "10%"
            )
        }
    }
}

@Preview(name = "Dark Mode - High Contrast Check")
@Composable
fun LegendRowItemDarkPreview() {
    LegendMockTheme(darkTheme = false) {
        Column(
            modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LegendRowItem(
                color = Color(0xFFE53935),
                label = stringResource(Res.string.legend_high),
                percentage = "15%"
            )
            LegendRowItem(
                color = Color(0xFF4CAF50),
                label = stringResource(Res.string.legend_normal),
                percentage = "75%"
            )
            LegendRowItem(
                color = Color(0xFF1E88E5),
                label = stringResource(Res.string.legend_low),
                percentage = "10%"
            )
        }
    }
}

/**
 * Isolated visual theme container providing your exact cream and dark specifications.
 */
@Composable
private fun LegendMockTheme(darkTheme: Boolean, content: @Composable () -> Unit) {
    val systemColorScheme = if (darkTheme) {
        androidx.compose.material3.darkColorScheme(
            surface = Color(0xFF1E1E1C),
            onSurface = Color(0xFFF7F7EE),      // Custom clear warm off-white text
            onSurfaceVariant = Color(0xFFE5E5DC) // Secondary desaturated labels
        )
    } else {
        androidx.compose.material3.lightColorScheme(
            surface = Color(0xFFFEF9F0),         // Your custom warm cream canvas background
            onSurface = Color(0xFF1A1A17),       // Crisp dark value text
            onSurfaceVariant = Color(0xFF5C5C56) // Muted gray-cream label text
        )
    }

    MaterialTheme(colorScheme = systemColorScheme, content = content)
}
