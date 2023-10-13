package com.dadadadev.loginflow.presentation.home

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.dadadadev.loginflow.presentation.home.components.TopAppBarComponent
import com.dadadadev.loginflow.presentation.home.view_model.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    Scaffold(
        topBar = { TopAppBarComponent(onSignOutButtonPressed = { viewModel.userSignOut() }) },
        containerColor = Color.White,
    ) {

    }
}