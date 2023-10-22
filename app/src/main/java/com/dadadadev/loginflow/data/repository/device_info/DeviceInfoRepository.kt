package com.dadadadev.loginflow.data.repository.device_info

import android.os.Build
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.home.DeviceInfo
import com.dadadadev.loginflow.data.service.time.TimeServiceInterface
import javax.inject.Inject

class DeviceInfoRepository @Inject constructor(
    private val timeService: TimeServiceInterface
) : DeviceInfoRepositoryInterface {
    override fun getUserDeviceInfo(): DataState<DeviceInfo> {
        return try {
            val deviceInfo = DeviceInfo(
                model = Build.MODEL,
                brand = Build.BRAND,
                manufacturer = Build.MANUFACTURER,
                currentTime = timeService.getCurrentTime()
            )
            DataState.Success(deviceInfo)
        } catch (e: Exception) {
            DataState.Failure(e)
        }
    }
}