package com.zs.jyoon.musigi.di

import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.musigi.AppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideLogger(): Logger {
        return AppLogger
    }
}