package com.dadadadev.loginflow.presentation.screens.sign_in

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
import com.dadadadev.loginflow.presentation.screens.sign_in.components.ClickableRegisterTextComponent
import com.dadadadev.loginflow.presentation.shared_components.BasicTextFieldComponent
import com.dadadadev.loginflow.presentation.shared_components.DividerComponent
import com.dadadadev.loginflow.presentation.shared_components.HeadingTextComponent
import com.dadadadev.loginflow.presentation.shared_components.NormalTextComponent
import com.dadadadev.loginflow.presentation.shared_components.PasswordTextFieldComponent
import com.dadadadev.loginflow.presentation.shared_components.SignButtonComponent

@Composable
fun SignInScreen(
    navigateToSignUpScreen: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val emailFieldState by viewModel.emailFieldState.collectAsStateWithLifecycle()
    val passwordFieldState by viewModel.passwordFieldState
        .collectAsStateWithLifecycle()

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
            fieldState = emailFieldState,
            labelValue = stringResource(R.string.email),
            imageVector = Icons.Outlined.Email,
            onValueChange = { value ->
                viewModel.onEmailChanged(
                    value,
                )
            },
        )

        PasswordTextFieldComponent(
            fieldState = passwordFieldState,
            onValueChange = { value ->
                viewModel.onPasswordChanged(
                    value,
                )
            },
        )

        Spacer(
            modifier = Modifier
                .weight(0.93f)
        )

        SignButtonComponent(value = stringResource(id = R.string.login), onPressed = {
            viewModel.userSignIn()
        }, signStatus = viewModel.signInStatus.value)

        Spacer(modifier = Modifier.height(20.dp))

        DividerComponent(value = stringResource(id = R.string.or))

        Spacer(modifier = Modifier.height(20.dp))

        ClickableRegisterTextComponent(onRegisterPressed = navigateToSignUpScreen)

        Spacer(
            modifier = Modifier
                .weight(0.07f)
        )
    }
}