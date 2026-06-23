package com.hathway.medbuddy.icons

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object KmpComposeIcons {

    // Default icon color (Deep Teal to match your image)
    private val IconColor = Color(0xFF00796B)

    // 📦 1. Box / 3D Cube Icon
    val Box3D: ImageVector by lazy {
        ImageVector.Builder(
            name = "Box3D",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Isometric outer cube outline
            moveTo(12f, 2f)
            lineTo(21f, 7f)
            lineTo(21f, 17f)
            lineTo(12f, 22f)
            lineTo(3f, 17f)
            lineTo(3f, 7f)
            close()
            // Interior dividing lines
            moveTo(12f, 22f)
            lineTo(12f, 12f)
            moveTo(21f, 7f)
            lineTo(12f, 12f)
            lineTo(3f, 7f)
        }.build()
    }

    // 🛡️ 2. Shield Icon
    val Shield: ImageVector by lazy {
        ImageVector.Builder(
            name = "Shield",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Shield boundary curve
            moveTo(12f, 22f)
            curveTo(17.5f, 20.2f, 21f, 15.5f, 21f, 10f)
            lineTo(21f, 4f)
            lineTo(12f, 2f)
            lineTo(3f, 4f)
            lineTo(3f, 10f)
            curveTo(3f, 15.5f, 6.5f, 20.2f, 12f, 22f)
            close()
            // Inner checkmark detail
            moveTo(9f, 11f)
            lineTo(11f, 13f)
            lineTo(15f, 9f)
        }.build()
    }

    // 📖 3. Open Book Icon
    val OpenBook: ImageVector by lazy {
        ImageVector.Builder(
            name = "OpenBook",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Left page and right page book spine logic
            moveTo(2f, 3f)
            horizontalLineTo(11f)
            verticalLineTo(20f)
            horizontalLineTo(2f)
            close()
            moveTo(13f, 3f)
            horizontalLineTo(22f)
            verticalLineTo(20f)
            horizontalLineTo(13f)
            close()
        }.build()
    }

    // 🎧 1. Headset / Support Icon
    val Headset: ImageVector by lazy {
        ImageVector.Builder(
            name = "Headset",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Main headband arc
            moveTo(3f, 11f)
            curveTo(3f, 6.03f, 7.03f, 2f, 12f, 2f)
            curveTo(16.97f, 2f, 21f, 6.03f, 21f, 11f)
            // Left ear cup
            moveTo(3f, 11f)
            horizontalLineTo(5f)
            verticalLineTo(16f)
            horizontalLineTo(3f)
            close()
            // Right ear cup
            moveTo(19f, 11f)
            horizontalLineTo(21f)
            verticalLineTo(16f)
            horizontalLineTo(19f)
            close()
            // Microphone arm extension
            moveTo(19f, 16f)
            curveTo(19f, 18.5f, 16.5f, 20f, 14f, 20f)
            horizontalLineTo(13f)
        }.build()
    }

    // 💬 2. Chat Bubble Icon
    val ChatBubble: ImageVector by lazy {
        ImageVector.Builder(
            name = "ChatBubble",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Rounded speech bubble outline
            moveTo(12f, 3f)
            curveTo(6.48f, 3f, 2f, 6.81f, 2f, 11.5f)
            curveTo(2f, 14.12f, 3.42f, 16.44f, 5.66f, 18f)
            lineTo(4.5f, 21f)
            lineTo(8.5f, 19.5f)
            curveTo(9.61f, 19.83f, 10.79f, 20f, 12f, 20f)
            curveTo(17.52f, 20f, 22f, 16.19f, 22f, 11.5f)
            curveTo(22f, 6.81f, 17.52f, 3f, 12f, 3f)
            close()
        }.build()
    }

    // ⭐ 3. Star / Favorites Icon
    val Star: ImageVector by lazy {
        ImageVector.Builder(
            name = "Star",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // 5-point symmetric star coordinates
            moveTo(12f, 2f)
            lineTo(15.09f, 8.26f)
            lineTo(22f, 9.27f)
            lineTo(17f, 14.14f)
            lineTo(18.18f, 21.02f)
            lineTo(12f, 17.77f)
            lineTo(5.82f, 21.02f)
            lineTo(7f, 14.14f)
            lineTo(2f, 9.27f)
            lineTo(8.91f, 8.26f)
            close()
        }.build()
    }

    // </> 1. Code Tags Icon
    val CodeTags: ImageVector by lazy {
        ImageVector.Builder(
            name = "CodeTags",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Left bracket <
            moveTo(6f, 8f)
            lineTo(2f, 12f)
            lineTo(6f, 16f)

            // Central forward slash /
            moveTo(14f, 4f)
            lineTo(10f, 20f)

            // Right bracket >
            moveTo(18f, 8f)
            lineTo(22f, 12f)
            lineTo(18f, 16f)
        }.build()
    }

    // 🖌️ 2. Paintbrush / Design Icon
    val Paintbrush: ImageVector by lazy {
        ImageVector.Builder(
            name = "Paintbrush",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Upper brush tip outline angled at 45 degrees
            moveTo(18.5f, 2.5f)
            curveTo(20f, 4f, 20f, 6.5f, 18.5f, 8f)
            lineTo(11f, 15.5f)
            lineTo(5.5f, 13f)
            lineTo(13f, 5.5f)
            close()

            // Lower handle stem base
            moveTo(5.5f, 13f)
            lineTo(2.5f, 18.5f)
            curveTo(2f, 19.5f, 2.5f, 21.5f, 4f, 22f)
            curveTo(4.5f, 22f, 5.5f, 21.5f, 6.5f, 19.5f)
            lineTo(11f, 15.5f)
        }.build()
    }

    // 🛢️ 3. Database Stack Icon
    val Database: ImageVector by lazy {
        ImageVector.Builder(
            name = "Database",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // Top cylinder plate
            moveTo(12f, 3f)
            curveTo(17.52f, 3f, 22f, 5.01f, 22f, 7.5f)
            curveTo(22f, 9.99f, 17.52f, 12f, 12f, 12f)
            curveTo(6.48f, 12f, 2f, 9.99f, 2f, 7.5f)
            curveTo(2f, 5.01f, 6.48f, 3f, 12f, 3f)
            close()

            // Middle cylinder segment curves
            moveTo(22f, 7.5f)
            verticalLineTo(12.5f)
            curveTo(22f, 14.99f, 17.52f, 17f, 12f, 17f)
            curveTo(6.48f, 17f, 2f, 14.99f, 2f, 12.5f)
            verticalLineTo(7.5f)

            // Bottom cylinder segment curves
            moveTo(22f, 12.5f)
            verticalLineTo(17.5f)
            curveTo(22f, 19.99f, 17.52f, 22f, 12f, 22f)
            curveTo(6.48f, 22f, 2f, 19.99f, 2f, 17.5f)
            verticalLineTo(12.5f)
        }.build()
    }

    // 🔔 1. Notification Bell Icon
    val NotificationBell: ImageVector by lazy {
        ImageVector.Builder(
            name = "NotificationBell",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18f, 8f)
            curveTo(18f, 4.69f, 15.31f, 2f, 12f, 2f)
            curveTo(8.69f, 2f, 6f, 4.69f, 6f, 8f)
            curveTo(6f, 14f, 3f, 16f, 3f, 16f)
            horizontalLineTo(21f)
            curveTo(21f, 16f, 18f, 14f, 18f, 8f)
            close()

            moveTo(10.3f, 20f)
            curveTo(10.62f, 21.16f, 11.21f, 22f, 12f, 22f)
            curveTo(12.79f, 22f, 13.38f, 21.16f, 13.7f, 20f)
            close()
        }.build()
    }

    // 📅 2. Calendar Icon
    val Calendar: ImageVector by lazy {
        ImageVector.Builder(
            name = "Calendar",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(19f, 4f)
            horizontalLineTo(5f)
            curveTo(3.89f, 4f, 3f, 4.89f, 3f, 6f)
            verticalLineTo(20f)
            curveTo(3f, 21.11f, 3.89f, 22f, 5f, 22f)
            horizontalLineTo(19f)
            curveTo(20.11f, 22f, 21f, 21.11f, 21f, 20f)
            verticalLineTo(6f)
            curveTo(21f, 4.89f, 20.11f, 4f, 19f, 4f)
            close()

            moveTo(3f, 9f)
            horizontalLineTo(21f)

            // Grid dots representation
            moveTo(7f, 13f)
            horizontalLineTo(8f)
            moveTo(12f, 13f)
            horizontalLineTo(13f)
            moveTo(16f, 13f)
            horizontalLineTo(17f)
            moveTo(7f, 17f)
            horizontalLineTo(8f)
            moveTo(12f, 17f)
            horizontalLineTo(13f)
        }.build()
    }

    // 📷 3. Camera Icon
    val Camera: ImageVector by lazy {
        ImageVector.Builder(
            name = "Camera",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 18f)
            curveTo(14.76f, 18f, 17f, 15.76f, 17f, 13f)
            curveTo(17f, 10.24f, 14.76f, 8f, 12f, 8f)
            curveTo(9.24f, 8f, 7f, 10.24f, 7f, 13f)
            curveTo(7f, 15.76f, 9.24f, 18f, 12f, 18f)
            close()

            moveTo(9f, 2f)
            lineTo(7.5f, 4f)
            horizontalLineTo(4f)
            curveTo(2.9f, 4f, 2f, 4.9f, 2f, 6f)
            verticalLineTo(20f)
            curveTo(2f, 21.1f, 2.9f, 22f, 4f, 22f)
            horizontalLineTo(20f)
            curveTo(21.1f, 22f, 22f, 21.1f, 22f, 20f)
            verticalLineTo(6f)
            curveTo(22f, 4.9f, 21.1f, 4f, 20f, 4f)
            horizontalLineTo(16.5f)
            lineTo(15f, 2f)
            close()
        }.build()
    }

    // 📄 4. Spreadsheet Document Icon
    val Spreadsheet: ImageVector by lazy {
        ImageVector.Builder(
            name = "Spreadsheet",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(14f, 2f)
            lineTo(20f, 8f)
            verticalLineTo(20f)
            curveTo(20f, 21.1f, 19.1f, 22f, 18f, 22f)
            horizontalLineTo(6f)
            curveTo(4.9f, 22f, 4f, 21.1f, 4f, 20f)
            verticalLineTo(4f)
            curveTo(4f, 2.9f, 4.9f, 2f, 6f, 2f)
            close()

            // Inner spreadsheet grids
            moveTo(8f, 12f)
            horizontalLineTo(16f)
            moveTo(8f, 16f)
            horizontalLineTo(16f)
        }.build()
    }

    // 👤 5. User Profile Icon
    val UserProfile: ImageVector by lazy {
        ImageVector.Builder(
            name = "UserProfile",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 12f)
            curveTo(14.21f, 12f, 16f, 10.21f, 16f, 8f)
            curveTo(16f, 5.79f, 14.21f, 4f, 12f, 4f)
            curveTo(9.79f, 4f, 8f, 5.79f, 8f, 8f)
            curveTo(8f, 10.21f, 9.79f, 12f, 12f, 12f)
            close()

            moveTo(4f, 20f)
            curveTo(4f, 16.69f, 7.58f, 14f, 12f, 14f)
            curveTo(16.42f, 14f, 20f, 16.69f, 20f, 20f)
        }.build()
    }

    // 📍 6. Location Pin Icon
    val LocationPin: ImageVector by lazy {
        ImageVector.Builder(
            name = "LocationPin",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 2f)
            curveTo(7.58f, 2f, 4f, 5.58f, 4f, 10f)
            curveTo(4f, 16f, 12f, 22f, 12f, 22f)
            curveTo(12f, 22f, 20f, 16f, 20f, 10f)
            curveTo(20f, 5.58f, 16.42f, 2f, 12f, 2f)
            close()

            moveTo(12f, 12f)
            curveTo(10.9f, 12f, 10f, 11.1f, 10f, 10f)
            curveTo(10f, 8.9f, 10.9f, 8f, 12f, 8f)
            curveTo(13.1f, 8f, 14f, 8.9f, 14f, 10f)
            curveTo(14f, 11.1f, 13.1f, 12f, 12f, 12f)
            close()
        }.build()
    }

    // 🛡️ 7. Verified Shield Solid Icon
    val VerifiedShield: ImageVector by lazy {
        ImageVector.Builder(
            name = "VerifiedShield",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            fill = SolidColor(IconColor), // Filled completely with solid color
        ) {
            moveTo(12f, 22f)
            curveTo(17.5f, 20.2f, 21f, 15.5f, 21f, 10f)
            lineTo(21f, 4f)
            lineTo(12f, 2f)
            lineTo(3f, 4f)
            lineTo(3f, 10f)
            curveTo(3f, 15.5f, 6.5f, 20.2f, 12f, 22f)
            close()

            // Cut-out white space checkmark path
            moveTo(10f, 15.5f)
            lineTo(6f, 11.5f)
            lineTo(7.4f, 10.1f)
            lineTo(10f, 12.7f)
            lineTo(16.6f, 6f)
            lineTo(18f, 7.4f)
            close()
        }.build()
    }

    // 🔒 Secure Padlock Icon
    val Padlock: ImageVector by lazy {
        ImageVector.Builder(
            name = "Padlock",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).path(
            stroke = SolidColor(IconColor),
            strokeLineWidth = 2f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            // 1. Lock Shackle (Top curved arc)
            moveTo(7f, 10f)
            verticalLineTo(7f)
            curveTo(7f, 4.24f, 9.24f, 2f, 12f, 2f)
            curveTo(14.76f, 2f, 17f, 4.24f, 17f, 7f)
            verticalLineTo(10f)

            // 2. Lock Body (Main body outline)
            moveTo(5f, 10f)
            horizontalLineTo(19f)
            curveTo(20.1f, 10f, 21f, 10.9f, 21f, 12f)
            verticalLineTo(20f)
            curveTo(21f, 21.1f, 20.1f, 22f, 19f, 22f)
            horizontalLineTo(5f)
            curveTo(3.9f, 22f, 3f, 21.1f, 3f, 20f)
            verticalLineTo(12f)
            curveTo(3f, 10.9f, 3.9f, 10f, 5f, 10f)
            close()

            // 3. Central Keyhole detail
            moveTo(12f, 14f)
            curveTo(11.17f, 14f, 10.5f, 14.67f, 10.5f, 15.5f)
            curveTo(10.5f, 16.05f, 10.8f, 16.54f, 11.25f, 16.79f)
            lineTo(11f, 19f)
            horizontalLineTo(13f)
            lineTo(12.75f, 16.79f)
            curveTo(13.2f, 16.54f, 13.5f, 16.05f, 13.5f, 15.5f)
            curveTo(13.5f, 14.67f, 12.83f, 14f, 12f, 14f)
            close()
        }.build()
    }

    // Official Firebase Color Palette
    private val FirebaseAmber = Color(0xFFFFCA28)
    private val FirebaseOrange = Color(0xFFF57C00)
    private val FirebaseDeepOrange = Color(0xFFE65100)

    // 🔥 Firebase Logo Icon
    val Firebase: ImageVector by lazy {
        ImageVector.Builder(
            name = "Firebase",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // Layer 1: Left Dark Orange Background Wedge
            path(fill = SolidColor(FirebaseDeepOrange)) {
                moveTo(3.91f, 18.06f)
                lineTo(10.15f, 2.53f)
                curveTo(10.3f, 2.15f, 10.84f, 2.15f, 11f, 2.53f)
                lineTo(12.75f, 6.94f)
                lineTo(3.91f, 18.06f)
                close()
            }
            // Layer 2: Right Main Orange Geometric Slice
            path(fill = SolidColor(FirebaseOrange)) {
                moveTo(20.09f, 18.06f)
                lineTo(15.75f, 9.43f)
                lineTo(12.75f, 6.94f)
                lineTo(3.91f, 18.06f)
                lineTo(11.23f, 22.17f)
                curveTo(11.71f, 22.44f, 12.29f, 22.44f, 12.77f, 22.17f)
                lineTo(20.09f, 18.06f)
                close()
            }
            // Layer 3: Top/Front Bright Amber Layer
            path(fill = SolidColor(FirebaseAmber)) {
                moveTo(3.91f, 18.06f)
                lineTo(7.13f, 10.11f)
                curveTo(7.28f, 9.73f, 7.82f, 9.73f, 7.97f, 10.11f)
                lineTo(12.75f, 6.94f)
                lineTo(3.91f, 18.06f)
                close()
            }
        }.build()
    }

    // Color Palette matching the icon style
    private val DeepTeal = Color(0xFF00796B)   // Stroke outline and main color
    private val LightTeal = Color(0xFF009688)  // Shaded right half tint

    val ShadedPadlock: ImageVector by lazy {
        ImageVector.Builder(
            name = "ShadedPadlock",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // LAYER 1: The shaded right half background fill
            path(fill = SolidColor(LightTeal)) {
                // Starts at top center (x=12), outlines the right half, ends at bottom center (x=12)
                moveTo(12f, 2f)
                curveTo(14.76f, 2f, 17f, 4.24f, 17f, 7f)
                verticalLineTo(10f)
                horizontalLineTo(19f)
                curveTo(20.1f, 10f, 21f, 10.9f, 21f, 12f)
                verticalLineTo(20f)
                curveTo(21f, 21.1f, 20.1f, 22f, 19f, 22f)
                horizontalLineTo(12f)
                verticalLineTo(2f) // Cut straight down the center line
                close()
            }

            // LAYER 2: Main outer stroke outline and structural shapes
            path(
                stroke = SolidColor(DeepTeal),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // 1. Lock Shackle (Top curved arc)
                moveTo(7f, 10f)
                verticalLineTo(7f)
                curveTo(7f, 4.24f, 9.24f, 2f, 12f, 2f)
                curveTo(14.76f, 2f, 17f, 4.24f, 17f, 7f)
                verticalLineTo(10f)

                // 2. Lock Body (Main body outline)
                moveTo(5f, 10f)
                horizontalLineTo(19f)
                curveTo(20.1f, 10f, 21f, 10.9f, 21f, 12f)
                verticalLineTo(20f)
                curveTo(21f, 21.1f, 20.1f, 22f, 19f, 22f)
                horizontalLineTo(5f)
                curveTo(3.9f, 22f, 3f, 21.1f, 3f, 20f)
                verticalLineTo(12f)
                curveTo(3f, 10.9f, 3.9f, 10f, 5f, 10f)
                close()

                // 3. Central Keyhole detail
                moveTo(12f, 14f)
                curveTo(11.17f, 14f, 10.5f, 14.67f, 10.5f, 15.5f)
                curveTo(10.5f, 16.05f, 10.8f, 16.54f, 11.25f, 16.79f)
                lineTo(11f, 19f)
                horizontalLineTo(13f)
                lineTo(12.75f, 16.79f)
                curveTo(13.2f, 16.54f, 13.5f, 16.05f, 13.5f, 15.5f)
                curveTo(13.5f, 14.67f, 12.83f, 14f, 12f, 14f)
                close()
            }
        }.build()
    }

    val ShadedShield: ImageVector by lazy {
        ImageVector.Builder(
            name = "ShadedShield",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            // LAYER 1: Full shield base in Deep Teal
            path(fill = SolidColor(DeepTeal)) {
                moveTo(12f, 22f)
                curveTo(17.5f, 20.2f, 21f, 15.5f, 21f, 10f)
                lineTo(21f, 4f)
                lineTo(12f, 2f)
                lineTo(3f, 4f)
                lineTo(3f, 10f)
                curveTo(3f, 15.5f, 6.5f, 20.2f, 12f, 22f)
                close()
            }

            // LAYER 2: Overlay shading for the right half (x = 12f to 21f)
            path(fill = SolidColor(LightTeal)) {
                moveTo(12f, 22f)
                curveTo(17.5f, 20.2f, 21f, 15.5f, 21f, 10f)
                lineTo(21f, 4f)
                lineTo(12f, 2f)
                verticalLineTo(22f) // Cuts straight down the central axis
                close()
            }

            // LAYER 3: White Checkmark cut-out layered on top
            path(fill = SolidColor(Color.White)) {
                moveTo(10f, 15.5f)
                lineTo(6f, 11.5f)
                lineTo(7.4f, 10.1f)
                lineTo(10f, 12.7f)
                lineTo(16.6f, 6f)
                lineTo(18f, 7.4f)
                close()
            }
        }.build()
    }
}
