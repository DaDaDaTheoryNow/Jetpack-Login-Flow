package com.dadadadev.loginflow.presentation.screens.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.dadadadev.loginflow.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarComponent(onSignOutButtonPressed: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.primaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        title = {
            Text(
                stringResource(id = R.string.home),
                style = TextStyle(
                    fontSize = 20.sp
                ),
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = onSignOutButtonPressed) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = null
                )
            }
        },
    )
}