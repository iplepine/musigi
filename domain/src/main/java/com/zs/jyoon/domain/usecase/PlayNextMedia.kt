package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.type.RepeatType
import com.zs.jyoon.domain.repositoy.PlaybackRepository
import javax.inject.Inject

class PlayNextMedia @Inject constructor(
    logger: Logger,
    private val playbackRepository: PlaybackRepository
) : BaseUseCase<Unit, Boolean>(
    logger
) {
    override suspend fun execute(param: Unit): Boolean {
        val currentPlayingList = playbackRepository.currentPlayingList.value
        if (currentPlayingList.isEmpty()) return false
        
        val currentPlayingItem = playbackRepository.currentPlayingItem.value
        val currentIndex = currentPlayingList.indexOf(currentPlayingItem)

        val repeatType = playbackRepository.currentRepeatType.value

        val isLastItem = currentIndex == currentPlayingList.size - 1

        return when (repeatType) {
            RepeatType.NONE -> {
                if (isLastItem) {
                    false
                } else {
                    playbackRepository.setSeekPosition(0)
                    true
                }
            }

            RepeatType.ONE -> {
                playbackRepository.setSeekPosition(0)
                true
            }

            RepeatType.ALL -> {
                val nextItem =
                    currentPlayingList.getOrElse(currentIndex + 1) { currentPlayingList.first() }
                playbackRepository.setCurrentPlayingItem(nextItem)
                playbackRepository.setSeekPosition(0)
                true
            }
        }
    }
}