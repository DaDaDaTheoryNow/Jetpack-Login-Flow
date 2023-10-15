package com.dadadadev.loginflow.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepositoryInterface
) : ViewModel() {
    init {
        getAuthState()
    }

    fun getAuthState() = authRepo.getAuthState(viewModelScope)
}