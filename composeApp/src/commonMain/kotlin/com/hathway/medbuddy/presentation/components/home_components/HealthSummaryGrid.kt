package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.presentation.theme.*
import org.jetbrains.compose.resources.stringResource
import medbuddy.composeapp.generated.resources.*
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.ThemeMode

@Composable
fun HealthSummaryGrid(
    average: Int, hbA1c: Double, highest: Int, lowest: Int
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SummaryCard(
                title = stringResource(Res.string.average),
                value = if (average > 0) {
                    average.toString()
                } else {
                    stringResource(Res.string.not_available)
                },
                unit = stringResource(Res.string.glucose_unit),
                valueColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f)
            )

            SummaryCard(
                title = stringResource(Res.string.hba1c),
                value = if (hbA1c > 0) "${((hbA1c * 10).toInt() / 10.0)}%"
                else stringResource(Res.string.not_available),
                subtitle = stringResource(Res.string.good),
                valueColor = StatusInRange,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            SummaryCard(
                title = stringResource(Res.string.highest),
                value = if (highest > 0) highest.toString()
                else stringResource(Res.string.not_available),
                unit = stringResource(Res.string.glucose_unit),
                valueColor = StatusHigh,
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = stringResource(Res.string.lowest),
                value = if (lowest > 0) lowest.toString() else stringResource(Res.string.not_available),
                unit = stringResource(Res.string.glucose_unit),
                valueColor = StatusLow,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun HealthSummaryGridPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        HealthSummaryGrid(
            average = 115,
            hbA1c = 5.7,
            highest = 145,
            lowest = 85
        )
    }
}
