package com.zs.jyoon.domain.usecase

import com.zs.jyoon.domain.core.BaseFlowUseCase
import com.zs.jyoon.domain.core.Logger
import com.zs.jyoon.domain.model.Library
import com.zs.jyoon.domain.repositoy.MediaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLibrary @Inject constructor(
    private val mediaRepository: MediaRepository,
    logger: Logger
) : BaseFlowUseCase<Unit, Library>(
    logger
) {
    override fun execute(param: Unit): Flow<Library> {
        return flow {
            emit(mediaRepository.getLibrary())
        }
    }
}