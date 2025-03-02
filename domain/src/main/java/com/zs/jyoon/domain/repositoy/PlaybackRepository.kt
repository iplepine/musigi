package com.zs.jyoon.domain.repositoy

import com.zs.jyoon.domain.core.player.model.MediaItem
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType
import kotlinx.coroutines.flow.StateFlow

interface PlaybackRepository {
    val currentPlayingList: StateFlow<List<MediaItem>>
    val setPlayingList: StateFlow<List<String>>

    val currentPlayingItem: StateFlow<MediaItem>
    val currentSeekPosition: StateFlow<Long>

    val currentRepeatType: StateFlow<RepeatType>
    fun setRepeatType(repeatType: RepeatType)

    val currentPlayingStrategy: StateFlow<PlayingStrategy>
    fun setPlayingStrategy(playingStrategy: PlayingStrategy)
}