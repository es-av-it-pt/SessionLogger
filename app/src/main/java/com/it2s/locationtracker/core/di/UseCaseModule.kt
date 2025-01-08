package com.it2s.locationtracker.core.di

import com.it2s.locationtracker.domain.repository.CustomDataRepository
import com.it2s.locationtracker.domain.useCase.GetScanModeUseCase
import com.it2s.locationtracker.domain.useCase.SendEventUseCase
import com.it2s.locationtracker.domain.useCase.UpdateMQTTUseCase
import com.it2s.locationtracker.domain.useCase.UpdateThunderBoardsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideGetScanModeUseCase(): GetScanModeUseCase = GetScanModeUseCase()

    @Provides
    @Singleton
    fun provideUpdateThunderBoardsUseCase(customDataRepository: CustomDataRepository): UpdateThunderBoardsUseCase =
        UpdateThunderBoardsUseCase(customDataRepository)

    @Provides
    @Singleton
    fun provdeUpdateMQTTUseCase(customDataRepository: CustomDataRepository): UpdateMQTTUseCase =
        UpdateMQTTUseCase(customDataRepository)

    @Provides
    @Singleton
    fun provdeSendEventUseCase(customDataRepository: CustomDataRepository): SendEventUseCase =
        SendEventUseCase(customDataRepository)

}