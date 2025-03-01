package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.model.MediaItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentPlayingList @Inject constructor(
    logger: Logger
) : BaseUseCase<Unit, Flow<List<MediaItem>>>(
    logger
) {
    override suspend fun execute(param: Unit): Flow<List<MediaItem>> {
        TODO("Not yet implemented")
    }
}