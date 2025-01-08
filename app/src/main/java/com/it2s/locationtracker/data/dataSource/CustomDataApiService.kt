package com.it2s.locationtracker.data.dataSource


import com.it2s.locationtracker.data.dto.DataCollectionRequestDto
import com.it2s.locationtracker.data.dto.SendEventRequestDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface CustomDataApiService {

    @POST
    suspend fun updateThunderBoards(
        @Url url: String,
        @Body data: DataCollectionRequestDto,
    ): String

    @POST
    suspend fun sendEvent(
        @Url url: String,
        @Body data: SendEventRequestDto,
    ):String
}