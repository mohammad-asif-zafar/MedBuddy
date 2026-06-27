package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.domain.model.CalendarDayStatus
import com.hathway.medbuddy.presentation.theme.StatusHigh
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.StatusLow

@Composable
fun CalendarDayCell(
    day: Int, status: CalendarDayStatus?, isSelected: Boolean, onClick: () -> Unit
) {
    Column(
        modifier = Modifier.aspectRatio(1f).clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier.size(34.dp).background(
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else Color.Transparent, shape = CircleShape
            ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.toString(),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) FontWeight.Bold
                else FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            if ((status?.lowCount ?: 0) > 0) {
                StatusDot(StatusLow)
            }

            if ((status?.inRangeCount ?: 0) > 0) {
                StatusDot(StatusInRange)
            }

            if ((status?.highCount ?: 0) > 0) {
                StatusDot(StatusHigh)
            }
        }
    }
}


@Composable
fun StatusDot(color: Color) {
    Box(
        modifier = Modifier.size(5.dp).background(
            color = color, shape = CircleShape
        )
    )
}


