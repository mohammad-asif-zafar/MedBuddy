package com.hathway.medbuddy.presentation.components.app_theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import medbuddy.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val containerColor = if (isDarkTheme) Color(0xFF131314) else Color.White
    val borderColor = if (isDarkTheme) Color(0xFF8E918F) else Color(0xFFDADCE0)
    val textColor = if (isDarkTheme) Color(0xFFE3E3E3) else Color(0xFF1F1F1F)

    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(26.dp),
        color = containerColor,
        border = BorderStroke(1.dp, borderColor),
        tonalElevation = 1.dp,
        shadowElevation = 1.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            GoogleLogoProcedural(modifier = Modifier.size(18.dp))

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(Res.string.continue_with_google),
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.25.sp
            )
        }
    }
}

@Composable
private fun GoogleLogoProcedural(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val size = size.minDimension
        val strokeWidth = size * 0.22f
        
        // Colors
        val red = Color(0xFFEA4335)
        val yellow = Color(0xFFFBBC05)
        val green = Color(0xFF34A853)
        val blue = Color(0xFF4285F4)

        // 1. Red (Top)
        drawArc(
            color = red,
            startAngle = 180f + 45f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
            topLeft = Offset(0f, 0f),
            size = Size(size, size)
        )
        
        // 2. Yellow (Left)
        drawArc(
            color = yellow,
            startAngle = 135f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
            topLeft = Offset(0f, 0f),
            size = Size(size, size)
        )

        // 3. Green (Bottom)
        drawArc(
            color = green,
            startAngle = 45f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
            topLeft = Offset(0f, 0f),
            size = Size(size, size)
        )

        // 4. Blue (Right + Arm)
        drawArc(
            color = blue,
            startAngle = -45f,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Butt),
            topLeft = Offset(0f, 0f),
            size = Size(size, size)
        )
        
        // Horizontal Arm
        drawLine(
            color = blue,
            start = Offset(center.x, center.y),
            end = Offset(size, center.y),
            strokeWidth = strokeWidth
        )
    }
}
