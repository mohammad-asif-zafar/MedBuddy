package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.LabResultsScreen

@Composable
fun LabReportsContent(onBack: () -> Unit, navigation: () -> Unit) {
    LabResultsScreen(onBack, navigation)
}