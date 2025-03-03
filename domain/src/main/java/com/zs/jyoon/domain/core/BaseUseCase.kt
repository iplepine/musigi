package com.zs.jyoon.domain.core

abstract class BaseUseCase<PARAM, RESULT>(
    private val logger: Logger
) {
    protected abstract suspend fun execute(param: PARAM): RESULT

    suspend operator fun invoke(param: PARAM): RESULT {
        return execute(param).apply {
            logger.d(
                "AppLogger|" + this@BaseUseCase::class.java.simpleName,
                "param : $param, result : $this"
            )
        }
    }
}