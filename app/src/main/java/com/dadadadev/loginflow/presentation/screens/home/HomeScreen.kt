package com.dadadadev.loginflow.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.presentation.screens.home.components.DeviceInfoComponent
import com.dadadadev.loginflow.presentation.screens.home.components.TopAppBarComponent
import com.dadadadev.loginflow.presentation.screens.home.components.UserInfoComponent
import com.dadadadev.loginflow.presentation.shared_components.DividerComponent
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val deviceInfoDataState by viewModel.deviceInfo
    val userInfoDataState by viewModel.userInfo

    // current device time
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault())
    val currentTimeFlow = (deviceInfoDataState as? DataState.Success)?.data?.currentTime
    val formattedTimeFlow = currentTimeFlow?.map { time ->
        sdf.format(time)
    } ?: flowOf("00:00:00")

    Scaffold(
        topBar = { TopAppBarComponent(onSignOutButtonPressed = { viewModel.userSignOut() }) },
        containerColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UserInfoComponent(userInfo = userInfoDataState)
            DividerComponent(
                value = formattedTimeFlow.collectAsStateWithLifecycle("00:00:00").value
            )
            DeviceInfoComponent(
                deviceInfo = deviceInfoDataState,
            )
        }
    }
}