package com.dadadadev.loginflow.di

import com.dadadadev.loginflow.data.repository.AuthRepositoryImpl
import com.dadadadev.loginflow.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {
    @Provides
    fun provideAuthRepository(): AuthRepository =
        AuthRepositoryImpl(auth = FirebaseAuth.getInstance())
}