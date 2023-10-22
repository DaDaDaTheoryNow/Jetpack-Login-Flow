package com.dadadadev.loginflow.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dadadadev.loginflow.navigation.MyNavGraph
import com.dadadadev.loginflow.presentation.theme.LoginflowTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
            LoginflowTheme {
                navController = rememberNavController()
                MyNavGraph(navController = navController)
            }
        }
    }
}