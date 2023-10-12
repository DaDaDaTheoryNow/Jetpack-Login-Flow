package com.dadadadev.loginflow.common.rules.sign_validation

import android.content.Context
import com.dadadadev.loginflow.R

object TextFieldValidator {
    fun validateFirstName(context: Context, value: String): TextFieldValidationResult {
        return TextFieldValidationResult(
            isError = value.isNullOrEmpty(),
            supportText = context.getString(R.string.text_field_first_name_error)
        )
    }

    fun validateLastName(context: Context, value: String): TextFieldValidationResult {
        return TextFieldValidationResult(
            isError = value.isNullOrEmpty(),
            supportText = context.getString(R.string.text_field_last_name_error)
        )
    }

    fun validateEmail(context: Context, value: String): TextFieldValidationResult {
        return TextFieldValidationResult(
            isError = value.isNullOrEmpty() || !value.contains("@") || !value.contains("."),
            supportText = context.getString(R.string.text_field_email_error)
        )
    }

    fun validatePassword(context: Context, value: String): TextFieldValidationResult {
        return TextFieldValidationResult(
            isError = value.isNullOrEmpty() || value.length < 6,
            supportText = context.getString(R.string.text_field_password_error)
        )
    }
}

data class TextFieldValidationResult(
    val isError: Boolean = false,
    val supportText: String = ""
)