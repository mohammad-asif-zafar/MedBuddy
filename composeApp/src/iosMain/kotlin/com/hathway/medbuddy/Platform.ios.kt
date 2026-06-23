package com.hathway.medbuddy

import platform.UIKit.UIDevice
import platform.Foundation.NSURL
import platform.UIKit.UIApplication


class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun openPlatformWebUrl(context: Any, url: String) {
    val nsUrl = NSURL.URLWithString(url)
    if (nsUrl != null) {
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}
