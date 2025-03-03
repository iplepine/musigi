package com.zs.jyoon.data.repository

import com.zs.jyoon.domain.core.player.model.MediaItem
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType
import com.zs.jyoon.domain.repositoy.PlaybackRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

// TODO 로컬 저장소에 저장 필요
class PlaybackRepositoryImpl @Inject constructor(
) : PlaybackRepository {
    private val _currentPlayingList = MutableStateFlow<List<MediaItem>>(emptyList())
    override val currentPlayingList: StateFlow<List<MediaItem>>
        get() = _currentPlayingList

    override fun setPlayingList(items: List<MediaItem>) {
        _currentPlayingList.value = items
    }

    private val _currentPlayingItem = MutableStateFlow<MediaItem?>(null)
    override val currentPlayingItem: StateFlow<MediaItem?>
        get() = _currentPlayingItem

    override fun setCurrentPlayingItem(mediaItem: MediaItem?) {
        _currentPlayingItem.value = mediaItem
    }

    private val _currentSeekPosition = MutableStateFlow(0L)
    override val currentSeekPosition: StateFlow<Long>
        get() = _currentSeekPosition

    override fun setSeekPosition(seekPosition: Long) {
        _currentSeekPosition.value = seekPosition
    }

    private val _currentRepeatType = MutableStateFlow(RepeatType.NONE)
    override val currentRepeatType: StateFlow<RepeatType>
        get() = _currentRepeatType

    override fun setRepeatType(repeatType: RepeatType) {
        _currentRepeatType.value = repeatType
    }

    private val _currentPlayingStrategy = MutableStateFlow(PlayingStrategy.SEQUENTIAL)
    override val currentPlayingStrategy: StateFlow<PlayingStrategy>
        get() = _currentPlayingStrategy

    override fun setPlayingStrategy(playingStrategy: PlayingStrategy) {
        _currentPlayingStrategy.value = playingStrategy
    }
}