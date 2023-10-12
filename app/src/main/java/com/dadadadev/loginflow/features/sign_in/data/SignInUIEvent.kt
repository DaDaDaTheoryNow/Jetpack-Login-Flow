package com.dadadadev.loginflow.features.sign_in.data

sealed class SignInUIEvent {
    data class EmailChanged(val value: String) : SignInUIEvent()
    data class PasswordChanged(val value: String) : SignInUIEvent()

    object SignInButtonPressed : SignInUIEvent()
}