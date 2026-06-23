package com.hathway.medbuddy

import android.app.Application
import android.content.Context

class MedBuddyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}
