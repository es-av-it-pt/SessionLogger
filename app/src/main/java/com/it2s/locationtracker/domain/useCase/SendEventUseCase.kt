package com.it2s.locationtracker.domain.useCase

import com.it2s.locationtracker.domain.entity.SendEventRequest
import com.it2s.locationtracker.core.BaseUseCase
import com.it2s.locationtracker.core.NetworkResult
import com.it2s.locationtracker.domain.repository.CustomDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendEventUseCase @Inject constructor(private val customDataRepository: CustomDataRepository) :
    BaseUseCase<String, Pair<String, SendEventRequest>> {
    override operator fun invoke(param: Pair<String, SendEventRequest>): Flow<NetworkResult<String>> =
        customDataRepository.sendEvent(param.first, param.second)
}