package com.dadadadev.loginflow.presentation.screens.sign_in

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.sign.FieldState
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import com.dadadadev.loginflow.data.repository.validate_fields.ValidateFieldsInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: AuthRepositoryInterface,
    private val validateRepo: ValidateFieldsInterface
) : ViewModel() {
    val emailFieldState: MutableStateFlow<FieldState> = MutableStateFlow(FieldState())
    val passwordFieldState: MutableStateFlow<FieldState> = MutableStateFlow(FieldState())
    val signInStatus: MutableState<DataState<Boolean>?> = mutableStateOf(null)

    private var ableToSignIn: Boolean = false

    init {
        listenAbleToSignInChanges()
    }

    fun onEmailChanged(email: String) {
        emailFieldState.value = validateRepo.validateEmail(email)
    }

    fun onPasswordChanged(password: String) {
        passwordFieldState.value = validateRepo.validatePassword(password)
    }

    fun userSignIn() {
        // fix when user tries to signIn without changing the fields
        revalidateFields()

        viewModelScope.launch {
            if (ableToSignIn) {
                authRepo.signIn(emailFieldState.value.value, passwordFieldState.value.value)
                    .onEach {
                        signInStatus.value = it
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun listenAbleToSignInChanges() {
        emailFieldState.combine(passwordFieldState) { emailState, passwordState ->
            ableToSignIn =
                emailState.errorMessage.isEmpty() && passwordState.errorMessage.isEmpty()
        }.launchIn(viewModelScope)
    }

    private fun revalidateFields() {
        if (emailFieldState.value.errorMessage.isEmpty() || passwordFieldState.value.errorMessage.isEmpty()) {
            onEmailChanged(emailFieldState.value.value)
            onPasswordChanged(passwordFieldState.value.value)
        }
    }
}
