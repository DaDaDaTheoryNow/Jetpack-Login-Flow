package com.dadadadev.loginflow.features.home.data

sealed class HomeUIEvent {
    object signOutButtonPressed : HomeUIEvent()
}