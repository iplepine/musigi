package com.zs.jyoon.domain.player

import com.zs.jyoon.domain.core.player.model.MediaItem
import kotlinx.coroutines.flow.StateFlow

interface PlaybackController {
    val currentPlayingList: StateFlow<List<MediaItem>>
    val currentMediaItem: StateFlow<MediaItem>
    val seekPosition: StateFlow<Long>
    val isPlaying: StateFlow<Boolean>

    fun play()
    fun pause()
    fun seekTo(position: Long)
}