package com.dadadadev.loginflow.features.app.data

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        checkUserSignIn(callback = { userIsSignIn ->
            _state.update {
                it.copy(
                    isUserSign = userIsSignIn
                )
            }
        })
    }

    private fun checkUserSignIn(callback: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().addAuthStateListener { firebaseAuth ->
            val userIsSignIn = firebaseAuth.currentUser != null
            callback(userIsSignIn)
        }
    }
}