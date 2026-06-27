package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.domain.model.BpReading
import com.hathway.medbuddy.domain.model.BpStatus
import com.hathway.medbuddy.domain.model.mockBpHistory
import com.hathway.medbuddy.icons.KmpComposeIcons
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.title_blood_pressure_history
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BloodPressureHistoryScreen(back: () -> Unit, navigateToCalendar: () -> Unit) {
    var selectedTab by remember { mutableStateOf(1) } // 0 = 7 Days, 1 = 30 Days, 2 = 90 Days
    val tabs = listOf("7 Days", "30 Days", "90 Days")

    DetailedReportWrapper(
        rightIcon = KmpComposeIcons.CalendarCheck,
        showRightAction = true,
        onRightActionClick = { navigateToCalendar() },
        title = stringResource(Res.string.title_blood_pressure_history),
        onBack = { back() }) {
        // 1. Segmented Timeframe Toggle Selector
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)).padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            tabs.forEachIndexed { index, text ->
                val isSelected = selectedTab == index
                Box(
                    modifier = Modifier.weight(1f).clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) Color(0xFFE8EAF6) else Color.Transparent)
                        .clickable { selectedTab = index }.padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                        color = if (isSelected) Color(0xFF3F51B5) else Color.DarkGray
                    )
                }
            }
        }

        // 2. Grouped Chronological History List
        LazyColumn(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            mockBpHistory.forEach { (date, readings) ->
                stickyHeader {
                    Text(
                        text = date,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(vertical = 8.dp)
                    )
                }

                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column {
                            readings.forEachIndexed { index, reading ->
                                BpReadingRow(
                                    reading = reading, onClick = { /* Navigate to detail */ })
                                if (index < readings.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        color = Color(0xFFEEEEEE)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BpReadingRow(reading: BpReading, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }
        .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = reading.time,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.weight(1.2f)
        )
        Text(
            text = "${reading.systolic}/${reading.diastolic}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = reading.pulse.toString(),
            fontSize = 15.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Row(
            modifier = Modifier.weight(1.5f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = reading.status.label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = reading.status.color
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Details",
                tint = Color.LightGray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}


// 1. Dual Screen-Level Preview (Light & Dark)
@Preview(name = "Light Mode", showBackground = true)
@Composable
fun BpHistoryLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        BloodPressureHistoryScreen(back = {}, navigateToCalendar = {})
    }
}

@Preview(name = "Dark Mode", showBackground = true)
@Composable
fun BpHistoryDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        BloodPressureHistoryScreen(back = {}, navigateToCalendar = {})
    }
}

// 2. Row Component State Preview using Parameters
@Preview(name = "State Variations", showBackground = true)
@Composable
fun BpRowPreview(
    @PreviewParameter(BpStateProvider::class) reading: BpReading
) {
    MedBuddyTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            BpReadingRow(reading = reading, onClick = {})
        }
    }
}

// 3. Provider for state variations
class BpStateProvider : PreviewParameterProvider<BpReading> {
    override val values = sequenceOf(
        BpReading("8:30 AM", 120, 80, 72, BpStatus.NORMAL),
        BpReading("9:00 PM", 130, 85, 76, BpStatus.ELEVATED)
    )
}
