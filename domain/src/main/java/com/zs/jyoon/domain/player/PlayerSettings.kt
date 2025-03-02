package com.zs.jyoon.domain.player

import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType
import kotlinx.coroutines.flow.StateFlow

interface PlayerSettings {
    val volume: StateFlow<Float>
    val repeatType: StateFlow<RepeatType>
    val playingStrategy: StateFlow<PlayingStrategy>

    fun setVolume(volume: Float)
    fun setRepeatType(repeatType: RepeatType)
    fun setPlayingStrategy(playingStrategy: PlayingStrategy)
}