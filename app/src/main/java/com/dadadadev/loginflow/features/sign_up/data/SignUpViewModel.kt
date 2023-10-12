package com.dadadadev.loginflow.features.sign_up.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.common.rules.sign_validation.TextFieldValidator
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class SignUpResult(
    val success: Boolean,
    val errorMessage: String = ""
)

class SignUpViewModel(
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    private var ableToSignUp = false

    fun onEvent(event: SignUpUIEvent, context: Context) {
        when (event) {
            is SignUpUIEvent.FirstNameChanged -> {
                val validate = TextFieldValidator.validateFirstName(context, event.value)
                updateUIState {
                    it.copy(
                        firstName = event.value,
                        firstNameError = validate.isError,
                        firstNameSupportText = validate.supportText
                    )
                }
            }

            is SignUpUIEvent.LastNameChanged -> {
                val validate = TextFieldValidator.validateLastName(context, event.value)
                updateUIState {
                    it.copy(
                        lastName = event.value,
                        lastNameError = validate.isError,
                        lastNameSupportText = validate.supportText
                    )
                }
            }

            is SignUpUIEvent.EmailChanged -> {
                val validate = TextFieldValidator.validateEmail(context, event.value)
                updateUIState {
                    it.copy(
                        email = event.value,
                        emailError = validate.isError,
                        emailSupportText = validate.supportText
                    )
                }
            }

            is SignUpUIEvent.PasswordChanged -> {
                val validate = TextFieldValidator.validatePassword(context, event.value)
                updateUIState {
                    it.copy(
                        password = event.value,
                        passwordError = validate.isError,
                        passwordSupportText = validate.supportText
                    )
                }
            }

            is SignUpUIEvent.PrivacyPolicyCheckBoxPressed -> {
                updateUIState { it.copy(privacyPolicyCheckBox = event.value) }
            }

            is SignUpUIEvent.SignUpButtonPressed -> {
                onSignUpButtonPressed(context)
            }
        }

        validatePrivacyPolicyCheckBox()
        checkSignUpEligibility(_uiState.value)
    }

    private fun updateUIState(updateFunction: (SignUpUIState) -> SignUpUIState) {
        _uiState.update { currentState -> updateFunction(currentState) }
    }

    private fun validatePrivacyPolicyCheckBox() {
        val privacyPolicyCheckBoxError = !_uiState.value.privacyPolicyCheckBox
        updateUIState { it.copy(privacyPolicyCheckBoxError = privacyPolicyCheckBoxError) }
    }

    private fun checkSignUpEligibility(currentState: SignUpUIState) {
        ableToSignUp = (!currentState.firstNameError
                && !currentState.lastNameError
                && !currentState.emailError
                && !currentState.passwordError && currentState.privacyPolicyCheckBox)
    }

    private fun revalidateFieldsIfNeeded(currentState: SignUpUIState, context: Context) {
        if (!currentState.firstNameError
            || !currentState.lastNameError
            || !currentState.emailError
            || !currentState.passwordError
        ) {
            onEvent(SignUpUIEvent.FirstNameChanged(currentState.firstName), context)
            onEvent(SignUpUIEvent.LastNameChanged(currentState.lastName), context)
            onEvent(SignUpUIEvent.EmailChanged(currentState.email), context)
            onEvent(SignUpUIEvent.PasswordChanged(currentState.password), context)
        }
    }

    private fun onSignUpButtonPressed(context: Context) {
        updateUIState { it.copy(signUpError = "") }
        revalidateFieldsIfNeeded(_uiState.value, context)

        if (ableToSignUp) {
            viewModelScope.launch {
                updateUIState { it.copy(signUpLoading = true) }

                val signUpResult = createUserInFirebase(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )

                updateUIState { it.copy(signUpLoading = false) }

                if (signUpResult.success) {
                    // go to home
                } else {
                    updateUIState { it.copy(signUpError = signUpResult.errorMessage) }
                }
            }
        }
    }

    private suspend fun createUserInFirebase(email: String, password: String): SignUpResult {
        return try {
            val authResult =
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
            SignUpResult(success = user != null)
        } catch (exception: Exception) {
            SignUpResult(success = false, errorMessage = "Error: ${exception.message}")
        }
    }
}
