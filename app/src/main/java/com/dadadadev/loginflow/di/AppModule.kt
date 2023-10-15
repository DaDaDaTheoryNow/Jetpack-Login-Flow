package com.dadadadev.loginflow.di

import android.app.Application
import android.content.Context
import com.dadadadev.loginflow.core.TextFieldValidator
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideContext(appContext: Application): Context =
        appContext

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideTextFieldValidator(appContext: Application): TextFieldValidator =
        TextFieldValidator(appContext)
}