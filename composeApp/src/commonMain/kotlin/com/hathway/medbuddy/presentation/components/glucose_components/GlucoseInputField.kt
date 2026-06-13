package com.hathway.medbuddy.presentation.components.glucose_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.hathway.medbuddy.domain.model.TimePeriod
import com.hathway.medbuddy.domain.model.getGlucoseRange

@Composable
fun GlucoseInputField(
    value: String,
    onValueChange: (String) -> Unit,
    timePeriod: TimePeriod,
    modifier: Modifier = Modifier
) {

    val glucoseInt = value.toIntOrNull()
    val (min, max) = getGlucoseRange(timePeriod)

    val isLow = glucoseInt != null && glucoseInt < min
    val isHigh = glucoseInt != null && glucoseInt > max
    val isNormal = glucoseInt != null && glucoseInt in min..max

    OutlinedTextField(
        value = value, onValueChange = { it ->
            val filtered = it.filter { it.isDigit() }.take(3)
            onValueChange(filtered)
        },

        label = {
            Text("${timePeriod.displayName} (${timePeriod.shortName})")
        },

        modifier = modifier.fillMaxWidth(),

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),

        isError = isLow || isHigh,

        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = when {
                isLow -> Color(0xFF3B82F6)
                isHigh -> Color(0xFFEF4444)
                isNormal -> Color(0xFF10B981)
                else -> MaterialTheme.colorScheme.primary
            }, unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),

        supportingText = {
            when {
                glucoseInt == null -> {
                    Text("Target: $min–$max mg/dL")
                }

                isLow -> {
                    Text("Low ⚠ ($glucoseInt)", color = Color(0xFF3B82F6))
                }

                isHigh -> {
                    Text("High ⚠ ($glucoseInt)", color = Color(0xFFEF4444))
                }

                isNormal -> {
                    Text("Normal ✓", color = Color(0xFF10B981))
                }
            }
        })
}