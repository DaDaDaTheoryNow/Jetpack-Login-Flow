package com.dadadadev.loginflow.data.service.firestore

import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.home.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface FirestoreServiceInterface {
    suspend fun getUserInfoFromFirestore(viewModelScope: CoroutineScope): StateFlow<DataState<UserInfo>>
    suspend fun saveUserInfoToFirestore(firstName: String, lastName: String): DataState<Boolean>
}