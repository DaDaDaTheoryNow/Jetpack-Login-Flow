package com.dadadadev.loginflow.features.sign_up.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import com.dadadadev.loginflow.features.sign_up.data.SignUpUIEvent
import com.dadadadev.loginflow.features.sign_up.data.SignUpUIState
import com.dadadadev.loginflow.features.sign_up.data.SignUpViewModel
import com.dadadadev.loginflow.features.sign_up.presentation.components.ClickableLoginTextComponent
import com.dadadadev.loginflow.features.sign_up.presentation.components.PrivacyPolicyCheckBoxComponent

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel,
    viewState: SignUpUIState
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
            text = stringResource(R.string.create_account),
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextFieldComponent(
            textValue = viewState.firstName,
            labelValue = stringResource(R.string.first_name),
            imageVector = Icons.Filled.Person,
            onValueChange = { value ->
                viewModel.onEvent(
                    context = context,
                    event = SignUpUIEvent.FirstNameChanged(value)
                )
            },
            isError = viewState.firstNameError,
            errorText = viewState.firstNameSupportText,
        )

        BasicTextFieldComponent(
            textValue = viewState.lastName,
            labelValue = stringResource(R.string.last_name),
            imageVector = Icons.Filled.Person,
            onValueChange = { value ->
                viewModel.onEvent(
                    context = context,
                    event = SignUpUIEvent.LastNameChanged(value)
                )
            },
            isError = viewState.lastNameError,
            errorText = viewState.lastNameSupportText,
        )
        BasicTextFieldComponent(
            textValue = viewState.email,
            labelValue = stringResource(R.string.email),
            imageVector = Icons.Outlined.Email,
            onValueChange = { value ->
                viewModel.onEvent(
                    context = context,
                    event = SignUpUIEvent.EmailChanged(value)
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
                    event = SignUpUIEvent.PasswordChanged(value)
                )
            },
            isError = viewState.passwordError,
            errorText = viewState.passwordSupportText,
        )

        PrivacyPolicyCheckBoxComponent(
            onPrivacyPolicyPressed = { navController.navigate(AppScreens.PrivacyPolicyScreen) },
            onCheckBoxPressed = { value ->
                viewModel.onEvent(
                    context = context,
                    event = SignUpUIEvent.PrivacyPolicyCheckBoxPressed(value)
                )
            },
            checkBoxState = viewState.privacyPolicyCheckBox,
            checkBoxError = viewState.privacyPolicyCheckBoxError
        )

        Spacer(
            modifier = Modifier
                .weight(0.9f)
        )

        SignButtonComponent(value = stringResource(id = R.string.register), onPressed = {
            viewModel.onEvent(context = context, event = SignUpUIEvent.SignUpButtonPressed)
        }, loading = viewState.signUpLoading, errorMessage = viewState.signUpError)

        Spacer(modifier = Modifier.height(20.dp))

        DividerComponent(value = stringResource(id = R.string.or))

        Spacer(modifier = Modifier.height(14.dp))

        ClickableLoginTextComponent(onLoginPressed = {
            navController.navigate(AppScreens.SignInScreen)
        })

        Spacer(
            modifier = Modifier
                .weight(0.1f)
        )
    }
}