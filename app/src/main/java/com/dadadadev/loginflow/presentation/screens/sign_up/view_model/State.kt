package com.dadadadev.loginflow.presentation.screens.sign_up.view_model

data class SignUpUIState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",

    val firstNameError: Boolean = false,
    val firstNameSupportText: String = "",

    val lastNameError: Boolean = false,
    val lastNameSupportText: String = "",

    val emailError: Boolean = false,
    val emailSupportText: String = "",

    val passwordError: Boolean = false,
    val passwordSupportText: String = "",

    val privacyPolicyCheckBox: Boolean = false,
    val privacyPolicyCheckBoxError: Boolean = false,

    val signUpLoading: Boolean = false,
    val signUpError: String = ""
)