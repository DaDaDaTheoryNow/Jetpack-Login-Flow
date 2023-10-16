package com.dadadadev.loginflow.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dadadadev.loginflow.navigation.MyNavGraph
import com.dadadadev.loginflow.navigation.Screen
import com.dadadadev.loginflow.presentation.theme.LoginflowTheme
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

                val isUserSignedOut by viewModel.getAuthState().collectAsState()
                if (isUserSignedOut) {
                    NavigateToSignInScreen()
                } else {
                    NavigateToHomeScreen()
                }
            }
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