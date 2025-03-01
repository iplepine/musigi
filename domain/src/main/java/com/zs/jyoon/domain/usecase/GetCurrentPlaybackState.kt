package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.model.PlaybackState
import com.zs.jyoon.domain.model.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentPlaybackState @Inject constructor(
    logger: Logger
) : BaseUseCase<Unit, Flow<PlaybackState>>(
    logger
) {
    override suspend fun execute(param: Unit): Flow<PlaybackState> {
        TODO("Not yet implemented")
    }
}