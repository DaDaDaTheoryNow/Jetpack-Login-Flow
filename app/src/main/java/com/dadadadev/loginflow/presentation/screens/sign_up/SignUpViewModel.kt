package com.dadadadev.loginflow.presentation.screens.sign_up

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.sign.FieldState
import com.dadadadev.loginflow.data.model.sign.PrivacyPolicyCheckBoxState
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import com.dadadadev.loginflow.data.repository.validate_fields.ValidateFieldsInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepo: AuthRepositoryInterface,
    private val validateRepo: ValidateFieldsInterface
) : ViewModel() {
    val firstNameFieldState: MutableStateFlow<FieldState> = MutableStateFlow(FieldState())
    val lastNameFieldState: MutableStateFlow<FieldState> = MutableStateFlow(FieldState())
    val emailFieldState: MutableStateFlow<FieldState> = MutableStateFlow(FieldState())
    val passwordFieldState: MutableStateFlow<FieldState> = MutableStateFlow(FieldState())
    val privacyPolicyCheckBoxState: MutableStateFlow<PrivacyPolicyCheckBoxState> = MutableStateFlow(
        PrivacyPolicyCheckBoxState()
    )

    val signUpStatus: MutableState<DataState<Boolean>?> = mutableStateOf(null)

    private var ableToSignUp: Flow<Boolean> = combine(
        firstNameFieldState,
        lastNameFieldState,
        emailFieldState,
        passwordFieldState,
        privacyPolicyCheckBoxState
    ) { firstName, lastName, email, password, privacyPolicyCheckBox ->
        firstName.errorMessage.isEmpty() &&
                lastName.errorMessage.isEmpty() &&
                email.errorMessage.isEmpty() &&
                password.errorMessage.isEmpty() &&
                !privacyPolicyCheckBox.isError
    }.distinctUntilChanged()

    fun onFirstNameChanged(firstName: String) {
        firstNameFieldState.value = validateRepo.validateFirstName(firstName)
    }

    fun onLastNameChanged(lastName: String) {
        lastNameFieldState.value = validateRepo.validateLastName(lastName)
    }

    fun onEmailChanged(email: String) {
        emailFieldState.value = validateRepo.validateEmail(email)
    }

    fun onPasswordChanged(password: String) {
        passwordFieldState.value = validateRepo.validatePassword(password)
    }


    fun onPrivacyPolicyCheckBoxPressed(value: Boolean) {
        privacyPolicyCheckBoxState.value = validateRepo.validatePrivacyPolicyCheckBox(value)
    }

    fun userSignUp() {
        // fix when user tries to signIn without changing the fields
        revalidateFields()

        viewModelScope.launch {
            if (ableToSignUp.first()) {
                authRepo.signUp(emailFieldState.value.value, passwordFieldState.value.value)
                    .onEach {
                        signUpStatus.value = it
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun revalidateFields() {
        val firstNameField = firstNameFieldState.value
        val lastNameField = lastNameFieldState.value
        val emailField = emailFieldState.value
        val passwordField = passwordFieldState.value
        val privacyPolicyCheckBox = privacyPolicyCheckBoxState.value

        if (firstNameField.errorMessage.isEmpty() || lastNameField.errorMessage.isEmpty() || emailField.errorMessage.isEmpty() || passwordField.errorMessage.isEmpty() || !privacyPolicyCheckBox.isError) {
            onFirstNameChanged(firstNameField.value)
            onLastNameChanged(lastNameField.value)
            onPrivacyPolicyCheckBoxPressed(privacyPolicyCheckBox.value)
            onEmailChanged(emailField.value)
            onPasswordChanged(passwordField.value)
        }
    }
}
