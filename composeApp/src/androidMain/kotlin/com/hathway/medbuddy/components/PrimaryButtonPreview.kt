package com.hathway.medbuddy.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.components.glucose_components.PrimaryButton
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme

@Composable
@Preview(showBackground = true)
fun PrimaryButtonPreview() {
    MedBuddyTheme {
        PrimaryButton(
            text = "Primary Button",
            onClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PrimaryButtonLoadingPreview() {
    MedBuddyTheme {
        PrimaryButton(
            text = "Primary Button",
            onClick = {},
            isLoading = true
        )
    }
}
