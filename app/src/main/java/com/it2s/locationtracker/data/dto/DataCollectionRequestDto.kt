package com.it2s.locationtracker.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DataCollectionRequestDto(
    val dataType: String,
    val referencePoint: Int?,
    val path: Int?,
    val collectionProgress: Boolean = true,
)