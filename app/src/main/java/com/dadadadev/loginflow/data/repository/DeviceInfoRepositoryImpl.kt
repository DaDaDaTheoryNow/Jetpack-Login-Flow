package com.dadadadev.loginflow.data.repository

import android.os.Build
import com.dadadadev.loginflow.core.Response
import com.dadadadev.loginflow.domain.model.DeviceInfo
import com.dadadadev.loginflow.domain.repository.DeviceInfoRepository
import com.dadadadev.loginflow.domain.repository.UserDeviceInfoResponse
import javax.inject.Inject

class DeviceInfoRepositoryImpl @Inject constructor() : DeviceInfoRepository {
    override fun getUserDeviceInfo(): UserDeviceInfoResponse =
        try {
            val deviceInfo = DeviceInfo(
                model = Build.MODEL,
                brand = Build.BRAND,
                manufacturer = Build.MANUFACTURER
            )
            Response.Success(deviceInfo)
        } catch (e: Exception) {
            Response.Failure(e)
        }
}