package com.dadadadev.loginflow.features.sign_in.data

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

data class SignInResult(
    val success: Boolean,
    val errorMessage: String = ""
)

class SignInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUIState())
    val uiState: StateFlow<SignInUIState> = _uiState.asStateFlow()

    private var ableToSignIn = false

    fun onEvent(event: SignInUIEvent, context: Context) {
        when (event) {
            is SignInUIEvent.EmailChanged -> {
                val validate = TextFieldValidator.validateEmail(context, event.value)
                updateUIState {
                    it.copy(
                        email = event.value,
                        emailError = validate.isError,
                        emailSupportText = validate.supportText
                    )
                }
            }

            is SignInUIEvent.PasswordChanged -> {
                val validate =
                    TextFieldValidator.validatePassword(context, event.value)

                updateUIState {
                    it.copy(
                        password = event.value,
                        passwordError = validate.isError,
                        passwordSupportText = validate.supportText
                    )
                }
            }

            is SignInUIEvent.SignInButtonPressed ->
                onSignInButtonPressed(context)
        }

        checkSignInEligibility(_uiState.value)
    }

    private fun updateUIState(updateFunction: (SignInUIState) -> SignInUIState) {
        _uiState.update { currentState -> updateFunction(currentState) }
    }

    private fun checkSignInEligibility(currentState: SignInUIState) {
        ableToSignIn = (!currentState.emailError
                && !currentState.passwordError)
    }

    private fun revalidateFieldsIfNeeded(currentState: SignInUIState, context: Context) {
        if (!currentState.emailError
            || !currentState.passwordError
        ) {
            onEvent(SignInUIEvent.EmailChanged(currentState.email), context)
            onEvent(SignInUIEvent.PasswordChanged(currentState.password), context)
        }
    }

    private fun onSignInButtonPressed(context: Context) {
        updateUIState { it.copy(signInError = "") }
        revalidateFieldsIfNeeded(_uiState.value, context)

        if (ableToSignIn) {
            viewModelScope.launch {
                updateUIState { it.copy(signInLoading = true) }

                val signInResult = signInUserInFirebase(
                    email = _uiState.value.email,
                    password = _uiState.value.password
                )

                updateUIState { it.copy(signInLoading = false) }

                if (signInResult.success) {
                    // go to main page
                } else {
                    updateUIState { it.copy(signInError = signInResult.errorMessage) }
                }
            }
        }
    }

    private suspend fun signInUserInFirebase(email: String, password: String): SignInResult {
        return try {
            val authResult =
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            val user = authResult.user
            SignInResult(success = user != null)
        } catch (exception: Exception) {
            if (exception.message?.contains("INVALID_LOGIN_CREDENTIALS") == true)
                SignInResult(success = false, errorMessage = "Error: Invalid login credentials")
            else
                SignInResult(success = false, errorMessage = "Error: ${exception.localizedMessage}")
        }
    }
}