package com.dadadadev.loginflow.presentation.screens.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import com.dadadadev.loginflow.presentation.screens.sign_up.components.ClickableLoginTextComponent
import com.dadadadev.loginflow.presentation.screens.sign_up.components.PrivacyPolicyCheckBoxComponent
import com.dadadadev.loginflow.presentation.screens.sign_up.view_model.SignUpViewModel
import com.dadadadev.loginflow.presentation.shared_components.BasicTextFieldComponent
import com.dadadadev.loginflow.presentation.shared_components.DividerComponent
import com.dadadadev.loginflow.presentation.shared_components.HeadingTextComponent
import com.dadadadev.loginflow.presentation.shared_components.NormalTextComponent
import com.dadadadev.loginflow.presentation.shared_components.PasswordTextFieldComponent
import com.dadadadev.loginflow.presentation.shared_components.SignButtonComponent

@Composable
fun SignUpScreen(
    navigateToSignInScreen: () -> Unit,
    navigateToPrivacyPolicyScreen: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val viewState by viewModel.uiState.collectAsStateWithLifecycle()

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
            text = stringResource(R.string.create_account),
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicTextFieldComponent(
            textValue = viewState.firstName,
            labelValue = stringResource(R.string.first_name),
            imageVector = Icons.Filled.Person,
            onValueChange = { value ->
                viewModel.onFirstNameChanged(value)
            },
            isError = viewState.firstNameError,
            errorText = viewState.firstNameSupportText,
        )

        BasicTextFieldComponent(
            textValue = viewState.lastName,
            labelValue = stringResource(R.string.last_name),
            imageVector = Icons.Filled.Person,
            onValueChange = { value ->
                viewModel.onLastNameChanged(value)
            },
            isError = viewState.lastNameError,
            errorText = viewState.lastNameSupportText,
        )
        BasicTextFieldComponent(
            textValue = viewState.email,
            labelValue = stringResource(R.string.email),
            imageVector = Icons.Outlined.Email,
            onValueChange = { value ->
                viewModel.onEmailChanged(value)
            },
            isError = viewState.emailError,
            errorText = viewState.emailSupportText,
        )

        PasswordTextFieldComponent(
            textValue = viewState.password,
            onValueChange = { value ->
                viewModel.onPasswordChanged(value)
            },
            isError = viewState.passwordError,
            errorText = viewState.passwordSupportText,
        )

        PrivacyPolicyCheckBoxComponent(
            onPrivacyPolicyPressed = navigateToPrivacyPolicyScreen,
            onCheckBoxPressed = { value ->
                viewModel.onPrivacyPolicyCheckBoxPressed(value)
            },
            checkBoxState = viewState.privacyPolicyCheckBox,
            checkBoxError = viewState.privacyPolicyCheckBoxError
        )

        Spacer(
            modifier = Modifier
                .weight(0.9f)
        )

        SignButtonComponent(value = stringResource(id = R.string.register), onPressed = {
            viewModel.userSignUp()
        }, loading = viewState.signUpLoading, errorMessage = viewState.signUpError)

        Spacer(modifier = Modifier.height(20.dp))

        DividerComponent(value = stringResource(id = R.string.or))

        Spacer(modifier = Modifier.height(14.dp))

        ClickableLoginTextComponent(onLoginPressed = navigateToSignInScreen)

        Spacer(
            modifier = Modifier
                .weight(0.1f)
        )
    }
}