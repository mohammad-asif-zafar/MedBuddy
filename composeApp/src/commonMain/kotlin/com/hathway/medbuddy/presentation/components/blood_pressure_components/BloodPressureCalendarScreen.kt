package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.title_blood_pressure_calendar
import org.jetbrains.compose.resources.stringResource

@Composable
fun  BloodPressureCalendarScreen(back: () -> Unit) {

    DetailedReportWrapper(
        title = stringResource(Res.string.title_blood_pressure_calendar),
        onBack = { back() }) {
    }
}