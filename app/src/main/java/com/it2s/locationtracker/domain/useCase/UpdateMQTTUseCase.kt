package com.it2s.locationtracker.domain.useCase

import com.it2s.locationtracker.core.BaseUseCase
import com.it2s.locationtracker.core.NetworkResult
import com.it2s.locationtracker.domain.repository.CustomDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateMQTTUseCase @Inject constructor(private val customDataRepository: CustomDataRepository) :
    BaseUseCase<String, Unit> {
    override operator fun invoke(param: Unit): Flow<NetworkResult<String>> =
        customDataRepository.updateMQTT()
}