package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme


@Composable
fun MetricColumn(value: String, label: String, valueColor: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = valueColor)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 12.sp, color = Color.Gray, textAlign = TextAlign.Center)
    }
}

@Composable
fun BpTrendCard() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "BP Trend", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Your BP is stable. Keep\nmaintaining your healthy habits.",
                fontSize = 14.sp,
                color = Color.Gray,
                lineHeight = 20.sp,
                modifier = Modifier.weight(1f)
            )
            // Upward trend arrow graphic placeholder
            Icon(
                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = null,
                tint = Color(0xFF00B0FF),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun RecommendationsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Recommendations",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))

            RecommendationItem(
                imageVector = Icons.AutoMirrored.Filled.DirectionsRun,
                iconColor = Color(0xFF2E7D32),
                iconBg = Color(0xFFE8F5E9),
                title = "Stay active",
                description = "Regular activity helps maintain healthy BP."
            )
            Spacer(modifier = Modifier.height(16.dp))
            RecommendationItem(
                imageVector = Icons.Default.FavoriteBorder,
                iconColor = Color(0xFFE65100),
                iconBg = Color(0xFFFFF3E0),
                title = "Eat a balanced diet",
                description = "Low salt, more fruits and vegetables."
            )
            Spacer(modifier = Modifier.height(16.dp))
            RecommendationItem(
                imageVector = Icons.Default.Lightbulb,
                iconColor = Color(0xFF673AB7),
                iconBg = Color(0xFFEDE7F6),
                title = "Manage stress",
                description = "Practice breathing exercises and meditate."
            )
        }
    }
}

@Composable
fun RecommendationItem(
    imageVector: ImageVector, iconColor: Color, iconBg: Color, title: String, description: String
) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier.size(36.dp).background(iconBg, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = description, fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MetricColumnPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Row(modifier = Modifier.padding(16.dp)) {
            MetricColumn(value = "120", label = "Systolic", valueColor = Color.Blue)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BpTrendCardPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.padding(16.dp)) {
            BpTrendCard()
        }
    }
}

// 1. Full Screen Preview - Light Mode
@Preview(name = "Insights Screen Light", showBackground = true)
@Composable
fun BloodPressureInsightsScreenLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        BloodPressureInsightsScreen(
            back = {},
            navigateToCalendar = {}
        )
    }
}

// 2. Full Screen Preview - Dark Mode
@Preview(name = "Insights Screen Dark", showBackground = true)
@Composable
fun BloodPressureInsightsScreenDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        BloodPressureInsightsScreen(
            back = {},
            navigateToCalendar = {}
        )
    }
}

// 3. Isolated Component Preview for the Card Layouts
@Preview(name = "Recommendations Isolated", showBackground = true)
@Composable
fun RecommendationsCardPreview() {
    MedBuddyTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            RecommendationsCard()
        }
    }
}
