package com.hathway.medbuddy.components

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.data.TimePeriod

@Composable
fun TimePeriodDropdown(
    selected: TimePeriod,
    onSelected: (TimePeriod) -> Unit
) {
    AppDropdownField(
        label = "Select Time Period",
        items = TimePeriod.values().toList(),
        selectedItem = selected,
        onItemSelected = onSelected,
        itemToString = { it.displayName }
    )
}