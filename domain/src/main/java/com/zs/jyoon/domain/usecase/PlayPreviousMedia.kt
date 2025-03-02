package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.type.RepeatType
import com.zs.jyoon.domain.repositoy.PlaybackRepository
import javax.inject.Inject

class PlayPreviousMedia @Inject constructor(
    logger: Logger,
    private val playbackRepository: PlaybackRepository
) : BaseUseCase<Unit, Boolean>(logger) {

    override suspend fun execute(param: Unit): Boolean {
        val currentPlayingList = playbackRepository.currentPlayingList.value
        val currentPlayingItem = playbackRepository.currentPlayingItem.value
        val currentIndex = currentPlayingList.indexOf(currentPlayingItem)

        val repeatType = playbackRepository.currentRepeatType.value

        val isFirstItem = currentIndex == 0

        return when (repeatType) {
            RepeatType.NONE -> {
                if (isFirstItem) {
                    playbackRepository.setSeekPosition(0)
                    false
                } else {
                    playbackRepository.setCurrentPlayingItem(currentPlayingList[currentIndex - 1])
                    playbackRepository.setSeekPosition(0)
                    true
                }
            }

            RepeatType.ONE -> {
                playbackRepository.setSeekPosition(0)
                true
            }

            RepeatType.ALL -> {
                if (isFirstItem) {
                    playbackRepository.setCurrentPlayingItem(currentPlayingList.last())
                } else {
                    playbackRepository.setCurrentPlayingItem(currentPlayingList[currentIndex - 1])
                }
                playbackRepository.setSeekPosition(0)
                true
            }
        }
    }
}
