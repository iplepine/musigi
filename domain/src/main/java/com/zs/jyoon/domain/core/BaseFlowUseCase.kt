package com.zs.jyoon.domain.core

import kotlinx.coroutines.flow.Flow

abstract class BaseFlowUseCase<PARAM, RESULT>(
    private val logger: Logger
) {
    protected abstract fun execute(param: PARAM): Flow<RESULT>

    operator fun invoke(param: PARAM): Flow<RESULT> {
        return execute(param).apply {
            logger.d(this@BaseFlowUseCase::class.java.simpleName, "param : $param, result : $this")
        }
    }
}