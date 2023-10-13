package com.dadadadev.loginflow.navigation

import com.dadadadev.loginflow.core.Constants.HOME_SCREEN
import com.dadadadev.loginflow.core.Constants.PRIVACY_POLICY_SCREEN
import com.dadadadev.loginflow.core.Constants.SIGN_IN_SCREEN
import com.dadadadev.loginflow.core.Constants.SIGN_UP_SCREEN

sealed class Screen(val route: String) {
    object SignInScreen : Screen(SIGN_IN_SCREEN)
    object SignUpScreen : Screen(SIGN_UP_SCREEN)
    object PrivacyPolicyScreen : Screen(PRIVACY_POLICY_SCREEN)
    object HomeScreen : Screen(HOME_SCREEN)
}