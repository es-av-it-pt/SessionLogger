package com.it2s.locationtracker.data.repository

import com.it2s.locationtracker.core.NetworkResult
import com.it2s.locationtracker.data.dataSource.CustomDataApiService
import com.it2s.locationtracker.data.dto.DataCollectionRequestDto
import com.it2s.locationtracker.domain.entity.SendEventRequest
import com.it2s.locationtracker.domain.repository.CustomDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class CustomDataRepositoryImpl @Inject constructor(private val customDataApiService: CustomDataApiService) :
    CustomDataRepository {
    override fun updateThunderBoards(
        url: String,
        data: DataCollectionRequestDto,
    ): Flow<NetworkResult<String>> =
        flow {
            emit(NetworkResult.Loading)
            try {
                emit(
                    NetworkResult.Success(
                        customDataApiService.updateThunderBoards(
                            "http://$url/dataCollection",
                            data
                        )
                    )
                )
            } catch (e: Exception) {
                Timber.e(e)
                emit(NetworkResult.Error(e.message ?: "Unhandled exception"))
            }

        }.flowOn(Dispatchers.IO)

    override fun sendEvent(
        url: String,
        data: SendEventRequest,
    ): Flow<NetworkResult<String>> =
        flow {
            emit(NetworkResult.Loading)
            try {
                emit(
                    NetworkResult.Success(
                        customDataApiService.sendEvent(
                            "http://$url/publish",
                            data.toDto()
                        )
                    )
                )
            } catch (e: Exception) {
                Timber.e(e)
                emit(NetworkResult.Error(e.message ?: "Unhandled exception"))
            }

        }.flowOn(Dispatchers.IO)


    override fun updateMQTT(): Flow<NetworkResult<String>> = flow {
        emit(NetworkResult.Loading)
        delay(5000L)
        emit(NetworkResult.Success("Success"))
    }.flowOn(Dispatchers.IO)

}