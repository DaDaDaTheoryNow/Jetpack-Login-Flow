package com.dadadadev.loginflow.presentation.screens.privacy_policy

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.presentation.shared_components.HeadingTextComponent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(navigateBack: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(R.string.privacy_policy_header))
        }, navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
                navigationIconContentColor = Color.Black,
            )
        )
    }, containerColor = Color.White) {
        HeadingTextComponent(text = stringResource(R.string.privacy_policy_header))
    }
}