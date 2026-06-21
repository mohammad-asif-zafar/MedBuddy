package com.hathway.medbuddy.presentation.components.app_theme

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Bedtime
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.ThemeMode


@Composable
fun ThemeIllustration(mode: ThemeMode) {
    val bgColor = when (mode) {
        ThemeMode.LIGHT -> Color(0xFFF0F2F5)
        ThemeMode.DARK -> Color(0xFF1A1C1E)
        ThemeMode.SYSTEM -> Color(0xFFE2E2E6)
    }

    val boxColor = when (mode) {
        ThemeMode.LIGHT -> Color.White
        ThemeMode.DARK -> Color(0xFF2D3135)
        ThemeMode.SYSTEM -> Color.White.copy(alpha = 0.7f)
    }

    val toggleTrackColor = when (mode) {
        ThemeMode.LIGHT -> Color(0xFFE1E2E5)
        ThemeMode.DARK -> Color(0xFF3F474E)
        ThemeMode.SYSTEM -> Color(0xFFD1D3D6)
    }

    val toggleThumbColor = when (mode) {
        ThemeMode.LIGHT -> Color(0xFF74777F)
        ThemeMode.DARK -> Color.White
        ThemeMode.SYSTEM -> Color(0xFF74777F)
    }

    Box(
        modifier = Modifier.fillMaxWidth().height(80.dp).clip(RoundedCornerShape(12.dp))
            .background(bgColor), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Mock UI elements inside illustration
            Row(
                modifier = Modifier.fillMaxWidth(0.7f).height(30.dp).clip(RoundedCornerShape(8.dp))
                    .background(boxColor).padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (mode == ThemeMode.DARK) {
                    Icon(
                        imageVector = Icons.Outlined.Bedtime,
                        contentDescription = null,
                        tint = Color(0xFFD1E4FF),
                        modifier = Modifier.size(14.dp)
                    )
                    // star
                    Box(
                        modifier = Modifier.size(4.dp).clip(CircleShape)
                            .background(Color(0xFFD1E4FF))
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.LightMode,
                        contentDescription = null,
                        tint = Color(0xFFFFB900),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mock Toggle
            Box(
                modifier = Modifier.width(34.dp).height(18.dp).clip(RoundedCornerShape(50))
                    .background(toggleTrackColor),
                contentAlignment = if (mode == ThemeMode.DARK) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier.padding(2.dp).size(14.dp).clip(CircleShape)
                        .background(toggleThumbColor)
                )
            }
        }
    }
}
