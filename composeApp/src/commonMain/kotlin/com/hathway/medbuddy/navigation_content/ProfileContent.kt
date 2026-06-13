package com.hathway.medbuddy.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.presentation.ui.ProfileScreen
import com.hathway.medbuddy.presentation.viewmodel.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileContent() {
    val profileViewModel: ProfileViewModel = viewModel()

    ProfileScreen(profileViewModel)
}