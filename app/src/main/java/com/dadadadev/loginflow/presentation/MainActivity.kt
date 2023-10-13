package com.dadadadev.loginflow.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dadadadev.loginflow.core.theme.LoginflowTheme
import com.dadadadev.loginflow.navigation.MyNavGraph
import com.dadadadev.loginflow.navigation.Screen
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            LoginflowTheme {
                navController = rememberNavController()
                MyNavGraph(navController = navController)
                AuthState()
            }
        }
    }

    @Composable
    private fun AuthState() {
        val isUserSignedOut = viewModel.getAuthState().collectAsStateWithLifecycle().value
        if (isUserSignedOut) {
            NavigateToSignInScreen()
        } else {
            NavigateToHomeScreen()
        }
    }

    @Composable
    private fun NavigateToSignInScreen() = navController.navigate(Screen.SignInScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }

    @Composable
    private fun NavigateToHomeScreen() = navController.navigate(Screen.HomeScreen.route) {
        popUpTo(navController.graph.id) {
            inclusive = true
        }
    }
}