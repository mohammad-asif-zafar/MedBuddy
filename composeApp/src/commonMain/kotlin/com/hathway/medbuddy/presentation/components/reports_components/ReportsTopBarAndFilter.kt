package com.hathway.medbuddy.presentation.components.reports_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReportsTopBarAndFilter(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onMenuClick: () -> Unit,
    onCalendarClick: () -> Unit
) {
    val filters = listOf("7 Days", "30 Days", "90 Days", "Custom")
    val brandGreen = Color(0xFF1B5E20)

    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
            }
            Text("Reports", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            IconButton(onClick = onCalendarClick) {
                Icon(Icons.Default.DateRange, contentDescription = "Calendar", tint = brandGreen)
            }
        }

        // Horizontal Selector Row
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                val isSelected = filter == selectedFilter
                Surface(
                    modifier = Modifier.weight(1f).clickable { onFilterSelected(filter) },
                    shape = RoundedCornerShape(50),
                    color = if (isSelected) brandGreen else Color(0xFFF7F7EE),
                    border = if (!isSelected) BorderStroke(
                        1.dp, Color.LightGray.copy(alpha = 0.5f)
                    ) else null
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = filter,
                            color = if (isSelected) Color.White else Color.Black,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        if (filter == "Custom") {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = if (isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
