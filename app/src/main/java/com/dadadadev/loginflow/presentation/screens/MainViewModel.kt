package com.dadadadev.loginflow.presentation.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepositoryInterface
) : ViewModel() {
    val isUserSignOut = mutableStateOf(false)

    init {
        getAuthState()
    }

    private fun getAuthState() {
        authRepo.getAuthState(viewModelScope).onEach {
            isUserSignOut.value = it
        }.launchIn(viewModelScope)
    }
}