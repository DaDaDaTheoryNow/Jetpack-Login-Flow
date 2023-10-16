package com.dadadadev.loginflow.data.repository.validate_fields

import android.app.Application
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.data.model.sign.FieldState
import com.dadadadev.loginflow.data.model.sign.PrivacyPolicyCheckBoxState
import javax.inject.Inject

class ValidateFields @Inject constructor(
    private val appContext: Application
) : ValidateFieldsInterface {

    override fun validateFirstName(value: String): FieldState {
        val isError = value.isEmpty()
        return FieldState(
            value = value,
            errorMessage = if (isError) appContext.getString(R.string.text_field_first_name_error) else ""
        )
    }

    override fun validateLastName(value: String): FieldState {
        val isError = value.isEmpty()
        return FieldState(
            value = value,
            errorMessage = if (isError) appContext.getString(R.string.text_field_last_name_error) else ""
        )
    }

    override fun validateEmail(value: String): FieldState {
        val isError = value.isEmpty() || !value.contains("@") || !value.contains(".")
        return FieldState(
            value = value,
            errorMessage = if (isError) appContext.getString(R.string.text_field_email_error) else ""
        )
    }

    override fun validatePassword(value: String): FieldState {
        val isError = value.isEmpty() || value.length < 6
        return FieldState(
            value = value,
            errorMessage = if (isError) appContext.getString(R.string.text_field_password_error) else ""
        )
    }

    override fun validatePrivacyPolicyCheckBox(value: Boolean): PrivacyPolicyCheckBoxState {
        return PrivacyPolicyCheckBoxState(value = value, isError = !value)
    }
}