package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.model.Album
import com.zs.jyoon.domain.repositoy.MediaRepository
import javax.inject.Inject

class GetAlbum @Inject constructor(
    private val mediaRepository: MediaRepository,
    logger: Logger
) : BaseUseCase<String, Result<Album>>(
    logger
) {
    override suspend fun execute(param: String): Result<Album> {
        return runCatching {
            mediaRepository.getAlbum(param)
        }
    }
}