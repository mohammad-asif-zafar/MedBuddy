package com.hathway.medbuddy.presentation.components.history_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.glucose_target
import medbuddy.composeapp.generated.resources.glucose_unit
import org.jetbrains.compose.resources.stringResource

@Composable
fun GlucoseReadingCard(
    label: String, value: Int, time: String
) {
    val uiModel = GlucoseReadingMapper.map(
        label = label, value = value
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.Top
        ) {
            // Context Graphic Block Frame
            Box(
                modifier = Modifier.size(46.dp)
                    .background(uiModel.iconBgColor, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = uiModel.icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = uiModel.iconTint
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Text Metadata Frame Node
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Row 1: Header Text Info Slot
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = time,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Box(
                            modifier = Modifier.size(8.dp)
                                .background(uiModel.statusColor, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Row 2: Metrics Baseline-aligned layout
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = value.toString(),
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(Res.string.glucose_unit),
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.alignByBaseline()
                        )
                    }

                    Text(
                        text = uiModel.statusText,
                        color = uiModel.statusColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Row 3: Guidelines Context Node
                Text(
                    text = stringResource(Res.string.glucose_target, uiModel.minTarget, uiModel.maxTarget),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}



@Preview
@Composable
fun GlucoseReadingCardPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Normal Range Sample",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            // Simulates a safe fasting glucose level
            GlucoseReadingCard(
                label = "Before Breakfast",
                value = 95,
                time = "08:15 AM"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Elevated/Spike Range Sample",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            // Simulates an elevated post-meal glucose level to verify UI tint transformations
            GlucoseReadingCard(
                label = "After Dinner",
                value = 165,
                time = "09:30 PM"
            )
        }
    }
}

