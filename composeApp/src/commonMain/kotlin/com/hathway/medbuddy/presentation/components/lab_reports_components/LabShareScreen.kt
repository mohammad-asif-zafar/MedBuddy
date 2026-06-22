package com.hathway.medbuddy.presentation.components.lab_reports_components

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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.lab_share_btn
import medbuddy.composeapp.generated.resources.lab_share_with
import org.jetbrains.compose.resources.stringResource


@Composable
fun LabShareScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(Res.string.lab_share_with), fontWeight = FontWeight.Bold)
        repeat(3) {
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Surface(modifier = Modifier.size(40.dp), shape = CircleShape, color = Color.LightGray) {}
                Spacer(Modifier.width(12.dp))
                Text("Recipient Name", modifier = Modifier.weight(1f))
                Checkbox(checked = false, onCheckedChange = {})
            }
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text(stringResource(Res.string.lab_share_btn))
        }
    }
}