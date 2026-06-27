package com.hathway.medbuddy.presentation.components.blood_pressure_components

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.detailed_reports.DetailedReportWrapper
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.title_blood_pressure_saved_Reading
import org.jetbrains.compose.resources.stringResource
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme

@Composable
fun BloodPressureViewReadingScreen(navigateToHomeBP: () -> Unit, navigateToAdd: () -> Unit) {
    DetailedReportWrapper(stringResource(Res.string.title_blood_pressure_saved_Reading)) {

        Column(
            modifier = Modifier.fillMaxSize().background(Color.White).padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // 1. Success Checkmark Animated Badge
            Box(
                modifier = Modifier.size(100.dp).background(Color(0xFFE6F7ED), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success Notification Symbol",
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Main Title Text Headline
            Text(
                text = "Reading Saved!", style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold, color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Informative Helper Status Subtext Description Block
            Text(
                text = "Your blood pressure reading\nhas been saved successfully.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray, lineHeight = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            // 4. Summarized Metric Overview Data Display Card Content
            Column(
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color(0xFFE2E8F0), RoundedCornerShape(16.dp)).padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "120/80", style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 28.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "mmHg", style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray, fontWeight = FontWeight.Normal
                        ), modifier = Modifier.align(Alignment.Bottom).padding(bottom = 4.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    // Blood Pressure Normal Indicator Badge Status Node
                    Box(
                        modifier = Modifier.background(Color(0xFFE6F7ED), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Normal", style = MaterialTheme.typography.labelMedium.copy(
                                color = Color(0xFF10B981), fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Date and Time Metadata Readout Text line
                Text(
                    text = "23 Jun 2026, 08:30 AM",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Black, fontWeight = FontWeight.Medium
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Core Pulse Metrics Sub-Indicator Layout
                Text(
                    text = "Pulse: 72 bpm", style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // 5. Dashboard Primary Navigation Action Button Execution Row
            Button(
                onClick = navigateToHomeBP,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688)) // Theme Specific Teal Action Color Accent
            ) {
                Text(
                    text = "View Dashboard", style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold, color = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 6. Secondary Clear Alternate Action Interface Link Row
            TextButton(
                onClick = navigateToAdd, modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Text(
                    text = "Add Another Reading", style = MaterialTheme.typography.bodyLarge.copy(
                        color = Color(0xFF009688), fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "Blood Pressure Saved Light Mode",
    device = "spec:width=1080px,height=2400px,dpi=440" // Matches standard device viewport scale
)
@Composable
fun BloodPressureViewReadingScreenPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        BloodPressureViewReadingScreen(
            navigateToHomeBP = { /* Navigation Action Click Mocked */ },
            navigateToAdd = { /* Navigation Action Click Mocked */ }
        )
    }
}

@Preview(
    showBackground = true,
    name = "Blood Pressure Saved Dark Mode",
    device = "spec:width=1080px,height=2400px,dpi=440"
)
@Composable
fun BloodPressureViewReadingScreenDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        BloodPressureViewReadingScreen(
            navigateToHomeBP = {},
            navigateToAdd = {}
        )
    }
}
