package com.dadadadev.loginflow.di

import com.dadadadev.loginflow.data.repository.auth.AuthRepository
import com.dadadadev.loginflow.data.repository.auth.AuthRepositoryInterface
import com.dadadadev.loginflow.data.repository.device_info.DeviceInfoRepository
import com.dadadadev.loginflow.data.repository.device_info.DeviceInfoRepositoryInterface
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
    fun bindDeviceInfoRepository(): DeviceInfoRepositoryInterface = DeviceInfoRepository()

    @Singleton
    @Provides
    fun bindAuthRepository(): AuthRepositoryInterface = AuthRepository(
        auth = FirebaseAuth.getInstance()
    )
}