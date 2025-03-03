package com.zs.jyoon.domain.player

import com.zs.jyoon.domain.core.player.model.MediaItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface PlaybackController {
    val currentPlayingList: StateFlow<List<MediaItem>>
    val currentMediaItem: StateFlow<MediaItem?>

    // ui -> domain -> Player
    val seekPosition: StateFlow<Long>

    // Player -> domain -> ui
    val seekToPosition: SharedFlow<Long>
    val duration: StateFlow<Long>
    val isPlaying: StateFlow<Boolean>

    fun play()
    fun playItem(item: MediaItem)
    fun playList(list: List<MediaItem>)
    fun pause()
    suspend fun seekTo(position: Long)
    fun updateSeekPosition(position: Long)
    fun updateDuration(duration: Long)
}