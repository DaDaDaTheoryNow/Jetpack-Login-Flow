package com.dadadadev.loginflow.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dadadadev.loginflow.presentation.home.components.DeviceInfoComponent
import com.dadadadev.loginflow.presentation.home.components.TopAppBarComponent
import com.dadadadev.loginflow.presentation.home.view_model.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBarComponent(onSignOutButtonPressed = { viewModel.userSignOut() }) },
        containerColor = Color.White,
    ) {
        Box(modifier = Modifier.padding(paddingValues = it)) {
            DeviceInfoComponent(
                deviceInfo = viewState.deviceInfo,
                error = viewState.error
            )
        }
    }
}