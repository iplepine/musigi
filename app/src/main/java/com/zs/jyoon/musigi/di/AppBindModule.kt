package com.zs.jyoon.musigi.di

import com.zs.jyoon.data.repository.PlaybackRepositoryImpl
import com.zs.jyoon.domain.player.MediaPlayer
import com.zs.jyoon.domain.player.MediaPlayerImpl
import com.zs.jyoon.domain.repositoy.MediaRepository
import com.zs.jyoon.domain.repositoy.PlaybackRepository
import com.zs.jyoon.media.repository.MediaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class AppBindModule {
    @Singleton
    @Binds
    abstract fun bindMediaRepository(
        mediaRepositoryImpl: MediaRepositoryImpl
    ): MediaRepository

    @Singleton
    @Binds
    abstract fun bindPlaybackRepository(
        repositoryImpl: PlaybackRepositoryImpl
    ): PlaybackRepository

    @Singleton
    @Binds
    abstract fun bindMediaPlayer(
        mediaPlayer: MediaPlayerImpl
    ): MediaPlayer
}