package com.it2s.locationtracker.domain.useCase

import com.it2s.locationtracker.core.BaseUseCase
import com.it2s.locationtracker.core.NetworkResult
import com.it2s.locationtracker.domain.entity.ScanModeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetScanModeUseCase : BaseUseCase<List<ScanModeEntity>, Unit> {
    override operator fun invoke(params: Unit): Flow<NetworkResult<List<ScanModeEntity>>> =
        flow {
            emit(
                NetworkResult.Success(
                    listOf(
                        ScanModeEntity("Static", 100),
                        ScanModeEntity("Dynamic", 200)
                    )
                )
            )
        }.flowOn(Dispatchers.IO)
}