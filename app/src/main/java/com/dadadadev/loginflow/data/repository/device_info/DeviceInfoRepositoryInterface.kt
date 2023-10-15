package com.dadadadev.loginflow.data.repository.device_info

import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.DeviceInfo
import kotlinx.coroutines.flow.Flow

interface DeviceInfoRepositoryInterface {
    fun getUserDeviceInfo(): Flow<DataState<DeviceInfo>>
}