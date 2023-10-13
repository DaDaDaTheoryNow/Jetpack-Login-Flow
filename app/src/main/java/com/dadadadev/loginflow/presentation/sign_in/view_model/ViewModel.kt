package com.dadadadev.loginflow.presentation.sign_in.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.core.Response
import com.dadadadev.loginflow.core.TextFieldValidator
import com.dadadadev.loginflow.domain.repository.AuthRepository
import com.dadadadev.loginflow.domain.repository.SignInResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
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

                val signInResponse: SignInResponse =
                    authRepo.signIn(_uiState.value.email, _uiState.value.password)

                updateUIState { it.copy(signInLoading = false) }

                if (signInResponse is Response.Failure) {
                    updateUIState {
                        it.copy(
                            signInError = "Error: ${signInResponse.e.message}"
                        )
                    }
                }
            }
        }
    }

    /*private suspend fun signInUserInFirebase(email: String, password: String): SignInResult {
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
    }*/
}