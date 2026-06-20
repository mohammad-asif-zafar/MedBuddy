package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.theme.StatusHigh
import com.hathway.medbuddy.presentation.theme.StatusInRange
import com.hathway.medbuddy.presentation.theme.StatusLow
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarLegend() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(
                horizontal = 20.dp, vertical = 14.dp
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(
                color = StatusLow, 
                label = stringResource(Res.string.low)
            )

            LegendItem(
                color = StatusInRange, 
                label = stringResource(Res.string.status_in_range)
            )

            LegendItem(
                color = StatusHigh, 
                label = stringResource(Res.string.high)
            )

            LegendItem(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), 
                label = stringResource(Res.string.no_record)
            )
        }
    }
}
