package com.it2s.locationtracker.domain.useCase

import com.it2s.locationtracker.core.BaseUseCase
import com.it2s.locationtracker.core.NetworkResult
import com.it2s.locationtracker.data.dto.DataCollectionRequestDto
import com.it2s.locationtracker.domain.repository.CustomDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateThunderBoardsUseCase @Inject constructor(private val customDataRepository: CustomDataRepository) :
    BaseUseCase<String, Pair<String, DataCollectionRequestDto>> {
    override operator fun invoke(param: Pair<String, DataCollectionRequestDto>): Flow<NetworkResult<String>> =
        customDataRepository.updateThunderBoards(param.first, param.second)
}