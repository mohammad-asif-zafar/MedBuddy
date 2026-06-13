package com.hathway.medbuddy.presentation.components.glucose_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import kotlinx.datetime.LocalDate

@Composable
actual fun NativeDatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    initialDate: LocalDate
) {
    val context = LocalContext.current

    val year = initialDate.year
    val month = initialDate.monthNumber - 1 // Android month = 0-based
    val day = initialDate.dayOfMonth

    android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->

            // ✅ Convert directly to LocalDate
            val selectedDate = LocalDate(
                year = selectedYear,
                monthNumber = selectedMonth + 1,
                dayOfMonth = selectedDayOfMonth
            )

            onDateSelected(selectedDate)
        },
        year,
        month,
        day
    ).apply {
        setOnDismissListener { onDismiss() }
        show()
    }
}