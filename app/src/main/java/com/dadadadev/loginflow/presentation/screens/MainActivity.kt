package com.dadadadev.loginflow.presentation.screens

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dadadadev.loginflow.navigation.MyNavGraph
import com.dadadadev.loginflow.presentation.theme.LoginflowTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideUp.interpolator = DecelerateInterpolator()
            slideUp.duration = 250L

            slideUp.doOnEnd { splashScreenView.remove() }

            slideUp.start()
        }

        setContent {
            LoginflowTheme {
                navController = rememberNavController()
                MyNavGraph(navController = navController)
            }
        }
    }
}