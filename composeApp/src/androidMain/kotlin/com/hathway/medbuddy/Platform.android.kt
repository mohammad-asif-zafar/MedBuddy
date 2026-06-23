package com.hathway.medbuddy

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.net.toUri

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun openPlatformWebUrl(context: Any, url: String) {
    val androidContext = context as? Context ?: return
    val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
        // If it's an Activity context, you don't strictly need NEW_TASK, but keeping it is safe
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    androidContext.startActivity(intent)
}