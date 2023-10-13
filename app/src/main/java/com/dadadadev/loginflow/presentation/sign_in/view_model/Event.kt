package com.dadadadev.loginflow.presentation.sign_in.view_model

sealed class SignInUIEvent {
    data class EmailChanged(val value: String) : SignInUIEvent()
    data class PasswordChanged(val value: String) : SignInUIEvent()

    object SignInButtonPressed : SignInUIEvent()
}