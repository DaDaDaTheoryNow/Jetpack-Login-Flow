package com.dadadadev.loginflow.presentation.screens.sign_in.view_model


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.core.Response
import com.dadadadev.loginflow.core.TextFieldValidator
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import com.dadadadev.loginflow.data.repository.auth.SignInResponse
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
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepositoryInterface,
    private val textFieldValidator: TextFieldValidator,
    private val appContext: Application
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUIState())
    val uiState: StateFlow<SignInUIState> = _uiState.asStateFlow()

    private var ableToSignIn = _uiState.asStateFlow().map { currentState ->
        !currentState.emailError && !currentState.passwordError
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
    }

    fun userSignIn() {
        _uiState.update { it.copy(signInError = "") }
        revalidateFieldsIfNeeded()

        viewModelScope.launch {
            val ableToSignInValue: Boolean = ableToSignIn.first()
            if (ableToSignInValue) {
                _uiState.update { it.copy(signInLoading = true) }

                val signInResponse: SignInResponse =
                    authRepo.signIn(_uiState.value.email, _uiState.value.password)

                if (signInResponse is Response.Failure) {
                    val errorString: String = appContext.getString(R.string.error)
                    val unknownErrorString: String = appContext.getString(
                        R.string.unknown_error
                    )

                    var signInError =
                        "$errorString: ${signInResponse.e.message ?: unknownErrorString}"

                    if (signInError.contains("INVALID_LOGIN_CREDENTIALS")) {
                        signInError = "Error: Invalid login credentials"
                    }

                    _uiState.update {
                        it.copy(
                            signInError = signInError
                        )
                    }
                }

                _uiState.update { it.copy(signInLoading = false) }
            }
        }
    }

    private fun revalidateFieldsIfNeeded() {
        val currentState = _uiState.value

        if (!currentState.emailError || !currentState.passwordError) {
            onEmailChanged(currentState.email)
            onPasswordChanged(currentState.password)
        }
    }
}
