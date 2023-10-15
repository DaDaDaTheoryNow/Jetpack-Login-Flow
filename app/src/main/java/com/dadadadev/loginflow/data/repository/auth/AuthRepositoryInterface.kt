package com.dadadadev.loginflow.data.repository.auth

import com.dadadadev.loginflow.core.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

typealias SignUpResponse = Response<Boolean>
typealias SignInResponse = Response<Boolean>
typealias AuthStateResponse = StateFlow<Boolean>

interface AuthRepositoryInterface {
    suspend fun signUp(email: String, password: String): SignUpResponse
    suspend fun signIn(email: String, password: String): SignInResponse
    fun signOut()
    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
}