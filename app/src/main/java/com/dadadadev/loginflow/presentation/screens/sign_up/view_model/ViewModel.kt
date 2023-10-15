package com.dadadadev.loginflow.presentation.screens.sign_up.view_model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.core.Response
import com.dadadadev.loginflow.core.TextFieldValidator
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import com.dadadadev.loginflow.data.repository.auth.SignUpResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthRepositoryInterface,
    private val textFieldValidator: TextFieldValidator,
    private val appContext: Application
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()


    private var ableToSignUp = _uiState.map { currentState ->
        !currentState.firstNameError
                && !currentState.lastNameError
                && !currentState.emailError
                && !currentState.passwordError && currentState.privacyPolicyCheckBox
    }

    fun onFirstNameChanged(firstName: String) {
        val validate = textFieldValidator.validateFirstName(firstName)
        _uiState.update {
            it.copy(
                firstName = firstName,
                firstNameError = validate.isError,
                firstNameSupportText = validate.supportText
            )
        }
        validatePrivacyPolicyCheckBox()
    }

    fun onLastNameChanged(lastName: String) {
        val validate = textFieldValidator.validateLastName(lastName)
        _uiState.update {
            it.copy(
                lastName = lastName,
                lastNameError = validate.isError,
                lastNameSupportText = validate.supportText
            )
        }

        validatePrivacyPolicyCheckBox()
    }

    fun onEmailChanged(email: String) {
        val validate = textFieldValidator.validateEmail(email)
        _uiState.update {
            it.copy(
                email = email,
                emailError = validate.isError,
                emailSupportText = validate.supportText
            )
        }

        validatePrivacyPolicyCheckBox()
    }

    fun onPasswordChanged(password: String) {
        val validate = textFieldValidator.validatePassword(password)
        _uiState.update {
            it.copy(
                password = password,
                passwordError = validate.isError,
                passwordSupportText = validate.supportText
            )
        }

        validatePrivacyPolicyCheckBox()
    }

    fun onPrivacyPolicyCheckBoxPressed(value: Boolean) {
        _uiState.update { it.copy(privacyPolicyCheckBox = value) }
        validatePrivacyPolicyCheckBox()
    }

    private fun validatePrivacyPolicyCheckBox() {
        val privacyPolicyCheckBoxError = !_uiState.value.privacyPolicyCheckBox
        _uiState.update { it.copy(privacyPolicyCheckBoxError = privacyPolicyCheckBoxError) }
    }

    fun userSignUp() {
        _uiState.update { it.copy(signUpError = "") }
        revalidateFieldsIfNeeded()

        viewModelScope.launch {
            val ableToSignUpValue: Boolean = ableToSignUp.first()
            if (ableToSignUpValue) {
                _uiState.update { it.copy(signUpLoading = true) }

                val signUpResponse: SignUpResponse =
                    authRepo.signUp(_uiState.value.email, _uiState.value.password)

                _uiState.update { it.copy(signUpLoading = false) }

                if (signUpResponse is Response.Failure) {
                    _uiState.update {
                        it.copy(
                            signUpError = "${appContext.getString(R.string.error)}: ${signUpResponse.e.message}"
                        )
                    }
                }
            }
        }
    }

    private fun revalidateFieldsIfNeeded() {
        val currentState = _uiState.value

        if (!currentState.firstNameError
            || !currentState.lastNameError
            || !currentState.emailError
            || !currentState.passwordError
        ) {
            onFirstNameChanged(currentState.firstName)
            onLastNameChanged(currentState.lastName)
            onEmailChanged(currentState.email)
            onPasswordChanged(currentState.password)
        }
    }
}
