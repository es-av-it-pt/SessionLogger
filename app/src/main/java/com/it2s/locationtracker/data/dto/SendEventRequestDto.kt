package com.it2s.locationtracker.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SendEventRequestDto(
    val dataType:String,
    val collectionNumber:Int,
    val label:String,
    val status:String,
    val timer:Long = 0,
)
