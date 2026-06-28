package com.hathway.medbuddy.presentation.components.glucose_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.getGlucoseRange
import com.hathway.medbuddy.presentation.theme.Danger
import com.hathway.medbuddy.presentation.theme.Primary
import com.hathway.medbuddy.presentation.theme.Success
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun GlucoseInputField(
    value: String,
    onValueChange: (String) -> Unit,
    timePeriod: TimePeriod,
    modifier: Modifier = Modifier
) {

    val glucoseInt = value.toIntOrNull()
    val (min, max) = getGlucoseRange(timePeriod)
    val focusManager = LocalFocusManager.current

    val isLow = glucoseInt != null && glucoseInt < min
    val isHigh = glucoseInt != null && glucoseInt > max
    val isNormal = glucoseInt != null && glucoseInt in min..max

    val displayName = when (timePeriod) {
        TimePeriod.BEFORE_BREAKFAST -> stringResource(Res.string.before_breakfast)
        TimePeriod.AFTER_BREAKFAST -> stringResource(Res.string.after_breakfast)
        TimePeriod.BEFORE_LUNCH -> stringResource(Res.string.before_lunch)
        TimePeriod.AFTER_LUNCH -> stringResource(Res.string.after_lunch)
        TimePeriod.BEFORE_DINNER -> stringResource(Res.string.before_dinner)
        TimePeriod.AFTER_DINNER -> stringResource(Res.string.after_dinner)
        TimePeriod.BEDTIME -> stringResource(Res.string.bedtime)
    }

    val shortName = when (timePeriod) {
        TimePeriod.BEFORE_BREAKFAST -> stringResource(Res.string.before_breakfast_short)
        TimePeriod.AFTER_BREAKFAST -> stringResource(Res.string.after_breakfast_short)
        TimePeriod.BEFORE_LUNCH -> stringResource(Res.string.before_lunch_short)
        TimePeriod.AFTER_LUNCH -> stringResource(Res.string.after_lunch_short)
        TimePeriod.BEFORE_DINNER -> stringResource(Res.string.before_dinner_short)
        TimePeriod.AFTER_DINNER -> stringResource(Res.string.after_dinner_short)
        TimePeriod.BEDTIME -> stringResource(Res.string.bedtime_short)
    }

    OutlinedTextField(
        value = value, onValueChange = { it ->
            val filtered = it.filter { it.isDigit() }.take(3)
            onValueChange(filtered)
        },

        label = {
            Text(
                "$displayName ($shortName)",
                modifier = Modifier.padding(5.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        },

        modifier = modifier.fillMaxWidth(),

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),

        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus() // hides keyboard
            }),

        isError = isLow || isHigh,

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = when {
                isLow -> Primary
                isHigh -> Danger
                isNormal -> Success
                else -> MaterialTheme.colorScheme.primary
            }, unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),

        supportingText = {
            when {
                glucoseInt == null -> {
                    Text(
                        stringResource(
                            Res.string.glucose_target, min, max
                        )
                    )
                }

                isLow -> {
                    Text(
                        stringResource(
                            Res.string.glucose_low, glucoseInt
                        ), color = Primary
                    )
                }

                isHigh -> {
                    Text(
                        stringResource(
                            Res.string.glucose_high, glucoseInt
                        ), color = Danger
                    )
                }

                isNormal -> {
                    Text(
                        stringResource(Res.string.glucose_normal), color = Success
                    )
                }
            }
        })
}

@Preview
@Composable
fun GlucoseInputFieldPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        GlucoseInputField(
            value = "120",
            onValueChange = {},
            timePeriod = TimePeriod.BEFORE_BREAKFAST
        )
    }
}
