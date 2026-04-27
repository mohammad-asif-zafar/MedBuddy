package com.hathway.medbuddy.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Date
import java.util.Calendar

@Composable
actual fun NativeDatePickerDialog(
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit,
    initialDate: Date
) {
    val context = LocalContext.current
    
    val calendar = Calendar.getInstance()
    calendar.time = initialDate
    
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    
    android.app.DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
            onDateSelected(selectedCalendar.time)
        },
        year,
        month,
        day
    ).apply {
        setOnDismissListener { onDismiss() }
        show()
    }
}
