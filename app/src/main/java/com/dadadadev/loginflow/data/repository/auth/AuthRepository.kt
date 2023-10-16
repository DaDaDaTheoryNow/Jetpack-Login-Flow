package com.dadadadev.loginflow.data.repository.auth

import android.app.Application
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.core.DataState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val appContext: Application
) : AuthRepositoryInterface {
    override suspend fun signUp(email: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }

    override suspend fun signIn(email: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            emit(DataState.Success(true))
        } catch (e: Exception) {
            val unknownErrorString: String = appContext.getString(
                R.string.unknown_error
            )
            val signInError = e.message ?: unknownErrorString

            if (signInError.contains("INVALID_LOGIN_CREDENTIALS")) {
                emit(DataState.Failure(Exception("Invalid login credentials")))
            } else {
                emit(DataState.Failure(e))
            }
        }
    }

    override fun signOut() {
        auth.signOut()
    }

    override fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }

        auth.addAuthStateListener(authStateListener)

        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}