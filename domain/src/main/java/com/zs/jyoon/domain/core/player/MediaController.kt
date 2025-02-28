package com.zs.jyoon.domain.core.player

interface MediaController {
    fun play()
    fun pause()
    fun playNext()
    fun playPrevious()
    fun seekTo(position: Long)
    fun setVolume(volume: Float)
}