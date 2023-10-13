package com.dadadadev.loginflow.presentation.home.view_model

import androidx.lifecycle.ViewModel
import com.dadadadev.loginflow.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
    fun userSignOut() {
        authRepo.signOut()
    }
}