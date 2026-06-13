package com.hathway.medbuddy.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.profile.ProfileScreen
import com.hathway.medbuddy.profile.ProfileViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileContent() {
    val profileViewModel: ProfileViewModel = viewModel()

    ProfileScreen(profileViewModel)
}