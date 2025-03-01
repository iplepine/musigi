package com.zs.jyoon.domain.core.player

import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.core.player.type.RepeatType

data class PlayerSetting(
    val playingStrategy: PlayingStrategy,
    val repeatType: RepeatType,
)