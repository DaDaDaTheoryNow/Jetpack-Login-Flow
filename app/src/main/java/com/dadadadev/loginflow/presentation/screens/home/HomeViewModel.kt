package com.dadadadev.loginflow.presentation.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.home.DeviceInfo
import com.dadadadev.loginflow.data.model.home.UserInfo
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

    val userInfo: MutableState<DataState<UserInfo>> =
        mutableStateOf(DataState.Loading)

    init {
        getUserDeviceInfo()
        getUserInfo()
    }

    fun userSignOut() {
        authRepo.signOut()
    }

    private fun getUserInfo() {
        authRepo.getUserInformation(viewModelScope).onEach {
            userInfo.value = it
        }.launchIn(viewModelScope)
    }

    private fun getUserDeviceInfo() {
        deviceInfo.value = deviceInfoRepo.getUserDeviceInfo()
    }
}