package com.zs.jyoon.domain.core.player.model

data class PlaybackState(
    val isPlaying: Boolean,
    val currentMediaId: String,
    val seekPosition: Long,
    val totalDuration: Long
)