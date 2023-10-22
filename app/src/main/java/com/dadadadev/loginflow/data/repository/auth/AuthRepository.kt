package com.dadadadev.loginflow.data.repository.auth

import android.app.Application
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.home.UserInfo
import com.dadadadev.loginflow.data.model.sign.UserCredentials
import com.dadadadev.loginflow.data.service.firestore.FirestoreServiceInterface
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val appContext: Application,
    private val firestoreService: FirestoreServiceInterface
) : AuthRepositoryInterface {
    override fun getUserInformation(viewModelScope: CoroutineScope): Flow<DataState<UserInfo>> =
        flow {
            emit(DataState.Loading)
            delay(750L)
            val dataState = firestoreService.getUserInfoFromFirestore(viewModelScope)

            dataState.collect {
                if (it is DataState.Success) {
                    emit(DataState.Success(it.data))
                } else if (it is DataState.Failure) {
                    emit(DataState.Failure(it.exception))
                }
            }
        }

    override suspend fun signUp(
        userCredentials: UserCredentials,
        viewModelScope: CoroutineScope
    ): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            auth.createUserWithEmailAndPassword(userCredentials.email, userCredentials.password)
                .await()

            firestoreService.saveUserInfoToFirestore(
                userCredentials.firstName,
                userCredentials.lastName
            )

            emit(DataState.Success(true))
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun signIn(
        userCredentials: UserCredentials,
        viewModelScope: CoroutineScope
    ): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            auth.signInWithEmailAndPassword(userCredentials.email, userCredentials.password).await()
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
    }.flowOn(Dispatchers.IO)

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
    }.stateIn(viewModelScope, SharingStarted.Lazily, auth.currentUser == null)
}