package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.util.displayName
import com.hathway.medbuddy.util.formatDisplayDate
import com.hathway.medbuddy.util.getGreetingIconImageVector
import com.hathway.medbuddy.util.getNowLocalDateTime
import com.hathway.medbuddy.util.greetingIconColor
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun IntegratedGlucoseCardSection(
    greeting: String,
    patientName: String,
    glucoseValue: Int,
    mealType: String,
    status: String,
    minTarget: String,
    maxTarget: String,
    isToday: Boolean = true
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "$greeting ${patientName.displayName()} ",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Icon(
                            imageVector = getGreetingIconImageVector(),
                            contentDescription = null,
                            tint = greetingIconColor(),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    val dateLabel = if (isToday) stringResource(Res.string.today) else stringResource(Res.string.last_reading)
                    Text(
                        text = "$dateLabel • ${
                            formatDisplayDate(
                                getNowLocalDateTime().date
                            )
                        }", 
                        style = MaterialTheme.typography.bodySmall, 
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(
                    topStart = 20.dp, topEnd = 20.dp, bottomStart = 24.dp, bottomEnd = 24.dp
                ), 
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ), 
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isToday) stringResource(Res.string.todays_glucose) else stringResource(Res.string.last_reading),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = mealType,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = glucoseValue.toString(),
                                fontSize = 44.sp,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = " " + stringResource(Res.string.glucose_unit),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        StatusChip(status)

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = stringResource(Res.string.glucose_target, minTarget, maxTarget),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    GlucoseMeter(
                        value = glucoseValue, modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun PatientGreetingCardPreview() {
    MaterialTheme {
        IntegratedGlucoseCardSection(
            greeting = "Good Afternoon",
            patientName = "Shagufta Zafar",
            status = "In Range",
            glucoseValue = 7,
            mealType = "BFF",
            minTarget = "121",
            maxTarget = "121"
        )
    }
}
