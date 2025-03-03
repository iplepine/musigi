package com.zs.jyoon.domain.player

import com.zs.jyoon.domain.core.player.model.MediaItem
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType
import com.zs.jyoon.domain.repositoy.PlaybackRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MediaPlayerImpl @Inject constructor(
    private val playbackRepository: PlaybackRepository,
) : MediaPlayer {
    override val currentPlayingList: StateFlow<List<MediaItem>>
        get() = playbackRepository.currentPlayingList

    override val currentMediaItem: StateFlow<MediaItem?>
        get() = playbackRepository.currentPlayingItem

    private val _seekToPosition: MutableSharedFlow<Long> = MutableSharedFlow()
    override val seekToPosition: SharedFlow<Long>
        get() = _seekToPosition

    override suspend fun seekTo(position: Long) {
        updateSeekPosition(position)
        _seekToPosition.emit(position)
    }

    override val seekPosition: StateFlow<Long>
        get() = playbackRepository.currentSeekPosition

    override fun updateSeekPosition(position: Long) {
        playbackRepository.setSeekPosition(position)
    }

    private val _duration: MutableStateFlow<Long> = MutableStateFlow(Long.MAX_VALUE)
    override val duration: StateFlow<Long>
        get() = _duration

    override fun updateDuration(duration: Long) {
        _duration.value = duration.coerceAtLeast(0)
    }

    private val _isPlaying: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val isPlaying: StateFlow<Boolean> = _isPlaying

    override fun play() {
        _isPlaying.value = true
    }

    override fun playItem(item: MediaItem) {
        playbackRepository.setCurrentPlayingItem(item)
        _isPlaying.value = true
    }

    override fun playList(list: List<MediaItem>) {
        playbackRepository.setPlayingList(list)
        playbackRepository.setCurrentPlayingItem(list.first())
        _isPlaying.value = true
    }

    override fun pause() {
        _isPlaying.value = false
    }

    private val _volume: MutableStateFlow<Float> = MutableStateFlow(0f)
    override val volume: StateFlow<Float>
        get() = _volume

    override val repeatType: StateFlow<RepeatType>
        get() = playbackRepository.currentRepeatType
    override val playingStrategy: StateFlow<PlayingStrategy>
        get() = playbackRepository.currentPlayingStrategy

    override fun setVolume(volume: Float) {
        _volume.value = volume
    }

    override fun setRepeatType(repeatType: RepeatType) {
        playbackRepository.setRepeatType(repeatType)
    }

    override fun setPlayingStrategy(playingStrategy: PlayingStrategy) {
        playbackRepository.setPlayingStrategy(playingStrategy)
    }
}