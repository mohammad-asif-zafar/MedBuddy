package com.hathway.medbuddy.presentation.components.reports_components.additional

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun AdditionalReportCard(
    title: String,
    value: String,
    icon: ImageVector,
    description: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun DoctorAppointmentReport(date: String, doctor: String) {
    AdditionalReportCard(
        title = stringResource(Res.string.report_doctor_appointments),
        value = date,
        icon = Icons.Outlined.Event,
        description = if (doctor.isNotBlank()) "With $doctor" else stringResource(Res.string.no_upcoming_appointments)
    )
}

@Composable
fun BloodTrackingReport(systolic: Int, diastolic: Int) {
    AdditionalReportCard(
        title = stringResource(Res.string.report_blood_pressure),
        value = stringResource(Res.string.bp_label, systolic, diastolic),
        icon = Icons.Outlined.Timeline,
        description = "Latest BP Reading"
    )
}

@Composable
fun WeightReport(weight: Double) {
    AdditionalReportCard(
        title = stringResource(Res.string.report_weight),
        value = stringResource(Res.string.weight_kg_label, weight.toString()),
        icon = Icons.Outlined.MonitorWeight,
        description = "Current body weight"
    )
}

@Composable
fun BMIReport(bmi: Double) {
    val category = when {
        bmi < 18.5 -> "Underweight"
        bmi < 25.0 -> "Healthy"
        bmi < 30.0 -> "Overweight"
        else -> "Obese"
    }
    AdditionalReportCard(
        title = stringResource(Res.string.report_bmi),
        value = bmi.toString(),
        icon = Icons.Outlined.Speed,
        description = "Category: $category"
    )
}
