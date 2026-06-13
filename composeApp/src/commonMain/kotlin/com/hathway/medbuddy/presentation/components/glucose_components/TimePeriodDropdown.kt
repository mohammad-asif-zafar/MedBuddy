package com.hathway.medbuddy.presentation.components.glucose_components

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.domain.model.TimePeriod

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