package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.medbuddy
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeTopBar() {

    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = { }) {
            Icon(
                Icons.Default.Menu, contentDescription = null
            )
        }

        Text(
            text = stringResource(Res.string.medbuddy),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4F6B35)
        )

        Spacer(Modifier.weight(1f))

        IconButton(
            onClick = { }) {
            Icon(
                Icons.Outlined.Notifications, contentDescription = null
            )
        }
    }
}