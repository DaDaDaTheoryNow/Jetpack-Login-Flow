package com.dadadadev.loginflow.di

import com.dadadadev.loginflow.data.repository.AuthRepositoryImpl
import com.dadadadev.loginflow.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository =
        AuthRepositoryImpl(auth = FirebaseAuth.getInstance())
}