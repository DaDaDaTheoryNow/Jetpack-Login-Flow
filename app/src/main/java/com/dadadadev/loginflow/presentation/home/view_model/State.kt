package com.dadadadev.loginflow.presentation.home.view_model

import com.dadadadev.loginflow.domain.model.DeviceInfo

data class HomeUIState(
    val deviceInfo: DeviceInfo = DeviceInfo(),
    val error: String = ""
)