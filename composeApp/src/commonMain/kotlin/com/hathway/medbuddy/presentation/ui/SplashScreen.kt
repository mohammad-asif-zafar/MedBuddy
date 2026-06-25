package com.hathway.medbuddy.presentation.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hathway.medbuddy.ThemeMode
import com.hathway.medbuddy.presentation.theme.MedBuddyTheme
import com.hathway.medbuddy.presentation.theme.Primary
import kotlinx.coroutines.delay
import medbuddy.composeapp.generated.resources.Res
import medbuddy.composeapp.generated.resources.medbuddy_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onSplashFinished()
    }

    // Dynamic brand colors extracted directly from your image asse
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Native Custom Canvas Logo Component
            // KMP resource image loader
            Image(
                painter = painterResource(Res.drawable.medbuddy_logo), // Make sure your filename matches this resource name
                contentDescription = "MedBuddy App Logo",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "MedBuddy",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Smart Glucose Care",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    tealColor: Color,
    yellowColor: Color,
    backgroundColor: Color
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // 1. Draw Large Outer Droplet Shape Base
        val dropPath = Path().apply {
            moveTo(w * 0.5f, h * 0.05f)
            cubicTo(w * 0.5f, h * 0.05f, w * 0.95f, h * 0.45f, w * 0.95f, h * 0.68f)
            cubicTo(w * 0.95f, h * 0.92f, w * 0.75f, h * 0.95f, w * 0.5f, h * 0.95f)
            cubicTo(w * 0.25f, h * 0.95f, w * 0.05f, h * 0.92f, w * 0.05f, h * 0.68f)
            cubicTo(w * 0.05f, h * 0.45f, w * 0.5f, h * 0.05f, w * 0.5f, h * 0.05f)
            close()
        }
        drawPath(path = dropPath, color = backgroundColor)

        // 2. Draw Glucometer Device Frame
        val meterW = w * 0.36f
        val meterH = meterW * 1.25f
        val meterX = (w - meterW) / 2f
        val meterY = h * 0.28f

        drawRoundRect(
            color = tealColor,
            topLeft = Offset(meterX, meterY),
            size = Size(meterW, meterH),
            cornerRadius = CornerRadius(meterW * 0.3f, meterW * 0.3f),
            style = Stroke(width = w * 0.05f)
        )

        // 3. Draw Inner Display Window Line
        val innerWindowW = meterW * 0.6f
        val innerWindowH = innerWindowW * 0.65f
        drawRoundRect(
            color = tealColor,
            topLeft = Offset(meterX + (meterW - innerWindowW) / 2f, meterY + meterH * 0.15f),
            size = Size(innerWindowW, innerWindowH),
            cornerRadius = CornerRadius(meterW * 0.1f, meterW * 0.1f),
            style = Stroke(width = w * 0.025f)
        )

        // 4. Draw Small Droplet Inside Display
        val innerDropW = innerWindowW * 0.4f
        val innerDropH = innerWindowH * 0.7f
        val innerDropX = meterX + (meterW / 2f)
        val innerDropY = meterY + meterH * 0.22f

        val innerDropPath = Path().apply {
            moveTo(innerDropX, innerDropY)
            cubicTo(innerDropX, innerDropY, innerDropX + innerDropW * 0.5f, innerDropY + innerDropH * 0.6f, innerDropX + innerDropW * 0.5f, innerDropY + innerDropH * 0.8f)
            cubicTo(innerDropX + innerDropW * 0.5f, innerDropY + innerDropH, innerDropX - innerDropW * 0.5f, innerDropY + innerDropH, innerDropX - innerDropW * 0.5f, innerDropY + innerDropH * 0.8f)
            cubicTo(innerDropX - innerDropW * 0.5f, innerDropY + innerDropH * 0.6f, innerDropX, innerDropY, innerDropX, innerDropY)
            close()
        }
        drawPath(path = innerDropPath, color = tealColor)

        // 5. Draw Bottom Hardware Strip Button Detail
        val btnW = meterW * 0.22f
        val btnH = btnW * 0.4f
        drawRoundRect(
            color = tealColor,
            topLeft = Offset(meterX + (meterW - btnW) / 2f, meterY + meterH * 0.56f),
            size = Size(btnW, btnH),
            cornerRadius = CornerRadius(btnW * 0.4f, btnW * 0.4f)
        )

        // 6. Draw Bottom Strip Attachment Element
        val stripW = meterW * 0.12f
        val stripH = meterH * 0.25f
        drawRoundRect(
            color = tealColor,
            topLeft = Offset(meterX + (meterW - stripW) / 2f, meterY + meterH - (w * 0.025f)),
            size = Size(stripW, stripH),
            cornerRadius = CornerRadius(stripW * 0.2f, stripW * 0.2f)
        )

        // 7. Draw Trend Line Data Graph (Teal Section)
        val strokeWidth = w * 0.022f
        val nodeRadius = w * 0.025f

        val p1 = Offset(w * 0.28f, h * 0.75f)
        val p2 = Offset(w * 0.42f, h * 0.68f)
        val p3 = Offset(w * 0.52f, h * 0.77f)
        val p4 = Offset(w * 0.65f, h * 0.72f)
        val p5 = Offset(w * 0.76f, h * 0.55f) // Transition point to yellow

        drawLine(color = tealColor, start = p1, end = p2, strokeWidth = strokeWidth, cap = StrokeCap.Round)
        drawLine(color = tealColor, start = p2, end = p3, strokeWidth = strokeWidth, cap = StrokeCap.Round)
        drawLine(color = tealColor, start = p3, end = p4, strokeWidth = strokeWidth, cap = StrokeCap.Round)
        drawLine(color = tealColor, start = p4, end = p5, strokeWidth = strokeWidth, cap = StrokeCap.Round)

        drawCircle(color = tealColor, radius = nodeRadius, center = p1)
        drawCircle(color = tealColor, radius = nodeRadius, center = p2)
        drawCircle(color = tealColor, radius = nodeRadius, center = p3)
        drawCircle(color = tealColor, radius = nodeRadius, center = p4)

        // 8. Draw Trend Line Extension & Arrow (Yellow Alert Section)
        val arrowTip = Offset(w * 0.81f, h * 0.46f)
        drawLine(color = yellowColor, start = p4, end = p5, strokeWidth = strokeWidth, cap = StrokeCap.Round)
        drawLine(color = yellowColor, start = p5, end = arrowTip, strokeWidth = strokeWidth, cap = StrokeCap.Round)
        drawCircle(color = yellowColor, radius = nodeRadius, center = p5)

        // Arrowhead drawing
        val arrowPath = Path().apply {
            moveTo(arrowTip.x, arrowTip.y)
            lineTo(arrowTip.x - w * 0.06f, arrowTip.y + w * 0.01f)
            lineTo(arrowTip.x - w * 0.01f, arrowTip.y + w * 0.06f)
            close()
        }
        drawPath(path = arrowPath, color = yellowColor)
    }
}

@Preview
@Composable
fun SplashScreenLightPreview() {
    MedBuddyTheme(themeMode = ThemeMode.LIGHT) {
        SplashScreen(onSplashFinished = {})
    }
}

@Preview
@Composable
fun SplashScreenDarkPreview() {
    MedBuddyTheme(themeMode = ThemeMode.DARK) {
        SplashScreen(onSplashFinished = {})
    }
}
