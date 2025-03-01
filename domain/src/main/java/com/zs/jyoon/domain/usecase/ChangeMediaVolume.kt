package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import javax.inject.Inject

class ChangeMediaVolume @Inject constructor(
    logger: Logger
) : BaseUseCase<Int, Int>(
    logger
) {
    override suspend fun execute(param: Int): Int {
        TODO("Not yet implemented")
    }
}