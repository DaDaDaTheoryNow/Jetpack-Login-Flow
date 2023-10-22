package com.dadadadev.loginflow.data.model.home

import kotlinx.coroutines.flow.Flow
import java.util.Date

data class DeviceInfo(
    val model: String = "Unknown",
    val brand: String = "Unknown",
    val manufacturer: String = "Unknown",

    val currentTime: Flow<Date>
)
