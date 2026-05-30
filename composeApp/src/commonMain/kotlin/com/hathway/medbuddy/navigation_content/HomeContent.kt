package com.hathway.medbuddy.navigation_content

import androidx.compose.runtime.Composable
import com.hathway.medbuddy.home.presentation_layer.ui_compose_screen.MainScreen
import com.hathway.medbuddy.home.presentation_layer.vm.MainViewModel
import com.hathway.medbuddy.screens.HomeScreen

@Composable
fun HomeContent() {
    //HomeScreen()
    MainScreen(
        viewModel = MainViewModel()
    )
}