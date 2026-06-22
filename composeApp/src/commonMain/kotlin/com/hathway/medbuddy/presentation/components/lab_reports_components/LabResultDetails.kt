package com.hathway.medbuddy.presentation.components.lab_reports_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.domain.model.LabResultData
import com.hathway.medbuddy.presentation.theme.StatusInRange
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.btn_share_report
import medbuddy.composeapp.generated.resources.lab_details_delete
import medbuddy.composeapp.generated.resources.lab_details_report
import medbuddy.composeapp.generated.resources.lab_form_date
import medbuddy.composeapp.generated.resources.lab_form_notes
import medbuddy.composeapp.generated.resources.lab_form_ref_range
import org.jetbrains.compose.resources.stringResource

@Composable
fun LabResultDetails(result: LabResultData?, onShareClick: () -> Unit, onInsightsClick: () -> Unit) {
    if (result == null) return

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(result.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(result.value, fontSize = 36.sp, fontWeight = FontWeight.Black, color = result.statusColor)
                    Spacer(Modifier.width(16.dp))
                    Surface(color = result.statusColor.copy(alpha = 0.1f), shape = CircleShape) {
                        Text(result.status, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), color = result.statusColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
            IconButton(onClick = onInsightsClick) { Icon(Icons.Outlined.Lightbulb, null, tint = MaterialTheme.colorScheme.primary) }
        }

        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(stringResource(Res.string.lab_form_date), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(result.date, fontWeight = FontWeight.Bold)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(stringResource(Res.string.lab_form_ref_range), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("4.0 - 6.0 %", fontWeight = FontWeight.Bold)
            }
        }

        // Custom Reference Range Scale UI
        Box(modifier = Modifier.fillMaxWidth().height(40.dp)) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val trackHeight = 8.dp.toPx()
                val radius = 4.dp.toPx()
                drawLine(Color.Gray.copy(alpha = 0.2f), Offset(0f, size.height/2), Offset(size.width, size.height/2), trackHeight, cap = androidx.compose.ui.graphics.StrokeCap.Round)

                // Normal range highlight
                drawLine(StatusInRange.copy(alpha = 0.5f), Offset(size.width * 0.4f, size.height/2), Offset(size.width * 0.6f, size.height/2), trackHeight)

                // Pointer
                drawCircle(StatusInRange, radius = 8.dp.toPx(), center = Offset(size.width * 0.55f, size.height/2))
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(stringResource(Res.string.lab_form_notes), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Fasting test")
            }
        }

        Text(stringResource(Res.string.lab_details_report), fontWeight = FontWeight.Bold)
        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.PictureAsPdf, null, tint = Color.Red)
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("HbA1c_Report_12May.pdf", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                    Text("PDF • 250 KB", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                }
                Icon(Icons.Default.Download, null)
            }
        }

        Spacer(Modifier.weight(1f))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onShareClick, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
                Icon(Icons.Default.Share, null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(stringResource(Res.string.btn_share_report))
            }
            Button(onClick = {}, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                Text(stringResource(Res.string.lab_details_delete))
            }
        }
    }
}
