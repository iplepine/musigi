package com.zs.jyoon.musigi.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.musigi.AppLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideLogger(): Logger {
        return AppLogger
    }

    @Singleton
    @Provides
    fun provideExoplayer(
        @ApplicationContext context: Context
    ): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }
}