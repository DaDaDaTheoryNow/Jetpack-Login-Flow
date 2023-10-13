package com.dadadadev.loginflow.presentation.sign_up.view_model

sealed class SignUpUIEvent {
    data class FirstNameChanged(val value: String) : SignUpUIEvent()
    data class LastNameChanged(val value: String) : SignUpUIEvent()
    data class EmailChanged(val value: String) : SignUpUIEvent()
    data class PasswordChanged(val value: String) : SignUpUIEvent()
    data class PrivacyPolicyCheckBoxPressed(val value: Boolean) : SignUpUIEvent()

    object SignUpButtonPressed : SignUpUIEvent()
}