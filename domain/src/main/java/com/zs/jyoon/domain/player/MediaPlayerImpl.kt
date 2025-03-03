package com.zs.jyoon.domain.player

import com.zs.jyoon.domain.core.player.model.MediaItem
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType
import com.zs.jyoon.domain.repositoy.PlaybackRepository
import kotlinx.coroutines.flow.MutableStateFlow
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

    override val seekPosition: StateFlow<Long>
        get() = playbackRepository.currentSeekPosition

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

    override fun seekTo(position: Long) {
        playbackRepository.setSeekPosition(position)
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