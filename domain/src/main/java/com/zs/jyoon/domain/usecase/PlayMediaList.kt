package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.model.MediaItem
import javax.inject.Inject

class PlayMediaList @Inject constructor(
    logger: Logger
) : BaseUseCase<PlayMediaList.Param, Unit>(
    logger
) {
    override suspend fun execute(param: Param) {
        TODO("Not yet implemented")
    }

    data class Param(
        val playList: List<MediaItem>,
        val startIndex: Int
    )
}