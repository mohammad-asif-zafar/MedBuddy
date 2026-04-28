package com.hathway.medbuddy.components

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDate

@Composable
expect fun NativeDatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    initialDate: LocalDate
)
