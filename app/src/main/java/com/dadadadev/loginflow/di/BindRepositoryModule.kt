package com.dadadadev.loginflow.di

import com.dadadadev.loginflow.data.repository.DeviceInfoRepositoryImpl
import com.dadadadev.loginflow.domain.repository.DeviceInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindDeviceInfoRepository(deviceInfoRepositoryImpl: DeviceInfoRepositoryImpl): DeviceInfoRepository
}