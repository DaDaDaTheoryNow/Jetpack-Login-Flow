package com.dadadadev.loginflow.data.repository.device_info

import android.os.Build
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.DeviceInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeviceInfoRepository @Inject constructor() : DeviceInfoRepositoryInterface {
    override fun getUserDeviceInfo(): Flow<DataState<DeviceInfo>> = flow {
        emit(DataState.Loading)
        try {
            val deviceInfo = DeviceInfo(
                model = Build.MODEL,
                brand = Build.BRAND,
                manufacturer = Build.MANUFACTURER
            )
            emit(DataState.Success(deviceInfo))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }
}