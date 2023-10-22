package com.dadadadev.loginflow.di

import android.app.Application
import com.dadadadev.loginflow.data.repository.auth.AuthRepository
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import com.dadadadev.loginflow.data.repository.device_info.DeviceInfoRepository
import com.dadadadev.loginflow.data.repository.device_info.DeviceInfoRepositoryInterface
import com.dadadadev.loginflow.data.repository.validate_fields.ValidateFields
import com.dadadadev.loginflow.data.repository.validate_fields.ValidateFieldsInterface
import com.dadadadev.loginflow.data.service.firestore.FirestoreServiceInterface
import com.dadadadev.loginflow.data.service.time.TimeServiceInterface
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepository(
        appContext: Application,
        firestoreServiceInterface: FirestoreServiceInterface
    ): AuthRepositoryInterface = AuthRepository(
        auth = FirebaseAuth.getInstance(),
        appContext = appContext,
        firestoreService = firestoreServiceInterface
    )

    @Singleton
    @Provides
    fun provideDeviceInfoRepository(timeService: TimeServiceInterface): DeviceInfoRepositoryInterface =
        DeviceInfoRepository(timeService = timeService)

    @Singleton
    @Provides
    fun provideValidateFieldsRepository(appContext: Application): ValidateFieldsInterface =
        ValidateFields(
            appContext = appContext
        )
}