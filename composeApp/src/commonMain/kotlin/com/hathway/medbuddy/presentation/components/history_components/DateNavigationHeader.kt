package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.util.getNowLocalDateTime
import kotlinx.datetime.LocalDate
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.next_symbol
import medbuddy.composeapp.generated.resources.prev_symbol
import org.jetbrains.compose.resources.stringResource

/**
 * Top navigation section.
 *
 * Features:
 * - Previous day navigation
 * - Current selected date display
 * - Open calendar picker
 * - Next day navigation
 */

@Composable
fun DateNavigationHeader(
    selectedDate: LocalDate,
    onPreviousDay: () -> Unit,
    onNextDay: () -> Unit,
    onDateClick: () -> Unit
) {
    // Need to Change for other languages
    val dayName =
        selectedDate.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
    val monthName = selectedDate.month.name.lowercase().replaceFirstChar { it.uppercase() }

    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Previous day button
        Box(
            modifier = Modifier.size(32.dp).clickable { onPreviousDay() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.prev_symbol),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Date display (clickable to open calendar)
        Box(
            modifier = Modifier.clickable { onDateClick() }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$dayName, ${selectedDate.dayOfMonth} $monthName ${selectedDate.year}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        // Next day button
        Box(
            modifier = Modifier.size(32.dp).clickable { onNextDay() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(Res.string.next_symbol),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun DateNavigationHeaderPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        DateNavigationHeader(
            selectedDate = getNowLocalDateTime().date,
            onPreviousDay = {},
            onNextDay = {},
            onDateClick = {}
        )
    }
}
