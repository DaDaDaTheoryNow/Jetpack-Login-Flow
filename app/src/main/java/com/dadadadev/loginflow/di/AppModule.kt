package com.dadadadev.loginflow.di

import android.app.Application
import android.content.Context
import com.dadadadev.loginflow.core.TextFieldValidator
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

    @Provides
    @Singleton
    fun provideTextFieldValidator(appContext: Application): TextFieldValidator =
        TextFieldValidator(appContext)
}