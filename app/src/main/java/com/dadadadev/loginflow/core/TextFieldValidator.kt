package com.dadadadev.loginflow.core

import android.app.Application
import com.dadadadev.loginflow.R
import javax.inject.Inject

class TextFieldValidator @Inject constructor(
    private val appContext: Application
) {
    fun validateFirstName(value: String): TextFieldValidationResult {
        return TextFieldValidationResult(
            isError = value.isEmpty(),
            supportText = appContext.getString(R.string.text_field_first_name_error)
        )
    }

    fun validateLastName(value: String): TextFieldValidationResult {
        return TextFieldValidationResult(
            isError = value.isEmpty(),
            supportText = appContext.getString(R.string.text_field_last_name_error)
        )
    }

    fun validateEmail(value: String): TextFieldValidationResult {
        return TextFieldValidationResult(
            isError = value.isEmpty() || !value.contains("@") || !value.contains("."),
            supportText = appContext.getString(R.string.text_field_email_error)
        )
    }

    fun validatePassword(value: String): TextFieldValidationResult {
        return TextFieldValidationResult(
            isError = value.isEmpty() || value.length < 6,
            supportText = appContext.getString(R.string.text_field_password_error)
        )
    }
}

data class TextFieldValidationResult(
    val isError: Boolean = false,
    val supportText: String = ""
)
