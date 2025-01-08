package com.it2s.locationtracker.core.di

import com.it2s.locationtracker.data.dataSource.CustomDataApiService
import com.it2s.locationtracker.data.repository.CustomDataRepositoryImpl
import com.it2s.locationtracker.domain.repository.CustomDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepositoryModule(customDataApiService: CustomDataApiService): CustomDataRepository =
        CustomDataRepositoryImpl(customDataApiService)
}