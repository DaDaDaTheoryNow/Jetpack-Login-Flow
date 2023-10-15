package com.dadadadev.loginflow.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.dadadadev.loginflow.presentation.screens.home.components.DeviceInfoComponent
import com.dadadadev.loginflow.presentation.screens.home.components.TopAppBarComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val deviceInfoDataState by viewModel.deviceInfo

    Scaffold(
        topBar = { TopAppBarComponent(onSignOutButtonPressed = { viewModel.userSignOut() }) },
        containerColor = Color.White,
    ) {
        Box(modifier = Modifier.padding(paddingValues = it)) {
            DeviceInfoComponent(
                deviceInfo = deviceInfoDataState,
            )
        }
    }
}