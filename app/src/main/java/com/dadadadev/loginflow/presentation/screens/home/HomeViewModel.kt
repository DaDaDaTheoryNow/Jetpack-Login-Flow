package com.dadadadev.loginflow.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.DeviceInfo
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import com.dadadadev.loginflow.data.repository.device_info.DeviceInfoRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: AuthRepositoryInterface,
    private val deviceInfoRepo: DeviceInfoRepositoryInterface
) : ViewModel() {
    val deviceInfo: MutableState<DataState<DeviceInfo>> =
        mutableStateOf(DataState.Loading)

    init {
        getUserDeviceInfo()
    }

    fun userSignOut() {
        authRepo.signOut()
    }

    private fun getUserDeviceInfo() {
        deviceInfoRepo.getUserDeviceInfo().onEach {
            deviceInfo.value = it
        }.launchIn(viewModelScope)
    }
}