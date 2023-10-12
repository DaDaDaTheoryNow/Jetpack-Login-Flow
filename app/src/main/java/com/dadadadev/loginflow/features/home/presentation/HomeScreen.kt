package com.dadadadev.loginflow.features.home.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.dadadadev.loginflow.features.home.data.HomeUIEvent
import com.dadadadev.loginflow.features.home.data.HomeViewModel
import com.dadadadev.loginflow.features.home.presentation.components.TopAppBarComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Scaffold(
        topBar = { TopAppBarComponent(onSignOutButtonPressed = { viewModel.onEvent(HomeUIEvent.signOutButtonPressed) }) },
        containerColor = Color.White,
    ) {

    }
}