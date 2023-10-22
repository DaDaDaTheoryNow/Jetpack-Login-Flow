package com.dadadadev.loginflow.data.model.sign

data class UserCredentials(
    val firstName: String = "",
    val lastName: String = "",
    val email: String,
    val password: String,
)