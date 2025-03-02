package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.model.Library
import com.zs.jyoon.domain.repositoy.MediaRepository
import javax.inject.Inject

class GetLibrary @Inject constructor(
    private val mediaRepository: MediaRepository,
    logger: Logger
) : BaseUseCase<Unit, Result<Library>>(
    logger
) {
    override suspend fun execute(param: Unit): Result<Library> {
        return runCatching {
            mediaRepository.getLibrary()
        }
    }
}