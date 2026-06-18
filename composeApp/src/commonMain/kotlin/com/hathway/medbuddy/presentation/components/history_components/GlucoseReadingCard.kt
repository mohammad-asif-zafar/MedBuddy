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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Displays one glucose reading card.
 *
 * Example:
 *
 * Before Breakfast
 * 95 mg/dL
 * In Range
 *
 * Dynamically determines:
 * - Icon
 * - Target range
 * - Status
 * - Status color
 */

@Composable
fun GlucoseReadingCard(
    label: String, value: Int, time: String
) {

    val uiModel = remember(label, value) {
        GlucoseReadingMapper.map(
            label = label, value = value
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
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
                        color = Color(0xFF1A1A1A)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = time,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
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
                            color = Color(0xFF1A1A1A),
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "mg/dL",
                            fontSize = 13.sp,
                            color = Color.Gray,
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
                    text = "Target: ${uiModel.minTarget}-${uiModel.maxTarget} mg/dL",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
