package com.zs.jyoon.domain.repositoy

import com.zs.jyoon.domain.core.player.model.MediaItem
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType
import kotlinx.coroutines.flow.StateFlow

interface PlaybackRepository {
    val currentPlayingList: StateFlow<List<MediaItem>>
    fun setPlayingList(items: List<MediaItem>)

    val currentPlayingItem: StateFlow<MediaItem?>
    fun setCurrentPlayingItem(mediaItem: MediaItem?)

    val currentSeekPosition: StateFlow<Long>
    fun setSeekPosition(seekPosition: Long)

    val currentRepeatType: StateFlow<RepeatType>
    fun setRepeatType(repeatType: RepeatType)

    val currentPlayingStrategy: StateFlow<PlayingStrategy>
    fun setPlayingStrategy(playingStrategy: PlayingStrategy)
}
