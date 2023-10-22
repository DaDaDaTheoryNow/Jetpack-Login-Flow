package com.dadadadev.loginflow.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dadadadev.loginflow.presentation.screens.MainViewModel
import com.dadadadev.loginflow.presentation.screens.home.HomeScreen
import com.dadadadev.loginflow.presentation.screens.privacy_policy.PrivacyPolicyScreen
import com.dadadadev.loginflow.presentation.screens.sign_in.SignInScreen
import com.dadadadev.loginflow.presentation.screens.sign_up.SignUpScreen

@Composable
fun MyNavGraph(navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()) {
    val isUserSignOut by mainViewModel.isUserSignOut

    NavHost(
        navController = navController,
        startDestination = if (isUserSignOut) Screen.SignInScreen.route else Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = Screen.SignInScreen.route) {
            SignInScreen(
                navigateToSignUpScreen = {
                    navController.navigate(Screen.SignUpScreen.route)
                }
            )
        }
        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(
                navigateToSignInScreen = {
                    navController.navigate(Screen.SignInScreen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                navigateToPrivacyPolicyScreen = {
                    navController.navigate(Screen.PrivacyPolicyScreen.route)
                }
            )
        }
        composable(route = Screen.PrivacyPolicyScreen.route) {
            PrivacyPolicyScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}