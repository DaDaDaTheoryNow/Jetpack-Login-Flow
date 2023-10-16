package com.dadadadev.loginflow.data.repository.auth

import com.dadadadev.loginflow.core.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepositoryInterface {
    suspend fun signUp(email: String, password: String): Flow<DataState<Boolean>>
    suspend fun signIn(email: String, password: String): Flow<DataState<Boolean>>
    fun signOut()
    fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean>
}