package com.dadadadev.loginflow.di

import com.dadadadev.loginflow.data.service.firestore.FirestoreService
import com.dadadadev.loginflow.data.service.firestore.FirestoreServiceInterface
import com.dadadadev.loginflow.data.service.time.TimeService
import com.dadadadev.loginflow.data.service.time.TimeServiceInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideFirestoreService(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): FirestoreServiceInterface = FirestoreService(firebaseAuth, firestore)

    @Singleton
    @Provides
    fun provideTimeService(
    ): TimeServiceInterface = TimeService()
}