package com.dadadadev.loginflow.data.service.firestore

import com.dadadadev.loginflow.core.Constants.USERS_COLLECTION
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.home.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirestoreService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : FirestoreServiceInterface {
    override suspend fun getUserInfoFromFirestore(viewModelScope: CoroutineScope): StateFlow<DataState<UserInfo>> =
        callbackFlow {
            val currentUserUid = firebaseAuth.currentUser?.uid

            if (currentUserUid != null) {
                val db = firebaseFirestore.collection(USERS_COLLECTION).document(currentUserUid)
                val snapshotListener = db.addSnapshotListener { documentSnapshot, error ->
                    if (error != null) {
                        trySend(DataState.Failure(error))
                    } else {
                        val userInfo = documentSnapshot?.toObject(UserInfo::class.java)
                        if (userInfo != null) {
                            trySend(DataState.Success(userInfo))
                        } else {
                            trySend(DataState.Failure(Exception("Error while fetching user information")))
                        }
                    }
                }

                awaitClose {
                    snapshotListener.remove()
                }
            } else {
                DataState.Failure(Exception("Current user is missing"))
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DataState.Loading)

    override suspend fun saveUserInfoToFirestore(
        firstName: String,
        lastName: String
    ): DataState<Boolean> = try {
        val currentUserUid = firebaseAuth.currentUser?.uid
        if (currentUserUid != null) {
            val db = firebaseFirestore.collection(USERS_COLLECTION).document(currentUserUid)

            withContext(Dispatchers.IO) {
                db.set(
                    hashMapOf(
                        "firstName" to firstName,
                        "lastName" to lastName,
                    )
                ).await()
            }

            DataState.Success(true)
        } else {
            DataState.Failure(Exception("User is missing"))
        }
    } catch (e: Exception) {
        DataState.Failure(e)
    }
}