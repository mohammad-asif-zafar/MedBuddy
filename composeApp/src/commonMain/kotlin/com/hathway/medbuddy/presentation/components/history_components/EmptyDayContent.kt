package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.calendar_emoji
import medbuddy.composeapp.generated.resources.no_glucose_records_day
import org.jetbrains.compose.resources.stringResource


/**
 * Empty state screen.
 *
 * Shown when selected date
 * has no glucose records.
 */

@Composable
fun EmptyDayContent() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(Res.string.calendar_emoji), fontSize = 64.sp
        )

        Spacer(
            Modifier.height(16.dp)
        )

        Text(
            text = stringResource(
                Res.string.no_glucose_records_day
            ), color = Color.Gray
        )
    }
}