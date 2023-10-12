package com.dadadadev.loginflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dadadadev.loginflow.features.app.LoginFlowApp
import com.dadadadev.loginflow.features.app.data.AppState
import com.dadadadev.loginflow.features.app.data.AppViewModel
import com.dadadadev.loginflow.ui.theme.LoginflowTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            LoginflowTheme {
                val viewModel: AppViewModel = viewModel()
                val viewState: AppState by viewModel.state.collectAsStateWithLifecycle()
                LoginFlowApp(viewState = viewState)
            }
        }
    }
}