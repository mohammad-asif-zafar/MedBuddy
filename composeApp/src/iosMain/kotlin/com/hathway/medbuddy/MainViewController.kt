package com.hathway.medbuddy

import androidx.compose.ui.window.ComposeUIViewController
import com.hathway.medbuddy.data.repository.GlucoseRepository
import com.hathway.medbuddy.data.repository.DoctorRepository

fun MainViewController() = ComposeUIViewController {
    App(
        repository = GlucoseRepository(),
        doctorRepository = DoctorRepository()
    )
}
