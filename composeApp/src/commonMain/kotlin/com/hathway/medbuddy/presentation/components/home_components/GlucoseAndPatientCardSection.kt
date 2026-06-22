package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.presentation.theme.Primary
import com.hathway.medbuddy.util.displayName
import com.hathway.medbuddy.util.formatDisplayDate
import com.hathway.medbuddy.util.getGreetingIconImageVector
import com.hathway.medbuddy.util.getNowLocalDateTime
import com.hathway.medbuddy.util.greetingIconColor
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun GlucoseAndPatientCardSection(
    greeting: String,
    patientName: String,
    glucoseValue: Int,
    mealType: String,
    status: String,
    minTarget: String,
    maxTarget: String,
    isToday: Boolean = true,
    modifier: Modifier = Modifier
) {
    // ✅ Fix Layer 1: Convert outer card to a non-elevated column to eliminate muddy shadow layering
    Column(
        modifier = modifier.fillMaxWidth().background(
                // Seamlessly tint the header space without stacking conflicting transparent fills
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f),
                shape = RoundedCornerShape(24.dp)
            ).border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                shape = RoundedCornerShape(24.dp)
            )
    ) {
        // Patient Header Block Area
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
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

                val dateLabel =
                    if (isToday) stringResource(Res.string.today) else stringResource(Res.string.last_reading)
                Text(
                    text = "$dateLabel • ${formatDisplayDate(getNowLocalDateTime().date)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        // ✅ Fix Layer 2: Inner metric container card now sits cleanly with crisp surface boundaries
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            border = BorderStroke(
                width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isToday) stringResource(Res.string.todays_glucose) else stringResource(
                            Res.string.last_reading
                        ),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

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

                    Spacer(modifier = Modifier.height(12.dp))

                    StatusChip(status)

                    Spacer(modifier = Modifier.height(14.dp))

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
