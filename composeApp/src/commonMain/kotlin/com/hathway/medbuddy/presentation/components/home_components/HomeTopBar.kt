package com.hathway.medbuddy.presentation.components.home_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/*
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
}*/

import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Badge

@Composable
fun MedBuddyTopBar(
    title: String,
    modifier: Modifier = Modifier,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = null,
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    showBadge: Boolean = false
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (leftIcon != null) {
            IconButton(
                onClick = onLeftClick
            ) {
                Icon(
                    imageVector = leftIcon, contentDescription = null
                )
            }
        } else {
            Spacer(Modifier.width(32.dp))
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = titleColor
        )

        if (rightIcon != null) {
            IconButton(
                onClick = onRightClick
            ) {
                BadgedBox(
                    badge = {
                        if (showBadge) {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        }
                    }) {
                    Icon(
                        imageVector = rightIcon, contentDescription = null
                    )
                }
            }
        } else {
            Spacer(
                modifier = Modifier.width(48.dp)
            )
        }
    }
}