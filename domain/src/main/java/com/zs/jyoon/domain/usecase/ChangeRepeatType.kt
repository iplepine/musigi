package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.core.player.type.RepeatType
import javax.inject.Inject

class ChangeRepeatType @Inject constructor(
    logger: Logger
) : BaseUseCase<RepeatType, Unit>(
    logger
) {
    override suspend fun execute(param: RepeatType) {
        TODO("Not yet implemented")
    }
}