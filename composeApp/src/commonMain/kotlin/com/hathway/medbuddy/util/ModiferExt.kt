package com.hathway.medbuddy.util

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager

/**
 * Extension modifier that clears input focus and drops the keyboard
 * when the user taps on an empty area of the screen layout.
 */
@Composable
fun Modifier.clearFocusOnTapOutside(): Modifier {
    val focusManager = LocalFocusManager.current
    return this.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                focusManager.clearFocus() // ⌨️ Drops target selection, closing the soft keyboard safely
            })
    }
}
