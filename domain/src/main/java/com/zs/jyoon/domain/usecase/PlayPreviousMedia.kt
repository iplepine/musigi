package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import javax.inject.Inject

class PlayPreviousMedia @Inject constructor(
    logger: Logger
) : BaseUseCase<Unit, Unit>(
    logger
) {
    override suspend fun execute(param: Unit): Unit {
        TODO("Not yet implemented")
    }
}