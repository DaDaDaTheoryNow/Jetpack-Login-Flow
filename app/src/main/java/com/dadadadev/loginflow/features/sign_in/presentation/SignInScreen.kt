package com.dadadadev.loginflow.features.sign_in.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.common.components.BasicTextFieldComponent
import com.dadadadev.loginflow.common.components.DividerComponent
import com.dadadadev.loginflow.common.components.HeadingTextComponent
import com.dadadadev.loginflow.common.components.NormalTextComponent
import com.dadadadev.loginflow.common.components.PasswordTextFieldComponent
import com.dadadadev.loginflow.common.components.SignButtonComponent
import com.dadadadev.loginflow.common.navigation.AppScreens
import com.dadadadev.loginflow.features.sign_in.data.SignInUIEvent
import com.dadadadev.loginflow.features.sign_in.data.SignInUIState
import com.dadadadev.loginflow.features.sign_in.data.SignInViewModel
import com.dadadadev.loginflow.features.sign_in.presentation.components.ClickableForgotPasswordComponent
import com.dadadadev.loginflow.features.sign_in.presentation.components.ClickableRegisterTextComponent

@Composable
fun SignInScreen(
    viewState: SignInUIState,
    viewModel: SignInViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                viewModel.onEvent(
                    context = context,
                    event = SignInUIEvent.EmailChanged(value)
                )
            },
            isError = viewState.emailError,
            errorText = viewState.emailSupportText,
        )

        PasswordTextFieldComponent(
            textValue = viewState.password,
            onValueChange = { value ->
                viewModel.onEvent(
                    context = context,
                    event = SignInUIEvent.PasswordChanged(value)
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
            viewModel.onEvent(context = context, event = SignInUIEvent.SignInButtonPressed)
        }, loading = viewState.signInLoading, errorMessage = viewState.signInError)

        Spacer(modifier = Modifier.height(20.dp))

        DividerComponent(value = stringResource(id = R.string.or))

        Spacer(modifier = Modifier.height(3.dp))

        ClickableRegisterTextComponent(onRegisterPressed = {
            navController.navigate(AppScreens.SignUpScreen) {
                popUpTo(AppScreens.SignUpScreen) {
                    inclusive = true
                }
            }
        })

        Spacer(
            modifier = Modifier
                .weight(0.07f)
        )
    }
}