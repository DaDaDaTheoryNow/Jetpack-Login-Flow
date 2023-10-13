package com.dadadadev.loginflow.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
    init {
        getAuthState()
    }

    fun getAuthState() = authRepo.getAuthState(viewModelScope)
}