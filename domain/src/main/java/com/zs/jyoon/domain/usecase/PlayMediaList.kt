package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.model.MediaItem
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import com.zs.jyoon.domain.repositoy.PlaybackRepository
import javax.inject.Inject

class PlayMediaList @Inject constructor(
    logger: Logger,
    private val playbackRepository: PlaybackRepository
) : BaseUseCase<PlayMediaList.Param, Unit>(
    logger
) {
    override suspend fun execute(param: Param) {
        val playList = param.playList
        val playingStrategy = playbackRepository.currentPlayingStrategy.value
        when (playingStrategy) {
            PlayingStrategy.SEQUENTIAL -> {
                playbackRepository.setPlayingList(playList)
                playbackRepository.setCurrentPlayingItem(playList[param.startIndex])
            }

            PlayingStrategy.SHUFFLE -> {
                val startItem = playList[param.startIndex]
                val shuffledList = playList.toMutableList().apply {
                    removeAt(param.startIndex)
                    shuffle()
                    add(0, startItem)
                }
                playbackRepository.setPlayingList(shuffledList)
                playbackRepository.setCurrentPlayingItem(startItem)
            }
        }
    }

    data class Param(
        val playList: List<MediaItem>,
        val startIndex: Int
    )
}