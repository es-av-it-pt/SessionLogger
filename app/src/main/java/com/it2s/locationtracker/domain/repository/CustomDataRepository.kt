package com.it2s.locationtracker.domain.repository

import com.it2s.locationtracker.core.NetworkResult
import com.it2s.locationtracker.data.dto.DataCollectionRequestDto
import com.it2s.locationtracker.domain.entity.SendEventRequest
import kotlinx.coroutines.flow.Flow

interface CustomDataRepository {
    fun updateThunderBoards(
        url: String,
        data: DataCollectionRequestDto
    ): Flow<NetworkResult<String>>

    fun sendEvent(url: String, data: SendEventRequest): Flow<NetworkResult<String>>
    fun updateMQTT(): Flow<NetworkResult<String>>
}