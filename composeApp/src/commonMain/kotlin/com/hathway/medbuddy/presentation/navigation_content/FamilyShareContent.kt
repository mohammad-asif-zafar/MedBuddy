package com.hathway.medbuddy.presentation.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.FamilyCareScreen

@Composable
fun FamilyShareContent(onBack: () -> Unit, navigation: () -> Unit) {
    FamilyCareScreen(onBack, navigation)
}