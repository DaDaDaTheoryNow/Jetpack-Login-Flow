package com.dadadadev.loginflow.domain.repository

import com.dadadadev.loginflow.core.Response
import com.dadadadev.loginflow.domain.model.DeviceInfo

typealias UserDeviceInfoResponse = Response<DeviceInfo>

interface DeviceInfoRepository {
    fun getUserDeviceInfo(): UserDeviceInfoResponse
}