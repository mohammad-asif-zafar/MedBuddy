package com.hathway.medbuddy.home.presentation_layer.ui_compose_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hathway.medbuddy.home.presentation_layer.vm.MainViewModel


/*MainScreen(
viewModel = MainViewModel()
)*/
@Composable
fun MainScreen(
    viewModel: MainViewModel
) {

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = state.input,
            onValueChange = {
                viewModel.onInputChange(it)
            },
            label = {
                Text("Enter Name")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.onButtonClick()
            }
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = state.result
        )
    }
}