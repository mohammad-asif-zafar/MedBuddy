package com.hathway.medbuddy.presentation.components.glucose_components

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.domain.model.TimePeriod

import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun TimePeriodDropdown(
    selected: TimePeriod,
    onSelected: (TimePeriod) -> Unit
) {
    val items = TimePeriod.values().toList()
    val itemLabels = items.associateWith { item ->
        when (item) {
            TimePeriod.BEFORE_BREAKFAST -> stringResource(Res.string.before_breakfast)
            TimePeriod.AFTER_BREAKFAST -> stringResource(Res.string.after_breakfast)
            TimePeriod.BEFORE_LUNCH -> stringResource(Res.string.before_lunch)
            TimePeriod.AFTER_LUNCH -> stringResource(Res.string.after_lunch)
            TimePeriod.BEFORE_DINNER -> stringResource(Res.string.before_dinner)
            TimePeriod.AFTER_DINNER -> stringResource(Res.string.after_dinner)
            TimePeriod.BEDTIME -> stringResource(Res.string.bedtime)
        }
    }

    AppDropdownField(
        label = stringResource(Res.string.select_time_period),
        items = items,
        selectedItem = selected,
        onItemSelected = onSelected,
        itemToString = { itemLabels[it] ?: "" }
    )
}
