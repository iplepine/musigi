package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.type.PlayingStrategy
import javax.inject.Inject

class ChangePlayingStrategy @Inject constructor(
    logger: Logger
) : BaseUseCase<PlayingStrategy, Unit>(
    logger
) {
    override suspend fun execute(param: PlayingStrategy) {
        TODO("Not yet implemented")
    }
}