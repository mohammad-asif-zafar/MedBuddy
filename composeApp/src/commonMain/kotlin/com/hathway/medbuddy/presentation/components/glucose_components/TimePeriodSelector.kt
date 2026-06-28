package com.hathway.medbuddy.presentation.components.glucose_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.util.getDisplayName
import com.hathway.medbuddy.util.icon
import com.hathway.medbuddy.util.iconColor
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.time_period
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimePeriodSelector(
    selected: TimePeriod, onSelected: (TimePeriod) -> Unit
) {

    val periods = listOf(
        TimePeriod.BEFORE_BREAKFAST,
        TimePeriod.AFTER_BREAKFAST,
        TimePeriod.BEFORE_LUNCH,
        TimePeriod.AFTER_LUNCH,
        TimePeriod.BEFORE_DINNER,
        TimePeriod.BEDTIME
    )

    Column {

        Text(
            text = stringResource(Res.string.time_period),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        periods.chunked(2).forEach { rowItems ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                rowItems.forEach { period ->

                    val selectedItem = selected == period

                    Surface(
                        modifier = Modifier.weight(1f).height(60.dp).clickable {
                            onSelected(period)
                        },
                        shape = RoundedCornerShape(16.dp),
                        color = if (selectedItem) MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(
                            width = if (selectedItem) 2.dp else 1.dp,
                            color = if (selectedItem) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outline
                        )
                    ) {

                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Icon(
                                imageVector = period.icon(),
                                contentDescription = null,
                                tint = if (selectedItem) period.iconColor()
                                else period.iconColor().copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp),

                                )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                text = period.getDisplayName(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (selectedItem) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
fun TimePeriodSelectorPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        TimePeriodSelector(
            selected = TimePeriod.BEFORE_BREAKFAST,
            onSelected = {}
        )
    }
}
