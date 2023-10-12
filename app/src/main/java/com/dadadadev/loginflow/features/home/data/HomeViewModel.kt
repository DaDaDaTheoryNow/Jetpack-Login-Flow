package com.dadadadev.loginflow.features.home.data

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    fun onEvent(event: HomeUIEvent) {
        when (event) {
            is HomeUIEvent.signOutButtonPressed -> userSignOut()
        }
    }

    private fun userSignOut() {
        FirebaseAuth.getInstance().signOut()
    }
}