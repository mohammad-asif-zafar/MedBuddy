package com.hathway.medbuddy.components

import androidx.compose.runtime.Composable
import java.util.Date

@Composable
expect fun NativeDatePickerDialog(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    initialDate: Date
)
