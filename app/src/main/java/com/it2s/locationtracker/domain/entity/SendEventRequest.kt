package com.it2s.locationtracker.domain.entity

import com.it2s.locationtracker.data.dto.SendEventRequestDto


data class SendEventRequest(
    val dataType: String,
    val collectionNumber: Int,
    val statut: Statut,
    val event: Event,
    val timer: Long = 0,
) {
    fun toDto(): SendEventRequestDto =
        SendEventRequestDto(
            dataType,
            collectionNumber,
            statut.name,
            event.value,
            timer
        )


}

enum class Event(val value: String) {
    START("start"),
    STOP("stop"),
    CHANGE("change")
}

enum class Statut {
    IN, OUT
}
