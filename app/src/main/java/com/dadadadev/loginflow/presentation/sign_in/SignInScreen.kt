package com.dadadadev.loginflow.presentation.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.components.BasicTextFieldComponent
import com.dadadadev.loginflow.components.DividerComponent
import com.dadadadev.loginflow.components.HeadingTextComponent
import com.dadadadev.loginflow.components.NormalTextComponent
import com.dadadadev.loginflow.components.PasswordTextFieldComponent
import com.dadadadev.loginflow.components.SignButtonComponent
import com.dadadadev.loginflow.presentation.sign_in.components.ClickableForgotPasswordComponent
import com.dadadadev.loginflow.presentation.sign_in.components.ClickableRegisterTextComponent
import com.dadadadev.loginflow.presentation.sign_in.view_model.SignInUIState
import com.dadadadev.loginflow.presentation.sign_in.view_model.SignInViewModel

@Composable
fun SignInScreen(
    navigateToSignUpScreen: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val viewState: SignInUIState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NormalTextComponent(
            text = stringResource(R.string.hello),
        )
        HeadingTextComponent(
            text = stringResource(R.string.welcome_back),
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextFieldComponent(
            textValue = viewState.email,
            labelValue = stringResource(R.string.email),
            imageVector = Icons.Outlined.Email,
            onValueChange = { value ->
                viewModel.onEmailChanged(
                    value,
                )
            },
            isError = viewState.emailError,
            errorText = viewState.emailSupportText,
        )

        PasswordTextFieldComponent(
            textValue = viewState.password,
            onValueChange = { value ->
                viewModel.onPasswordChanged(
                    value,
                )
            },
            isError = viewState.passwordError,
            errorText = viewState.passwordSupportText,
        )

        Spacer(
            modifier = Modifier
                .height(10.dp)
        )

        ClickableForgotPasswordComponent(
            value = stringResource(id = R.string.forgot_password),
            onForgotPasswordPressed = {})

        Spacer(
            modifier = Modifier
                .weight(0.93f)
        )

        SignButtonComponent(value = stringResource(id = R.string.login), onPressed = {
            viewModel.userSignIn()
        }, loading = viewState.signInLoading, errorMessage = viewState.signInError)

        Spacer(modifier = Modifier.height(20.dp))

        DividerComponent(value = stringResource(id = R.string.or))

        Spacer(modifier = Modifier.height(3.dp))

        ClickableRegisterTextComponent(onRegisterPressed = navigateToSignUpScreen)

        Spacer(
            modifier = Modifier
                .weight(0.07f)
        )
    }
}