package com.dadadadev.loginflow.data.repository.auth

import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.home.UserInfo
import com.dadadadev.loginflow.data.model.sign.UserCredentials
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepositoryInterface {

    fun getUserInformation(viewModelScope: CoroutineScope): Flow<DataState<UserInfo>>
    suspend fun signUp(
        userCredentials: UserCredentials,
        viewModelScope: CoroutineScope
    ): Flow<DataState<Boolean>>

    suspend fun signIn(
        userCredentials: UserCredentials,
        viewModelScope: CoroutineScope
    ): Flow<DataState<Boolean>>

    fun signOut()
    fun getAuthState(viewModelScope: CoroutineScope): StateFlow<Boolean>
}