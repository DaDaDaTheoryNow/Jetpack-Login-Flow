package com.dadadadev.loginflow.data.repository.device_info

import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.home.DeviceInfo

interface DeviceInfoRepositoryInterface {
    fun getUserDeviceInfo(): DataState<DeviceInfo>
}