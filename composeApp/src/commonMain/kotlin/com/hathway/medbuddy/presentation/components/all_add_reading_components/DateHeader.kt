package com.hathway.medbuddy.presentation.components.all_add_reading_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.util.parseDisplayDate
import kotlinx.datetime.LocalDate

@Composable
fun DateHeader(
    dateString: String,
    today: LocalDate,
    yesterday: LocalDate,
    modifier: Modifier = Modifier
) {
    val date = remember(dateString) {
        try { parseDisplayDate(dateString) } catch (e: Exception) { null }
    }

    val displayTitle = when (date) {
        today -> "Today, $dateString"
        yesterday -> "Yesterday, $dateString"
        else -> dateString
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CalendarToday,
            contentDescription = null,
            // Dynamic: Secondary/onSurfaceVariant adds premium subtle contrast over primary
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = displayTitle,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            // Dynamic: Ensures readability on dark and light surfaces alike
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

// ========================
// Previews
// ========================

@Preview
@Composable
fun DateHeaderLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            DateHeader(
                dateString = "24 Oct 2023",
                today = LocalDate(2023, 10, 24),
                yesterday = LocalDate(2023, 10, 23)
            )
        }
    }
}

@Preview
@Composable
fun DateHeaderDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background).padding(16.dp)) {
            DateHeader(
                dateString = "24 Oct 2023",
                today = LocalDate(2023, 10, 24),
                yesterday = LocalDate(2023, 10, 23)
            )
        }
    }
}
