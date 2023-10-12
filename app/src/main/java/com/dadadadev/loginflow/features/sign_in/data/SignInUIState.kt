package com.dadadadev.loginflow.features.sign_in.data

data class SignInUIState(
    val email: String = "",
    val password: String = "",

    val emailError: Boolean = false,
    val emailSupportText: String = "",

    val passwordError: Boolean = false,
    val passwordSupportText: String = "",

    val signInLoading: Boolean = false,
    val signInError: String = ""
)