package com.dadadadev.loginflow.presentation.home.view_model

import androidx.lifecycle.ViewModel
import com.dadadadev.loginflow.core.Response
import com.dadadadev.loginflow.domain.repository.AuthRepository
import com.dadadadev.loginflow.domain.repository.DeviceInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val deviceInfoRepo: DeviceInfoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    init {
        getUserDeviceInfo()
    }

    fun userSignOut() {
        authRepo.signOut()
    }

    private fun getUserDeviceInfo() {
        val deviceInfoResponse = deviceInfoRepo.getUserDeviceInfo()

        if (deviceInfoResponse is Response.Success) {
            _uiState.update {
                it.copy(
                    deviceInfo = deviceInfoResponse.data
                )
            }
        }

        if (deviceInfoResponse is Response.Failure) {
            _uiState.update {
                it.copy(
                    error = deviceInfoResponse.e.toString(),
                )
            }
        }
    }
}