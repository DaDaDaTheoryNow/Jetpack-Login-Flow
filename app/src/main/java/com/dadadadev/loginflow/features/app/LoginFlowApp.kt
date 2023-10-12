package com.dadadadev.loginflow.features.app

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dadadadev.loginflow.common.navigation.AppScreens
import com.dadadadev.loginflow.features.app.data.AppState
import com.dadadadev.loginflow.features.home.data.HomeViewModel
import com.dadadadev.loginflow.features.home.presentation.HomeScreen
import com.dadadadev.loginflow.features.privacy_policy.presentation.PrivacyPolicyScreen
import com.dadadadev.loginflow.features.sign_in.data.SignInUIState
import com.dadadadev.loginflow.features.sign_in.data.SignInViewModel
import com.dadadadev.loginflow.features.sign_in.presentation.SignInScreen
import com.dadadadev.loginflow.features.sign_up.data.SignUpUIState
import com.dadadadev.loginflow.features.sign_up.data.SignUpViewModel
import com.dadadadev.loginflow.features.sign_up.presentation.SignUpScreen

@Composable
fun LoginFlowApp(viewState: AppState) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = if (viewState.isUserSign) AppScreens.HomeScreen else AppScreens.SignUpScreen
        ) {
            composable(route = AppScreens.HomeScreen, enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(500)
                )
            }, exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(500)
                )
            }) {
                val viewModel = viewModel<HomeViewModel>()
                HomeScreen(viewModel = viewModel)
            }

            composable(route = AppScreens.SignInScreen) {
                Box(modifier = Modifier.padding(15.dp)) {
                    val signInViewModel = viewModel<SignInViewModel>()
                    val signInViewState: SignInUIState by signInViewModel.uiState.collectAsStateWithLifecycle()
                    SignInScreen(
                        viewState = signInViewState,
                        viewModel = signInViewModel,
                        navController
                    )
                }
            }

            composable(route = AppScreens.SignUpScreen, enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(800)
                )
            }, exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(520)
                )
            }) {
                Box(modifier = Modifier.padding(15.dp)) {
                    val signUpViewModel = viewModel<SignUpViewModel>()
                    val signUpViewState: SignUpUIState by signUpViewModel.uiState.collectAsStateWithLifecycle()
                    SignUpScreen(
                        viewState = signUpViewState,
                        viewModel = signUpViewModel,
                        navController = navController
                    )
                }
            }

            composable(route = AppScreens.PrivacyPolicyScreen, enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(520)
                )
            }, exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(520)
                )
            }) {
                PrivacyPolicyScreen(navController)
            }
        }
    }
}