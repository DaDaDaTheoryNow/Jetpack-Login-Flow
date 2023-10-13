package com.dadadadev.loginflow.data.repository

import com.dadadadev.loginflow.core.Response
import com.dadadadev.loginflow.domain.repository.AuthRepository
import com.dadadadev.loginflow.domain.repository.AuthStateResponse
import com.dadadadev.loginflow.domain.repository.SignInResponse
import com.dadadadev.loginflow.domain.repository.SignUpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun signUp(email: String, password: String): SignUpResponse =
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    override suspend fun signIn(email: String, password: String): SignInResponse =
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }


    override fun signOut() {
        auth.signOut()
    }

    override fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }

        auth.addAuthStateListener(authStateListener)

        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)
}