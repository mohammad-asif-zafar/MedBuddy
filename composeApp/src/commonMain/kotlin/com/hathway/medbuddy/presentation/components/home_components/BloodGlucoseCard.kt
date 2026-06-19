package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.glucose_target
import medbuddy.composeapp.generated.resources.glucose_unit
import medbuddy.composeapp.generated.resources.todays_glucose
import org.jetbrains.compose.resources.stringResource

@Composable
fun BloodGlucoseCard(
    glucoseValue: Int, mealType: String, status: String, minTarget: String, maxTarget: String
) {
    println(status)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            // ✅ Set to an absolute white color or use surface token depending on your setup
            containerColor = Color.White
        ),
        // ✅ Removed the border stroke entirely to match the flat reference style
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(Res.string.todays_glucose),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = mealType,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = glucoseValue.toString(),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = stringResource(Res.string.glucose_unit),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(12.dp))

                StatusChip(status)

                Spacer(Modifier.height(12.dp))

                Text(
                    text = stringResource(
                        Res.string.glucose_target, minTarget, maxTarget
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            GlucoseMeter(
                value = glucoseValue, modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BloodGlucoseCardPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            BloodGlucoseCard(
                status = "In Range",
                glucoseValue = 7,
                mealType ="BFF",
                minTarget ="121",
                maxTarget ="121"
            )
        }
    }
}