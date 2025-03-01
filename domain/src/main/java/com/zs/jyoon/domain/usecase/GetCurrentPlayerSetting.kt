package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.PlayerSetting
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentPlayerSetting @Inject constructor(
    logger: Logger
) : BaseUseCase<Unit, Flow<PlayerSetting>>(
    logger
) {
    override suspend fun execute(param: Unit): Flow<PlayerSetting> {
        TODO("Not yet implemented")
    }
}