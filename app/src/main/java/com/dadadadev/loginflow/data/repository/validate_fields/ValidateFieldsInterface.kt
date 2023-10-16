package com.dadadadev.loginflow.data.repository.validate_fields

import com.dadadadev.loginflow.data.model.sign.FieldState
import com.dadadadev.loginflow.data.model.sign.PrivacyPolicyCheckBoxState

interface ValidateFieldsInterface {
    fun validateFirstName(value: String): FieldState
    fun validateLastName(value: String): FieldState
    fun validateEmail(value: String): FieldState
    fun validatePassword(value: String): FieldState

    fun validatePrivacyPolicyCheckBox(value: Boolean): PrivacyPolicyCheckBoxState
}