package com.dadadadev.loginflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dadadadev.loginflow.presentation.screens.home.HomeScreen
import com.dadadadev.loginflow.presentation.screens.privacy_policy.PrivacyPolicyScreen
import com.dadadadev.loginflow.presentation.screens.sign_in.SignInScreen
import com.dadadadev.loginflow.presentation.screens.sign_up.SignUpScreen

@Composable
fun MyNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignInScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen()
        }
        composable(
            route = Screen.SignInScreen.route
        ) {
            SignInScreen(
                navigateToSignUpScreen = {
                    navController.navigate(Screen.SignUpScreen.route)
                }
            )
        }
        composable(
            route = Screen.SignUpScreen.route
        ) {
            SignUpScreen(
                navigateToSignInScreen = {
                    navController.popBackStack()
                },
                navigateToPrivacyPolicyScreen = {
                    navController.navigate(Screen.PrivacyPolicyScreen.route)
                }
            )
        }
        composable(
            route = Screen.PrivacyPolicyScreen.route
        ) {
            PrivacyPolicyScreen(
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}